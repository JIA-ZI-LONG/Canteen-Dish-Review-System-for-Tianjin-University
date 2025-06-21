package xyz.tjucomments.tjufood.mapper;

// 导入 Page 类
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.tjucomments.tjufood.entity.Dish;
import xyz.tjucomments.tjufood.dto.DishDTO;
import java.util.List;

/**
 * 菜品Mapper接口 (已重写并适配 MyBatis-Plus)
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {

    // ========== 保留的自定义查询方法 ==========

    /**
     * 根据ID查询菜品详情 (返回DTO)
     */
    DishDTO queryDishById(Long id);

    /**
     * 根据窗口ID查询其下所有菜品 (返回DTO列表)
     */
    List<DishDTO> listDishesByStallId(@Param("stallId") Long stallId);

    /**
     * 【已修改】根据菜品名称模糊搜索菜品 (返回DTO列表，并支持分页)
     * * @param page 分页对象，必须作为第一个参数
     * @param name 菜品名称关键词
     * @return 分页后的菜品DTO列表
     */
    List<DishDTO> searchDishesByName(Page<DishDTO> page, @Param("name") String name);

    /**
     * 【新增】根据菜品名称模糊搜索菜品，支持自定义排序
     * @param page 分页对象，必须作为第一个参数
     * @param name 菜品名称关键词
     * @param sortBy 排序字段
     * @param sortOrder 排序方向
     * @return 分页后的菜品DTO列表
     */
    List<DishDTO> searchDishesByNameWithSort(Page<DishDTO> page,
                                           @Param("name") String name,
                                           @Param("campusId") Long campusId,
                                           @Param("canteenId") Long canteenId,
                                           @Param("sortBy") String sortBy,
                                           @Param("sortOrder") String sortOrder);

    /**
     * 【新增】获取菜品排行榜，支持按食堂ID过滤和自定义排序
     * @param canteenId 食堂ID，可为null
     * @param sortBy 排序字段
     * @param limit 限制数量
     * @return 菜品DTO列表
     */
    List<DishDTO> getDishRankingWithJoin(@Param("canteenId") Long canteenId,
                                        @Param("sortBy") String sortBy,
                                        @Param("limit") Integer limit);
}
