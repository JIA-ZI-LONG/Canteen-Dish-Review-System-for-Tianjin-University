package xyz.tjucomments.tjufood.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.tjucomments.tjufood.dto.*;
import xyz.tjucomments.tjufood.entity.CreditRecord;
import xyz.tjucomments.tjufood.entity.User;
import xyz.tjucomments.tjufood.interceptor.UserHolder;
import xyz.tjucomments.tjufood.mapper.UserMapper;
import xyz.tjucomments.tjufood.service.ICreditRecordService;
import xyz.tjucomments.tjufood.service.IIncentiveService;
import xyz.tjucomments.tjufood.service.IUserService;
import xyz.tjucomments.tjufood.utils.JwtUtil;
import xyz.tjucomments.tjufood.utils.constants.IdPrefixConstants;
import xyz.tjucomments.tjufood.utils.constants.RedisConstants;
import xyz.tjucomments.tjufood.utils.id.RedisIdWorker;
import xyz.tjucomments.tjufood.utils.security.DataMaskingUtils;
import xyz.tjucomments.tjufood.utils.security.RateLimitUtils;
import xyz.tjucomments.tjufood.service.IFileStorageService;
import xyz.tjucomments.tjufood.service.IMinioUrlService;
import org.springframework.web.multipart.MultipartFile;
import cn.hutool.core.io.FileUtil;
import java.io.InputStream;
import xyz.tjucomments.tjufood.utils.validation.RegexUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.Arrays;

