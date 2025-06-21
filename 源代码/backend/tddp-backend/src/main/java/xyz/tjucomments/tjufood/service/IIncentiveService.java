package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.dto.Result;

/**
 * 激励服务接口
 * 负责处理用户行为的积分奖励逻辑
 */
public interface IIncentiveService {

    /**
     * 处理用户行为奖励
     * @param userId 用户ID
     * @param actionType 行为类型
     * @param description 描述
     * @return 奖励结果
     */
    Result processUserAction(Long userId, String actionType, String description);

    /**
     * 检查用户今日是否已达到某行为的奖励上限
     * @param userId 用户ID
     * @param actionType 行为类型
     * @return 是否已达上限
     */
    boolean hasReachedDailyLimit(Long userId, String actionType);

    /**
     * 获取用户今日某行为的奖励次数
     * @param userId 用户ID
     * @param actionType 行为类型
     * @return 今日奖励次数
     */
    int getTodayActionCount(Long userId, String actionType);

    /**
     * 获取激励规则统计信息
     * @return 统计信息
     */
    Result getIncentiveStatistics();
}
