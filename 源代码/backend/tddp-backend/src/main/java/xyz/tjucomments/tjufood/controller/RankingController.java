package xyz.tjucomments.tjufood.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.service.IRankingService;

@Tag(name = "12. 排行榜功能", description = "提供各类排行榜数据的接口")
@RestController
@RequestMapping("/api/rankings")
public class RankingController {

    @Resource
    private IRankingService rankingService;

    @Operation(summary = "获取食堂排行榜")
    @GetMapping("/canteens")
    public Result getCanteenRanking(
            @Parameter(description = "排序方式 (score=评分, likes=点赞数, comments=评论数)") 
            @RequestParam(value = "sortBy", defaultValue = "score") String sortBy,
            @Parameter(description = "返回数量") 
            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        return rankingService.getCanteenRanking(sortBy, limit);
    }

    @Operation(summary = "获取菜品排行榜")
    @GetMapping("/dishes")
    public Result getDishRanking(
            @Parameter(description = "排序方式 (score=评分, likes=点赞数, sales=销量)") 
            @RequestParam(value = "sortBy", defaultValue = "score") String sortBy,
            @Parameter(description = "返回数量") 
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @Parameter(description = "食堂ID过滤") 
            @RequestParam(value = "canteenId", required = false) Long canteenId) {
        return rankingService.getDishRanking(sortBy, limit, canteenId);
    }

    @Operation(summary = "获取博客排行榜")
    @GetMapping("/blogs")
    public Result getBlogRanking(
            @Parameter(description = "排序方式 (likes=点赞数, comments=评论数, views=浏览量)") 
            @RequestParam(value = "sortBy", defaultValue = "likes") String sortBy,
            @Parameter(description = "返回数量") 
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @Parameter(description = "时间范围 (week=本周, month=本月, all=全部)") 
            @RequestParam(value = "timeRange", defaultValue = "all") String timeRange) {
        return rankingService.getBlogRanking(sortBy, limit, timeRange);
    }

    @Operation(summary = "获取用户排行榜")
    @GetMapping("/users")
    public Result getUserRanking(
            @Parameter(description = "排序方式 (credits=积分, blogs=博客数, likes=获赞数)") 
            @RequestParam(value = "sortBy", defaultValue = "credits") String sortBy,
            @Parameter(description = "返回数量") 
            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        return rankingService.getUserRanking(sortBy, limit);
    }

    @Operation(summary = "获取综合排行榜数据")
    @GetMapping("/summary")
    public Result getRankingSummary() {
        return rankingService.getRankingSummary();
    }
}
