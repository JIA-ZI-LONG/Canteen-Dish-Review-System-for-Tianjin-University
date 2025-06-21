package xyz.tjucomments.tjufood.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.entity.CreditRecord;
import xyz.tjucomments.tjufood.entity.IncentiveRule;
import xyz.tjucomments.tjufood.entity.User;
import xyz.tjucomments.tjufood.mapper.UserMapper;
import xyz.tjucomments.tjufood.service.*;
import xyz.tjucomments.tjufood.utils.id.RedisIdWorker;

import jakarta.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 激励服务实现类
 */
@Slf4j
@Service
public class IncentiveServiceImpl implements IIncentiveService {

    @Resource
    private IIncentiveRuleService incentiveRuleService;

    @Resource
    private ICreditRecordService creditRecordService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisIdWorker redisIdWorker;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    @Transactional
    public Result processUserAction(Long userId, String actionType, String description) {
        try {
            // 1. 查询激励规则
            IncentiveRule rule = incentiveRuleService.getOne(
                new QueryWrapper<IncentiveRule>()
                    .eq("action_type", actionType)
                    .eq("status", 1) // 启用状态
            );

            if (rule == null) {
                log.debug("未找到行为类型 {} 的激励规则", actionType);
                return Result.ok("该行为暂无积分奖励");
            }

            // 2. 检查每日限制
            if (hasReachedDailyLimit(userId, actionType)) {
                log.debug("用户 {} 今日行为 {} 已达奖励上限", userId, actionType);
                return Result.ok("今日该行为奖励已达上限");
            }

            // 3. 给用户增加积分 - 直接使用Mapper避免循环依赖
            int updateCount = userMapper.updateCredits(userId, rule.getCredits());
            if (updateCount == 0) {
                return Result.fail("积分更新失败");
            }

            // 4. 记录积分流水
            CreditRecord record = new CreditRecord();
            record.setId(redisIdWorker.nextId("credit"));
            record.setUserId(userId);
            record.setActionType(actionType);
            record.setCredits(rule.getCredits());
            record.setDescription(description);
            record.setCreateTime(LocalDateTime.now());
            creditRecordService.save(record);

            // 5. 更新Redis中的今日计数
            String key = "incentive:daily:" + userId + ":" + actionType + ":" + LocalDate.now();
            stringRedisTemplate.opsForValue().increment(key);
            // 设置过期时间为明天凌晨
            LocalDateTime tomorrowStart = LocalDate.now().plusDays(1).atStartOfDay();
            Date expireDate = Date.from(tomorrowStart.atZone(ZoneId.systemDefault()).toInstant());
            stringRedisTemplate.expireAt(key, expireDate);

            log.info("用户 {} 完成行为 {} 获得 {} 积分", userId, actionType, rule.getCredits());
            return Result.ok("获得 " + rule.getCredits() + " 积分奖励！");

        } catch (Exception e) {
            log.error("处理用户行为奖励失败", e);
            return Result.fail("积分奖励处理失败");
        }
    }

    @Override
    public boolean hasReachedDailyLimit(Long userId, String actionType) {
        // 查询激励规则的每日限制
        IncentiveRule rule = incentiveRuleService.getOne(
            new QueryWrapper<IncentiveRule>()
                .eq("action_type", actionType)
                .eq("status", 1)
        );

        if (rule == null || rule.getDailyLimit() == null || rule.getDailyLimit() <= 0) {
            return false; // 无限制
        }

        int todayCount = getTodayActionCount(userId, actionType);
        return todayCount >= rule.getDailyLimit();
    }

    @Override
    public int getTodayActionCount(Long userId, String actionType) {
        String key = "incentive:daily:" + userId + ":" + actionType + ":" + LocalDate.now();
        String count = stringRedisTemplate.opsForValue().get(key);
        return count != null ? Integer.parseInt(count) : 0;
    }

    @Override
    public Result getIncentiveStatistics() {
        try {
            Map<String, Object> statistics = new HashMap<>();

            // 统计激励规则数量
            long totalRules = incentiveRuleService.count();
            long activeRules = incentiveRuleService.count(new QueryWrapper<IncentiveRule>().eq("status", 1));

            // 统计今日积分发放情况
            LocalDateTime todayStart = LocalDate.now().atStartOfDay();
            LocalDateTime todayEnd = LocalDate.now().plusDays(1).atStartOfDay();
            
            List<CreditRecord> todayRecords = creditRecordService.list(
                new QueryWrapper<CreditRecord>()
                    .ge("create_time", todayStart)
                    .lt("create_time", todayEnd)
                    .gt("credits", 0) // 只统计获得积分的记录
            );

            int todayTotalCredits = todayRecords.stream()
                .mapToInt(CreditRecord::getCredits)
                .sum();

            statistics.put("totalRules", totalRules);
            statistics.put("activeRules", activeRules);
            statistics.put("todayRewardCount", todayRecords.size());
            statistics.put("todayTotalCredits", todayTotalCredits);

            return Result.ok(statistics);
        } catch (Exception e) {
            log.error("获取激励统计信息失败", e);
            return Result.fail("获取统计信息失败");
        }
    }
}
