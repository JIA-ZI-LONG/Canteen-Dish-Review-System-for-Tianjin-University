package xyz.tjucomments.tjufood.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.tjucomments.tjufood.config.RabbitMQConfig; // 引入RabbitMQ配置
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.entity.UserVoucher;
import xyz.tjucomments.tjufood.entity.Voucher;
import xyz.tjucomments.tjufood.interceptor.UserHolder;
import xyz.tjucomments.tjufood.mapper.VoucherMapper;
import xyz.tjucomments.tjufood.service.IUserVoucherService;
import xyz.tjucomments.tjufood.service.IVoucherService;
import xyz.tjucomments.tjufood.utils.constants.IdPrefixConstants;
import xyz.tjucomments.tjufood.utils.id.RedisIdWorker;
import org.springframework.amqp.rabbit.core.RabbitTemplate; // 引入RabbitTemplate
import xyz.tjucomments.tjufood.entity.User; // 引入User实体
import xyz.tjucomments.tjufood.service.IUserService; // 引入UserService

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class VoucherServiceImpl extends ServiceImpl<VoucherMapper, Voucher> implements IVoucherService {

    @Resource
    private IUserVoucherService userVoucherService;
    @Resource
    private RedisIdWorker redisIdWorker;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RabbitTemplate rabbitTemplate; // 注入RabbitMQ模板
    @Resource
    private IUserService userService; // 注入用户服务

    private static final DefaultRedisScript<Long> SECKILL_SCRIPT;

    // 加载我们新编写的Lua脚本
    static {
        SECKILL_SCRIPT = new DefaultRedisScript<>();
        SECKILL_SCRIPT.setLocation(new ClassPathResource("lua/seckill.lua"));
        SECKILL_SCRIPT.setResultType(Long.class);
    }

    // 缓存预热：在服务启动时，将所有秒杀券的库存加载到Redis
    // 缓存预热：在服务启动时，将所有秒杀券的库存加载到Redis
    @PostConstruct
    private void init() {
        // 【核心修正】直接使用 baseMapper 进行查询，确保 SQL Server 分页插件能正确工作
        List<Voucher> vouchers = baseMapper.selectList(
                new QueryWrapper<Voucher>().eq("status", 1).orderByDesc("id")
        );

        if (vouchers == null || vouchers.isEmpty()) {
            log.info("没有需要预热的秒杀券。");
            return;
        }

        for (Voucher voucher : vouchers) {
            // 确保 stock 不为 null，否则会报错。根据您的表设计，此字段允许NULL。
            Integer stock = voucher.getStock();
            if (stock != null) {
                String stockKey = "seckill:stock:" + voucher.getId();
                stringRedisTemplate.opsForValue().set(stockKey, stock.toString());
                log.info("预热秒杀券库存: VoucherId={}, Stock={}", voucher.getId(), stock);
            }
        }
    }

    @Override
    public Result queryVouchersByStallId(Long stallId) {
        List<Voucher> vouchers = query().eq("status", 1).list();
        // 同步 Redis 最新库存到返回对象，保证前端实时看到递减效果
        for (Voucher v : vouchers) {
            v.setStock(getCurrentStock(v.getId(), v.getStock()));
        }
        return Result.ok(vouchers);
    }

    @Override
    public Result redeemVoucher(Long voucherId) {
        // 1. 【新增】查询优惠券信息
        Voucher voucher = getById(voucherId);
        if (voucher == null) {
            return Result.fail("优惠券不存在！");
        }
        // 检查秒杀是否开始或已结束
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(voucher.getStartTime()) || now.isAfter(voucher.getEndTime())) {
            return Result.fail("活动未开始或已结束！");
        }

        Long userId = UserHolder.getUser().getId();
        if (userId == null) {
            return Result.fail("请先登录");
        }

        // 2. 【新增】校验用户积分
        User user = userService.getById(userId);
        if (user.getCredits() < voucher.getRequiredCredits()) {
            return Result.fail("积分不足，无法兑换！");
        }

        long orderId = redisIdWorker.nextId(IdPrefixConstants.ORDER_ID_PREFIX);

        try {
            // 3. 执行Lua脚本 (这部分逻辑不变)
            Long result = stringRedisTemplate.execute(
                    SECKILL_SCRIPT,
                    List.of("seckill:stock:" + voucherId, "seckill:order:" + voucherId),
                    userId.toString()
            );

            if (result == null) {
                log.error("Lua脚本执行返回null, VoucherId: {}", voucherId);
                return Result.fail("系统繁忙，请稍后再试");
            }

            int r = result.intValue();
            if (r != 0) {
                return Result.fail(r == 1 ? "库存不足" : "您已经抢过了哦");
            }

            // 4. 发送到RabbitMQ (这部分逻辑不变)
            Map<String, Object> orderMessage = new HashMap<>(); // 【修正】Value类型改为Object以兼容积分
            orderMessage.put("voucherId", voucherId);
            orderMessage.put("userId", userId);
            orderMessage.put("orderId", orderId);
            orderMessage.put("requiredCredits", voucher.getRequiredCredits()); // 【新增】将所需积分也放入消息

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.SECKILL_EXCHANGE,
                    RabbitMQConfig.SECKILL_ROUTING_KEY,
                    orderMessage
            );
            log.info("秒杀请求成功，订单信息已发送到队列, OrderID: {}", orderId);

            // 5. 返回订单ID
            return Result.ok(orderId);

        } catch (Exception e) {
            log.error("抢券过程中发生未知异常, VoucherId: " + voucherId, e);
            return Result.fail("系统繁忙，请稍后再试");
        }
    }

    // 【重要】createVoucherOrder方法需要修改以接收所需积分
    // 【核心修正】确保这个方法的签名与接口完全一致
    @Override
    @Transactional
    public Result createVoucherOrder(Long voucherId, Long userId, Integer requiredCredits) {
        // 1. 乐观锁扣减优惠券库存
        boolean success = update()
                .setSql("stock = stock - 1")
                .eq("id", voucherId)
                .gt("stock", 0) // where stock > 0
                .update();

        if (!success) {
            // 注意：在异步监听器中，不应返回Result，而应抛出异常来触发消息重试或进入死信队列
            throw new RuntimeException("数据库优惠券库存扣减失败！VoucherId: " + voucherId);
        }

        // 2. 扣减用户积分
        if (requiredCredits != null && requiredCredits > 0) {
            boolean creditSuccess = userService.update()
                    .setSql("credits = credits - " + requiredCredits)
                    .eq("id", userId)
                    .ge("credits", requiredCredits) // 确保积分足够
                    .update();
            if (!creditSuccess) {
                // 如果积分扣减失败，抛出异常让整个事务回滚
                throw new RuntimeException("用户积分扣减失败，回滚库存操作。UserID: " + userId);
            }
        }

        // 3. 创建用户优惠券记录
        UserVoucher userVoucher = new UserVoucher();
        // 注意：订单ID应该从消息中获取，而不是重新生成，以保证链路一致性
        // 这里暂时保持原样，但实际生产中建议从消息中传递orderId
        userVoucher.setId(redisIdWorker.nextId(IdPrefixConstants.ORDER_ID_PREFIX));
        userVoucher.setUserId(userId);
        userVoucher.setVoucherId(voucherId);
        userVoucher.setStatus(0); // 0=未使用
        userVoucherService.save(userVoucher);

        // 在Service的事务方法中，成功执行完就代表成功，可以不返回Result，或者返回一个表示成功的内部对象
        // 这里为了兼容之前的代码，我们返回OK
        return Result.ok();
    }

    @Override
    public Result queryMyVouchers() {
        Long userId = UserHolder.getUser().getId();
        List<UserVoucher> vouchers = userVoucherService.query().eq("user_id", userId).list();
        return Result.ok(vouchers);
    }

    @Override
    public Result searchVouchers(Integer current, Integer size, Integer type, Long canteenId, Integer status) {
        // 【核心修正】添加排序条件，避免SQL Server分页问题
        QueryWrapper<Voucher> queryWrapper = new QueryWrapper<>();
        if (type != null) {
            queryWrapper.eq("type", type);
        }
        if (canteenId != null) {
            queryWrapper.eq("canteen_id", canteenId);
        }
        if (status != null) {
            queryWrapper.eq("status", status);
        }
        // 添加排序条件
        queryWrapper.orderByDesc("create_time");

        Page<Voucher> page = page(new Page<>(current, size), queryWrapper);

        // 同步 Redis 库存，前端实时展示剩余数量
        List<Voucher> records = page.getRecords();
        for (Voucher v : records) {
            v.setStock(getCurrentStock(v.getId(), v.getStock()));
        }

        // 将分页结果包装成 Map，以匹配前端期望的 { records: [], total: x } 格式
        Map<String, Object> resultData = new HashMap<>();
        resultData.put("records", records);
        resultData.put("total", page.getTotal());

        return Result.ok(resultData);
    }

    /**
     * 获取最新库存：优先读取 Redis 中的实时库存值，若不存在则回退至数据库字段。
     */
    private int getCurrentStock(Long voucherId, Integer dbStock) {
        String stockKey = "seckill:stock:" + voucherId;
        String stockStr = stringRedisTemplate.opsForValue().get(stockKey);
        if (stockStr != null) {
            try {
                return Integer.parseInt(stockStr);
            } catch (NumberFormatException ignored) {
            }
        }
        return dbStock == null ? 0 : dbStock;
    }
}