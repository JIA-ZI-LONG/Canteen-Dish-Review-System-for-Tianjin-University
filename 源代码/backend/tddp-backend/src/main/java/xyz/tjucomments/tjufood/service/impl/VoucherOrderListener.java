package xyz.tjucomments.tjufood.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import xyz.tjucomments.tjufood.config.RabbitMQConfig;
import xyz.tjucomments.tjufood.interceptor.UserHolder;
import xyz.tjucomments.tjufood.dto.UserDTO;
import xyz.tjucomments.tjufood.service.IVoucherService;

import jakarta.annotation.Resource;
import java.util.Map;

@Slf4j
@Component
public class VoucherOrderListener {

    @Resource
    private IVoucherService voucherService;

    // 监听名为 "seckill.order.queue" 的队列
    @RabbitListener(queues = RabbitMQConfig.SECKILL_QUEUE)
    public void handleVoucherOrder(Map<String, Object> orderMessage) { // 【修正1】Value类型改为Object
        if (orderMessage == null) {
            log.error("接收到空的订单消息，已忽略。");
            return;
        }

        try {
            log.info("从队列接收到秒杀订单消息: {}", orderMessage);

            // 【修正2】从Map中安全地获取所有参数
            // 从JSON反序列化后，数字可能是Integer或Long，用Number接收最安全
            Long userId = ((Number) orderMessage.get("userId")).longValue();
            Long voucherId = ((Number) orderMessage.get("voucherId")).longValue();
            Integer requiredCredits = (Integer) orderMessage.get("requiredCredits");

            if (userId == null || voucherId == null || requiredCredits == null) {
                log.error("订单消息格式错误，缺少必要信息: {}", orderMessage);
                return;
            }

            // 在异步线程中处理业务，需要手动设置用户信息到UserHolder
            UserDTO userDTO = new UserDTO();
            userDTO.setId(userId);
            UserHolder.saveUser(userDTO);

            // 【修正3】调用拥有三个参数的正确方法
            voucherService.createVoucherOrder(voucherId, userId, requiredCredits);

            log.info("订单处理完成, VoucherID: {}, UserID: {}", voucherId, userId);

        } catch (Exception e) {
            log.error("处理秒杀订单消息时发生异常: {}", orderMessage, e);
            // 这里可以加入重试机制或记录到死信队列
        } finally {
            // 清理线程本地变量，防止内存泄漏
            UserHolder.removeUser();
        }
    }
}