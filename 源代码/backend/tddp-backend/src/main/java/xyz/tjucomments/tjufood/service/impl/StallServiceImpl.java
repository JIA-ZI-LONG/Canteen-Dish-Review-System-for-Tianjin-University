// 文件路径: yxq/back/src/main/java/xyz/tjucomments/tjufood/service/impl/StallServiceImpl.java
// (修正后的版本)
package xyz.tjucomments.tjufood.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.slf4j.Logger;                       // <<< 新增导入
import org.slf4j.LoggerFactory;            // <<< 新增导入
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.dto.StallDTO;
import xyz.tjucomments.tjufood.entity.Stall;
import xyz.tjucomments.tjufood.entity.StallReview;
import xyz.tjucomments.tjufood.mapper.StallMapper;
import xyz.tjucomments.tjufood.service.IStallReviewService;
import xyz.tjucomments.tjufood.service.IStallService;
import java.util.List;
// 移除了多余的 lombok.extern.slf4j.Slf4j 导入

@Service
public class StallServiceImpl extends ServiceImpl<StallMapper, Stall> implements IStallService {

    // 手动创建 log 对象
    private static final Logger log = LoggerFactory.getLogger(StallServiceImpl.class);

    @Resource
    private IStallReviewService stallReviewService;

    @Override
    @Transactional
    public void recalculateAllStallScores() {
        log.info("开始执行所有窗口评分的重算任务...");
        List<Stall> allStalls = this.list();
        for (Stall stall : allStalls) {
            List<StallReview> reviews = stallReviewService.lambdaQuery().eq(StallReview::getStallId, stall.getId()).list();
            if (reviews.isEmpty()) {
                continue;
            }

            double avgOverall = reviews.stream().mapToInt(StallReview::getOverallScore).average().orElse(0.0);

            Stall updatedStall = new Stall();
            updatedStall.setId(stall.getId());
            updatedStall.setScore(avgOverall);
            updatedStall.setComments(reviews.size());

            this.updateById(updatedStall);
        }
        log.info("所有窗口评分重算任务完成。");
    }

    @Override
    public Result queryStallById(Long id) {
        StallDTO stall = baseMapper.queryStallById(id);
        if (stall == null) {
            return Result.fail("窗口不存在！");
        }
        return Result.ok(stall);
    }

    @Override
    public Result listStallsByCanteenId(Long canteenId) {
        List<StallDTO> stalls = baseMapper.listStallsByCanteenId(canteenId);
        return Result.ok(stalls);
    }

    @Override
    @Transactional
    public Result addReviewAndUpdateScore(StallReview review) {
        // 步骤1: 保存评价 (逻辑不变)
        Result addReviewResult = stallReviewService.addReview(review);
        if (!addReviewResult.getSuccess()) {
            return addReviewResult;
        }

        // 【性能优化】步骤2: 使用增量方式原子性更新分数
        boolean success = this.update()
                .setSql("comments = comments + 1")
                .setSql("total_score = total_score + " + review.getOverallScore())
                .setSql("total_taste_score = total_taste_score + " + review.getTasteScore())
                .setSql("total_environment_score = total_environment_score + " + review.getEnvironmentScore())
                .setSql("total_service_score = total_service_score + " + review.getServiceScore())
                .setSql("total_price_score = total_price_score + " + review.getPriceScore())
                // 实时计算平均分
                .setSql("score = (total_score + " + review.getOverallScore() + ") / (comments + 1)")
                .setSql("taste_score = (total_taste_score + " + review.getTasteScore() + ") / (comments + 1)")
                .setSql("environment_score = (total_environment_score + " + review.getEnvironmentScore() + ") / (comments + 1)")
                .setSql("service_score = (total_service_score + " + review.getServiceScore() + ") / (comments + 1)")
                .setSql("price_score = (total_price_score + " + review.getPriceScore() + ") / (comments + 1)")
                .eq("id", review.getStallId())
                .update();

        if (!success) {
            // 如果更新失败，可以记录日志或抛出异常让事务回滚
            log.error("增量更新窗口评分失败，StallId: {}", review.getStallId());
            throw new RuntimeException("更新窗口评分失败");
        }

        return addReviewResult;
    }



}