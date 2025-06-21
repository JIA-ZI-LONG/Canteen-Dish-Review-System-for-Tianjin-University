package xyz.tjucomments.tjufood.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.entity.Stall;
import xyz.tjucomments.tjufood.entity.StallReview;

public interface IStallService extends IService<Stall> {
    // 保留返回 DTO 的自定义方法
    Result queryStallById(Long id);
    Result listStallsByCanteenId(Long canteenId);
    /**
     * 重新计算并更新所有窗口的评分
     */
    void recalculateAllStallScores();

    /**
     * 【新增】封装业务：添加一条窗口评价，并立即更新该窗口的评分
     * @param review 评价实体
     * @return 操作结果，成功则返回评价ID
     */
    Result addReviewAndUpdateScore(StallReview review);
}