import static xyz.tjucomments.tjufood.utils.constants.RedisConstants.USER_SIGN_KEY;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private JavaMailSender mailSender;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private RedisIdWorker redisIdWorker;
    @Resource
    private RateLimitUtils rateLimitUtils;
    @Resource
    private ICreditRecordService creditRecordService;
    @Resource
    private IIncentiveService incentiveService;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private IFileStorageService fileStorageService;
    @Resource
    private IMinioUrlService minioUrlService;

    @Value("${spring.mail.username}")
    private String fromEmail;


    // 在 UserServiceImpl.java 中

    @Override
    @Transactional
    public Result sendCode(SendCodeDTO sendCodeDTO) {
        String email = sendCodeDTO.getEmail();
        Integer type = sendCodeDTO.getType();

        if (RegexUtils.isEmailInvalid(email)) {
            return Result.fail("邮箱格式不正确！");
        }

        // 防重放和发送频率限制 (保持不变)
        if (!rateLimitUtils.checkSmsRateLimit(email)) {
            long remainingTime = rateLimitUtils.getRemainingCooldown(email, "sms_limit");
            return Result.fail("验证码发送过于频繁，请 " + remainingTime + " 秒后再试");
        }

        User user = getOne(new QueryWrapper<User>().eq("email", email));

        if (type == 0 && user != null) {
            return Result.fail("该邮箱已被注册！");
        }
        if (type == 2 && user == null) {
            return Result.fail("该邮箱未注册！");
        }

        String code = RandomUtil.randomNumbers(6);
        String emailCodeKey = RedisConstants.LOGIN_CODE_KEY + type + ":" + email;
        stringRedisTemplate.opsForValue().set(emailCodeKey, code, RedisConstants.LOGIN_CODE_TTL, TimeUnit.MINUTES);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email);
        message.setSubject("TjuFood 验证码");
        message.setText("您的验证码是：" + code + "，有效期5分钟，请勿泄露。");
        mailSender.send(message);

        return Result.ok("验证码发送成功");
    }
    @Override
    @Transactional
    public Result register(RegisterFormDTO registerForm) {
        if (!registerForm.getPassword().equals(registerForm.getConfirmPassword())) {
            return Result.fail("两次输入的密码不一致！");
        }
        if (RegexUtils.isCodeInvalid(registerForm.getCode())) {
            return Result.fail("验证码格式错误！");
        }
        long count = count(new QueryWrapper<User>().eq("email", registerForm.getEmail()));
        if (count > 0) {
            return Result.fail("该邮箱已被注册！");
        }
        // 注意，这里的 type 是 0
        String key = RedisConstants.LOGIN_CODE_KEY + "0:" + registerForm.getEmail();
        String cachedCode = stringRedisTemplate.opsForValue().get(key);
        if (cachedCode == null || !cachedCode.equals(registerForm.getCode())) {
            return Result.fail("邮箱验证码错误！");
        }

        long userId = redisIdWorker.nextId(IdPrefixConstants.USER_ID_PREFIX);
        User user = new User();
        user.setId(userId);
        user.setEmail(registerForm.getEmail());
        user.setPassword(passwordEncoder.encode(registerForm.getPassword()));
        user.setNickname(StrUtil.isNotBlank(registerForm.getNickname()) ?
                registerForm.getNickname() :
                IdPrefixConstants.USER_ID_PREFIX + userId);
        user.setGender(registerForm.getGender());
        user.setCampus(registerForm.getCampus());

        save(user);
        stringRedisTemplate.delete(key);
        return Result.ok("注册成功！");
    }

    @Override
    public Result login(LoginFormDTO loginForm) {
        log.info("用户登录请求开始，账号: {}", loginForm.getAccount());

        String account = loginForm.getAccount();
        if (StrUtil.isBlank(account)) {
            log.warn("登录失败：账号为空");
            return Result.fail("账号不能为空");
        }

        // 1. 验证滑动验证码
        String uuid = loginForm.getUuid();
        Integer userX = loginForm.getX();

        log.info("验证码信息 - UUID: {}, X坐标: {}", uuid, userX);

        if (StrUtil.isBlank(uuid)) {
            log.warn("登录失败：验证码UUID为空");
            return Result.fail("验证码UUID不能为空");
        }

        if (userX == null) {
            log.warn("登录失败：验证码滑动距离为空");
            return Result.fail("验证码滑动距离不能为空");
        }

        // 从Redis获取正确的X坐标
        String correctXStr = stringRedisTemplate.opsForValue().get(RedisConstants.LOGIN_CODE_KEY + uuid);
        log.info("从Redis获取的正确X坐标: {}", correctXStr);

        if (correctXStr == null) {
            log.warn("登录失败：验证码已过期，UUID: {}", uuid);
            return Result.fail("验证码已过期，请重新获取");
        }

        try {
            int correctX = Integer.parseInt(correctXStr);
            int diff = Math.abs(userX - correctX);
            log.info("验证码验证 - 用户X: {}, 正确X: {}, 差值: {}", userX, correctX, diff);

            // 允许40像素的误差范围（加大误差范围）
            if (diff > 40) {
                // 验证失败，删除验证码防止重复尝试
                stringRedisTemplate.delete(RedisConstants.LOGIN_CODE_KEY + uuid);
                log.warn("登录失败：验证码验证失败，差值: {}", diff);
                return Result.fail("验证码验证失败，请重新验证");
            }
        } catch (NumberFormatException e) {
            log.error("登录失败：验证码数据异常", e);
            return Result.fail("验证码数据异常");
        }

        // 验证码验证成功，删除验证码
        stringRedisTemplate.delete(RedisConstants.LOGIN_CODE_KEY + uuid);
        log.info("验证码验证成功");

        // 2. 查找用户
        User user;
        log.info("开始查找用户，账号: {}", account);

        if (StrUtil.isNumeric(account)) {
            log.info("账号为纯数字，尝试按用户ID查找");
            try {
                user = getById(Long.parseLong(account));
                log.info("按用户ID查找结果: {}", user != null ? "找到用户" : "未找到用户");
            } catch (NumberFormatException e) {
                log.info("用户ID解析失败，尝试按邮箱查找");
                user = query().eq("email", account).one();
                log.info("按邮箱查找结果: {}", user != null ? "找到用户" : "未找到用户");
            }
        } else if (!RegexUtils.isEmailInvalid(account)) {
            log.info("账号为邮箱格式，按邮箱查找");
            user = query().eq("email", account).one();
            log.info("按邮箱查找结果: {}", user != null ? "找到用户" : "未找到用户");
        } else {
            log.warn("登录失败：账号格式错误，账号: {}", account);
            return Result.fail("账号格式错误，请输入用户ID或邮箱");
        }

        if (user == null) {
            log.warn("登录失败：用户不存在，账号: {}", account);
            return Result.fail("用户不存在");
        }

        log.info("找到用户，ID: {}, 昵称: {}", user.getId(), user.getNickname());

        // 3. 验证密码
        boolean passwordMatch = passwordEncoder.matches(loginForm.getPassword(), user.getPassword());
        log.info("密码验证结果: {}", passwordMatch ? "正确" : "错误");

        if (!passwordMatch) {
            log.warn("登录失败：密码错误，用户ID: {}", user.getId());
            return Result.fail("密码错误");
        }

        // 4. 检查用户状态
        log.info("用户状态: {}", user.getStatus());
        if (user.getStatus() != null && user.getStatus() != 0) {
            log.warn("登录失败：账号已被禁用，用户ID: {}", user.getId());
            return Result.fail("账号已被禁用，请联系管理员");
        }

        // 5. 验证通过，生成token并封装返回数据
        log.info("所有验证通过，开始生成token");

        // 5.1 生成JWT
        String token = jwtUtil.createJWT(user.getId().toString());
        log.info("生成的token: {}", token);

        // 5.2 将用户信息写入Redis，供拦截器鉴权
        UserDTO cacheUser = BeanUtil.copyProperties(user, UserDTO.class);
        String redisKey = RedisConstants.LOGIN_USER_KEY + token;
        stringRedisTemplate.opsForValue().set(
                redisKey,
                JSONUtil.toJsonStr(cacheUser),
                RedisConstants.LOGIN_USER_TTL,
                TimeUnit.MINUTES);
        log.info("用户信息已写入Redis，key: {}", redisKey);

        LoginSuccessVO loginSuccessVO = new LoginSuccessVO();
        loginSuccessVO.setToken(token);
        loginSuccessVO.setId(user.getId());
        loginSuccessVO.setNickname(user.getNickname());
        loginSuccessVO.setIcon(user.getIcon());

        log.info("登录成功，返回数据: token={}, id={}, nickname={}", token, user.getId(), user.getNickname());

        Result result = Result.ok(loginSuccessVO);
        log.info("最终返回的Result对象: success={}, data={}", result.getSuccess(), result.getData());

        return result;
    }

    @Override
    public Result resetPassword(PasswordResetDTO resetForm) {
        // 注意，这里的 type 是 2
        String key = RedisConstants.LOGIN_CODE_KEY + "2:" + resetForm.getEmail();
        String cachedCode = stringRedisTemplate.opsForValue().get(key);
        if (cachedCode == null || !cachedCode.equals(resetForm.getCode())) {
            return Result.fail("验证码错误！");
        }
        String newPassword = passwordEncoder.encode(resetForm.getNewPassword());
        boolean success = update().set("password", newPassword).eq("email", resetForm.getEmail()).update();
        if(success) {
            stringRedisTemplate.delete(key);
            return Result.ok("密码重置成功！");
        }
        return Result.fail("密码重置失败");
    }

    @Override
    public Result logout(String token) {
        // JWT模式下，客户端丢弃token即可，此处为保留方法
        return Result.ok();
    }

    @Override
    public Result queryMe() {
        UserDTO userDTO = UserHolder.getUser();
        if (userDTO == null) return Result.fail("用户未登录");
        User user = getById(userDTO.getId());
        return Result.ok(user);
    }

    @Override
    public Result queryUserById(Long userId) {
        User user = getById(userId);
        if (user == null) return Result.fail("用户不存在！");
        user.setPassword(null);
        if (user.getPhone() != null) {
            user.setPhone(DataMaskingUtils.maskPhone(user.getPhone()));
        }
        if (user.getEmail() != null) {
            user.setEmail(DataMaskingUtils.maskEmail(user.getEmail()));
        }
        return Result.ok(user);
    }

    @Override
    @Transactional
    public Result updateUserProfile(UserDTO userDTO) {
        Long userId = UserHolder.getUser().getId();
        User user = new User();
        user.setId(userId);
        user.setNickname(userDTO.getNickName());
        user.setIcon(userDTO.getIcon());
        boolean isSuccess = updateById(user);
        return isSuccess ? Result.ok() : Result.fail("更新失败！");
    }

    @Override
    @Transactional
    public Result sign() {
        Long userId = UserHolder.getUser().getId();
        LocalDateTime now = LocalDateTime.now();
        String dateSuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        String key = USER_SIGN_KEY + userId + dateSuffix;
        int dayOfMonth = now.getDayOfMonth();

        if (Boolean.TRUE.equals(stringRedisTemplate.opsForValue().getBit(key, dayOfMonth - 1))) {
            return Result.fail("您今天已经签过到了！");
        }

        stringRedisTemplate.opsForValue().setBit(key, dayOfMonth - 1, true);

        // 使用激励服务处理签到奖励，而不是硬编码积分
        Result incentiveResult = incentiveService.processUserAction(userId, "USER_SIGN_IN", "每日签到奖励");

        if (incentiveResult.getSuccess()) {
            return Result.ok(incentiveResult.getData());
        } else {
            // 如果激励处理失败，回退到原有逻辑
            update().setSql("credits = credits + 5").eq("id", userId).update();

            CreditRecord record = new CreditRecord();
            record.setId(redisIdWorker.nextId("credit"));
            record.setUserId(userId);
            record.setCredits(5);
            record.setActionType("USER_SIGN_IN");
            record.setDescription("每日签到奖励（回退）");
            record.setCreateTime(LocalDateTime.now());
            creditRecordService.save(record);

            return Result.ok("签到成功！积分+5");
        }
    }

    @Override
    public Result updateAvatar(MultipartFile file) {
        try {
            // 1. 参数验证
            if (file == null || file.isEmpty()) {
                return Result.fail("请选择要上传的头像文件！");
            }

            // 2. 文件类型验证
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null) {
                return Result.fail("文件名不能为空！");
            }

            String extension = FileUtil.extName(originalFilename).toLowerCase();
            if (!Arrays.asList("jpg", "jpeg", "png", "gif", "bmp", "webp").contains(extension)) {
                return Result.fail("只支持 JPG、PNG、GIF、BMP、WEBP 格式的图片！");
            }

            // 3. 文件大小验证（限制为5MB）
            if (file.getSize() > 5 * 1024 * 1024) {
                return Result.fail("头像文件大小不能超过5MB！");
            }

            // 4. 获取当前用户
            UserDTO currentUser = UserHolder.getUser();
            if (currentUser == null) {
                return Result.fail("用户未登录！");
            }

            // 5. 生成文件名
            String fileName = "user_avatar_" + currentUser.getId() + "_" + System.currentTimeMillis() + "." + extension;

            // 6. 上传文件到MinIO
            try (InputStream inputStream = file.getInputStream()) {
                String objectName = fileStorageService.uploadFile(fileName, inputStream, file.getSize(), file.getContentType());

                // 7. 更新用户头像字段
                User updateUser = new User();
                updateUser.setId(currentUser.getId());
                updateUser.setIcon(objectName);

                boolean updateSuccess = updateById(updateUser);
                if (!updateSuccess) {
                    return Result.fail("更新用户头像失败！");
                }

                // 8. 生成访问URL
                String avatarUrl = minioUrlService.generatePresignedUrl(objectName);

                // 9. 更新UserHolder中的用户信息
                currentUser.setIcon(objectName);
                UserHolder.saveUser(currentUser);

                return Result.ok(avatarUrl);

            } catch (Exception e) {
                log.error("上传头像到MinIO失败", e);
                return Result.fail("头像上传失败：" + e.getMessage());
            }

        } catch (Exception e) {
            log.error("更新用户头像异常", e);
            return Result.fail("头像上传失败，请稍后重试！");
        }
    }

    @Override
    public Result refreshAvatarUrl() {
        try {
            // 1. 获取当前用户
            UserDTO currentUser = UserHolder.getUser();
            if (currentUser == null) {
                return Result.fail("用户未登录！");
            }

            // 2. 检查用户是否有头像
            if (StrUtil.isBlank(currentUser.getIcon())) {
                return Result.fail("用户尚未设置头像！");
            }

            // 3. 生成新的访问URL
            String avatarUrl = minioUrlService.generatePresignedUrl(currentUser.getIcon());

            return Result.ok(avatarUrl);

        } catch (Exception e) {
            log.error("刷新用户头像URL异常", e);
            return Result.fail("头像URL刷新失败，请稍后重试！");
        }
    }
}