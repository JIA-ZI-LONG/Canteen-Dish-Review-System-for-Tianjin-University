// src/main/java/xyz/tjucomments/tjufood/service/impl/AdminServiceImpl.java
package xyz.tjucomments.tjufood.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import xyz.tjucomments.tjufood.dto.*;
import xyz.tjucomments.tjufood.entity.Admin;
import xyz.tjucomments.tjufood.entity.Role;
import xyz.tjucomments.tjufood.mapper.AdminMapper;
import xyz.tjucomments.tjufood.mapper.RoleMapper;
import xyz.tjucomments.tjufood.service.IAdminService;
import xyz.tjucomments.tjufood.service.IFileStorageService;
import xyz.tjucomments.tjufood.service.IMinioUrlService;
import xyz.tjucomments.tjufood.utils.constants.RedisConstants;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;
@Slf4j
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {
    private static final String DEFAULT_INITIAL_PASSWORD = "123456";
    private static final String SYSTEM_ADMIN_ROLE_NAME = "系统管理员";

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private IFileStorageService fileStorageService; // 注入通用文件存储服务

    @Resource
    private IMinioUrlService minioUrlService;     // 注入URL管理服务

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public AdminInfoVO login(AdminLoginDTO loginForm) {
        Admin admin = getOne(new QueryWrapper<Admin>().eq("username", loginForm.getUsername()));

        // 统一的错误处理，避免时序攻击
        boolean isValidLogin = false;
        if (admin != null) {
            // 即使用户不存在，也执行密码验证以保持相同的处理时间
            isValidLogin = passwordEncoder.matches(loginForm.getPassword(), admin.getPassword());
        } else {
            // 用户不存在时，也执行一次密码编码操作以保持相同的处理时间
            passwordEncoder.matches(loginForm.getPassword(), "$2a$10$dummyHashToPreventTimingAttack");
        }

        if (!isValidLogin || admin == null) {
            throw new RuntimeException("账号或密码不对");
        }

        if (admin.getStatus() != 0) {
            throw new RuntimeException("该管理员账号已被禁用！");
        }
        String token = UUID.randomUUID().toString(true);
        AdminDTO adminDTO = BeanUtil.copyProperties(admin, AdminDTO.class);
        try {
            String adminDTOJson = objectMapper.writeValueAsString(adminDTO);
            String tokenKey = RedisConstants.LOGIN_ADMIN_KEY + token;
            stringRedisTemplate.opsForValue().set(tokenKey, adminDTOJson, RedisConstants.LOGIN_ADMIN_TTL, TimeUnit.MINUTES);
        } catch (JsonProcessingException e) {
            log.error("管理员登录序列化DTO失败", e);
            throw new RuntimeException("登录失败，系统内部错误");
        }
        AdminInfoVO adminInfoVO = new AdminInfoVO();
        adminInfoVO.setToken(token);
        adminInfoVO.setId(admin.getId());
        adminInfoVO.setUsername(admin.getUsername());
        adminInfoVO.setName(admin.getName());
        // 【修正】返回数据库中的头像URL
        adminInfoVO.setAvatar(admin.getAvatar());
        adminInfoVO.setRoles(getRolesByUsername(admin.getUsername()));
        return adminInfoVO;
    }

    @Override
    public List<String> getRolesByUsername(String username) {
        Admin admin = getOne(new QueryWrapper<Admin>().eq("username", username));
        if (admin == null) {
            return List.of();
        }
        return baseMapper.findRoleNamesByAdminId(admin.getId());
    }

    @Override
    public Result logout(String token) {
        String tokenKey = RedisConstants.LOGIN_ADMIN_KEY + token;
        stringRedisTemplate.delete(tokenKey);
        return Result.ok();
    }

    @Override
    @Transactional
    public Result createAdmin(AdminCreateDTO createDTO) {
        Role roleToAssign = roleMapper.selectById(createDTO.getRoleId());
        if (roleToAssign == null) {
            return Result.fail("指定的角色不存在！");
        }
        if (SYSTEM_ADMIN_ROLE_NAME.equals(roleToAssign.getName())) {
            return Result.fail("不允许通过此接口创建系统管理员！");
        }
        long count = this.count(new QueryWrapper<Admin>().eq("username", createDTO.getUsername()));
        if (count > 0) {
            return Result.fail("该管理员用户名已存在！");
        }
        Admin admin = new Admin();
        admin.setUsername(createDTO.getUsername());
        admin.setName(createDTO.getName());
        admin.setPassword(passwordEncoder.encode(DEFAULT_INITIAL_PASSWORD));
        admin.setRoleId(createDTO.getRoleId());
        this.save(admin);


        return Result.ok(admin.getId());
    }

    @Override
    @Transactional
    public Result updateProfile(Long adminId, AdminProfileUpdateDTO updateDTO) {
        // 检查管理员是否存在
        Admin existingAdmin = this.getById(adminId);
        if (existingAdmin == null) {
            return Result.fail("管理员不存在！");
        }

        // 防止修改系统管理员
        List<String> roleNames = getRolesByUsername(existingAdmin.getUsername());
        if (roleNames.contains(SYSTEM_ADMIN_ROLE_NAME)) {
            return Result.fail("不允许修改系统管理员信息！");
        }

        // 检查用户名是否已被其他管理员使用
        if (!existingAdmin.getUsername().equals(updateDTO.getUsername())) {
            long count = this.count(new QueryWrapper<Admin>()
                    .eq("username", updateDTO.getUsername())
                    .ne("id", adminId));
            if (count > 0) {
                return Result.fail("该用户名已被其他管理员使用！");
            }
        }

        // 更新管理员信息
        Admin admin = new Admin();
        admin.setId(adminId);
        admin.setUsername(updateDTO.getUsername());
        admin.setName(updateDTO.getName());

        boolean isSuccess = this.updateById(admin);
        return isSuccess ? Result.ok() : Result.fail("更新个人信息失败！");
    }

    @Override
    @Transactional
    public Result changePassword(Long adminId, AdminPasswordChangeDTO changeDTO) {
        // 检查管理员是否存在
        Admin existingAdmin = this.getById(adminId);
        if (existingAdmin == null) {
            return Result.fail("管理员不存在！");
        }

        // 验证当前密码
        if (!passwordEncoder.matches(changeDTO.getOldPassword(), existingAdmin.getPassword())) {
            return Result.fail("当前密码不正确！");
        }

        // 检查新密码是否与当前密码相同
        if (passwordEncoder.matches(changeDTO.getNewPassword(), existingAdmin.getPassword())) {
            return Result.fail("新密码不能与当前密码相同！");
        }

        // 更新密码
        Admin admin = new Admin();
        admin.setId(adminId);
        admin.setPassword(passwordEncoder.encode(changeDTO.getNewPassword()));

        boolean isSuccess = this.updateById(admin);
        return isSuccess ? Result.ok() : Result.fail("修改密码失败！");
    }

    /**
     * 实现更新头像的业务逻辑
     */
    @Override
    @Transactional
    public Result updateAvatar(Long adminId, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return Result.fail("上传文件不能为空");
        }

        try {
            // 1. 生成唯一的文件名
            String originalFilename = file.getOriginalFilename();
            String ext = FileUtil.extName(originalFilename);
            // 格式: avatars/admin/{adminId}-{timestamp}.{ext}
            String objectName = String.format("avatars/admin/%d-%d.%s",
                    adminId, System.currentTimeMillis(), ext);

            // 2. 使用通用服务上传文件到MinIO
            try (InputStream inputStream = file.getInputStream()) {
                // 【确认】此处的调用现在与更新后的接口完全匹配，编译将通过
                fileStorageService.uploadFile(objectName, inputStream, file.getSize(), file.getContentType());
            }

            // 3. 生成可访问的URL
            String avatarUrl = minioUrlService.generatePresignedUrl(objectName);

            // 4. 更新数据库中该管理员的avatar字段
            Admin admin = new Admin();
            admin.setId(adminId);
            admin.setAvatar(avatarUrl);
            boolean success = this.updateById(admin);

            if (success) {
                // 5. 返回新的URL给前端
                return Result.ok(avatarUrl);
            } else {
                return Result.fail("更新头像失败");
            }
        } catch (Exception e) {
            log.error("上传头像失败, adminId: {}", adminId, e);
            return Result.fail("上传头像失败，系统内部错误");
        }
    }

    /**
     * 刷新管理员头像URL
     * 当头像URL过期时调用此方法重新生成
     */
    @Override
    public Result refreshAvatarUrl(Long adminId) {
        try {
            // 1. 查询管理员信息
            Admin admin = this.getById(adminId);
            if (admin == null) {
                return Result.fail("管理员不存在");
            }

            // 2. 检查是否有头像URL
            if (admin.getAvatar() == null || admin.getAvatar().isEmpty()) {
                return Result.fail("该管理员没有上传头像");
            }

            // 3. 从URL中提取对象名
            String objectName = extractObjectNameFromUrl(admin.getAvatar());
            if (objectName == null) {
                return Result.fail("无法解析头像文件信息");
            }

            // 4. 检查MinIO中文件是否存在
            if (!minioUrlService.objectExists(objectName)) {
                return Result.fail("头像文件不存在，请重新上传");
            }

            // 5. 重新生成URL
            String newAvatarUrl = minioUrlService.generatePresignedUrl(objectName);

            // 6. 更新数据库
            Admin updateAdmin = new Admin();
            updateAdmin.setId(adminId);
            updateAdmin.setAvatar(newAvatarUrl);
            boolean success = this.updateById(updateAdmin);

            if (success) {
                return Result.ok(newAvatarUrl);
            } else {
                return Result.fail("更新头像URL失败");
            }
        } catch (Exception e) {
            log.error("刷新头像URL失败, adminId: {}", adminId, e);
            return Result.fail("刷新头像URL失败，系统内部错误");
        }
    }

    /**
     * 从URL中提取对象名称
     */
    private String extractObjectNameFromUrl(String url) {
        try {
            // 假设URL格式为: http://127.0.0.1:9005/tjufood/objectName?params
            String bucketName = "tjufood"; // 从配置中获取
            String pattern = "/" + bucketName + "/";

            int startIndex = url.indexOf(pattern);
            if (startIndex > 0) {
                startIndex += pattern.length();
                int endIndex = url.indexOf('?', startIndex);
                if (endIndex > 0) {
                    return url.substring(startIndex, endIndex);
                } else {
                    return url.substring(startIndex);
                }
            }
            return null;
        } catch (Exception e) {
            log.error("提取对象名称失败: {}", url, e);
            return null;
        }
    }
}