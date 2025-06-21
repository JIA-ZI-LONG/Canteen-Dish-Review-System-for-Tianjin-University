package xyz.tjucomments.tjufood.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.dto.CanteenDTO;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.entity.Canteen;
import xyz.tjucomments.tjufood.mapper.CanteenMapper;
import xyz.tjucomments.tjufood.service.ICanteenService;
import xyz.tjucomments.tjufood.utils.cache.CacheClient;
import xyz.tjucomments.tjufood.utils.constants.RedisConstants;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CanteenServiceImpl extends ServiceImpl<CanteenMapper, Canteen> implements ICanteenService {

    @Resource
    private CacheClient cacheClient;

    // 您已注入了 CanteenMapper, 我们将使用它来调用自定义方法
    @Resource
    private CanteenMapper canteenMapper;

    @Override
    public Result listCanteens(Long campusId) {
        // 【修正】调用自定义SQL应该使用注入的canteenMapper，而不是baseMapper
        List<CanteenDTO> canteens = canteenMapper.listCanteens(campusId);
        return Result.ok(canteens);
    }

    @Override
    public Result queryCanteenById(Long id) {
        CanteenDTO canteen = cacheClient.queryWithPassThrough(
                RedisConstants.CACHE_CANTEEN_KEY,
                id,
                CanteenDTO.class,
                // 【修正】这里同样使用canteenMapper来调用自定义方法
                canteenId -> canteenMapper.queryCanteenById(canteenId),
                RedisConstants.CACHE_CANTEEN_TTL,
                TimeUnit.MINUTES
        );
        if (canteen == null) {
            return Result.fail("食堂不存在！");
        }
        return Result.ok(canteen);
    }

    /**
     * 【修改】实现后台分页查询逻辑，并与接口定义保持一致
     */
    @Override
    public Result pageQueryForAdmin(Integer current, Integer size) {
        // 1. 创建 MyBatis-Plus 分页对象
        Page<CanteenDTO> page = new Page<>(current, size);

        // 2. 调用 Mapper 方法。由于不需要搜索，将搜索参数传 null
        // 我们之前写的 CanteenMapper.xml 中的动态SQL会自动忽略 null 的参数
        IPage<CanteenDTO> pageResult = canteenMapper.selectAdminPage(page, null, null);

        // 3. 返回OK的结果
        return Result.ok(pageResult);
    }
}