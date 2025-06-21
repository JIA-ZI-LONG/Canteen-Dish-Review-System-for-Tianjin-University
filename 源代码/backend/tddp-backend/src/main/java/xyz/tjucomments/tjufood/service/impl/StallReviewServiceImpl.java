// 文件路径: src/main/java/xyz/tjucomments/tjufood/service/impl/StallReviewServiceImpl.java
package xyz.tjucomments.tjufood.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.dto.UserDTO;
import xyz.tjucomments.tjufood.entity.StallReview;
import xyz.tjucomments.tjufood.interceptor.UserHolder;
import xyz.tjucomments.tjufood.mapper.StallReviewMapper;
import xyz.tjucomments.tjufood.service.IStallReviewService;
import xyz.tjucomments.tjufood.utils.constants.IdPrefixConstants;
import xyz.tjucomments.tjufood.utils.constants.SystemConstants;
import xyz.tjucomments.tjufood.utils.id.RedisIdWorker;

@Service
public class StallReviewServiceImpl extends ServiceImpl<StallReviewMapper, StallReview> implements IStallReviewService {

    @Resource
    private RedisIdWorker redisIdWorker;

    // 【关键修改】确保这里没有对 IStallService 的注入

    @Override
    @Transactional
    public Result addReview(StallReview review) {
        UserDTO currentUser = UserHolder.getUser();
        if (currentUser == null) {
            return Result.fail("请先登录！");
        }
        review.setUserId(currentUser.getId());
        review.setId(redisIdWorker.nextId(IdPrefixConstants.REVIEW_ID_PREFIX));

        // 只负责保存评价，不再有其他操作
        boolean isSuccess = save(review);
        if (!isSuccess) {
            return Result.fail("评价失败！");
        }
        return Result.ok(review.getId());
    }

    @Override
    public Result queryReviewsByStallId(Long stallId, Integer current) {
        Page<StallReview> page = page(new Page<>(current, SystemConstants.DEFAULT_PAGE_SIZE),
                new QueryWrapper<StallReview>().eq("stall_id", stallId).orderByDesc("create_time"));
        return Result.ok(page.getRecords(), page.getTotal());
    }

    // 【关键修改】确保整个 updateStallScores 方法已被删除
}