package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.dto.Result;

/**
 * 排行榜服务接口
 */
public interface IRankingService {

    /**
     * 获取食堂排行榜
     * @param sortBy 排序方式 (score=评分, likes=点赞数, comments=评论数)
     * @param limit 返回数量
     * @return 食堂排行榜数据
     */
    Result getCanteenRanking(String sortBy, Integer limit);

    /**
     * 获取菜品排行榜
     * @param sortBy 排序方式 (score=评分, likes=点赞数, sales=销量)
     * @param limit 返回数量
     * @param canteenId 食堂ID过滤
     * @return 菜品排行榜数据
     */
    Result getDishRanking(String sortBy, Integer limit, Long canteenId);

    /**
     * 获取博客排行榜
     * @param sortBy 排序方式 (likes=点赞数, comments=评论数, views=浏览量)
     * @param limit 返回数量
     * @param timeRange 时间范围 (week=本周, month=本月, all=全部)
     * @return 博客排行榜数据
     */
    Result getBlogRanking(String sortBy, Integer limit, String timeRange);

    /**
     * 获取用户排行榜
     * @param sortBy 排序方式 (credits=积分, blogs=博客数, likes=获赞数)
     * @param limit 返回数量
     * @return 用户排行榜数据
     */
    Result getUserRanking(String sortBy, Integer limit);

    /**
     * 获取综合排行榜数据
     * @return 综合排行榜数据
     */
    Result getRankingSummary();
}
