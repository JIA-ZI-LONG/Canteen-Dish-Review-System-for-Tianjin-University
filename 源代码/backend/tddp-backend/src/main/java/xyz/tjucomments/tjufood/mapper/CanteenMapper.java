package xyz.tjucomments.tjufood.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import xyz.tjucomments.tjufood.entity.Canteen;
import org.apache.ibatis.annotations.Param;
import xyz.tjucomments.tjufood.dto.CanteenDTO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page; // 确保导入
import java.util.List;

/**
 * 食堂Mapper接口 (已重写并适配 MyBatis-Plus)
 */
@Mapper
public interface CanteenMapper extends BaseMapper<Canteen> {

    // ========== MyBatis-Plus 提供的通用方法 (无需编写) ==========
    // insert(), deleteById(), updateById(), selectById(), selectList() ...
    // 已替代了原有的 insertCanteen, updateCanteen, deleteCanteen 方法

    // ========== 保留的自定义查询方法 (返回 DTO) ==========
    // 这些方法通常涉及多表连接，需要自定义SQL，因此予以保留

    /**
     * 查询食堂列表 (返回包含额外信息的DTO)
     * @return 食堂DTO列表
     */
    List<CanteenDTO> listCanteens(@Param("campusId") Long campusId);

    /**
     * 根据ID查询单个食堂详情 (返回包含额外信息的DTO)
     * @param id 食堂ID
     * @return 食堂DTO
     */
    CanteenDTO queryCanteenById(Long id);
    /**
     * 后台管理端分页查询食堂信息
     * @param page 分页参数
     * @param name 食堂名称 (可选查询条件)
     * @param campusId 校区ID (可选查询条件)
     * @return
     */
    IPage<CanteenDTO> selectAdminPage(IPage<?> page,
                                      @Param("name") String name,
                                      @Param("campusId") Integer campusId);
    // 【新增】声明后台专用的分页查询方法
    Page<CanteenDTO> selectAdminPage(Page<Canteen> page);
}