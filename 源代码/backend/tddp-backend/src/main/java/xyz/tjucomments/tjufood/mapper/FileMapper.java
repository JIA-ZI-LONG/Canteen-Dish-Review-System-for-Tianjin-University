// 文件路径: src/main/java/xyz/tjucomments/tjufood/mapper/FileMapper.java

package xyz.tjucomments.tjufood.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.tjucomments.tjufood.entity.File;

import java.util.List;

@Mapper
public interface FileMapper extends BaseMapper<File> {

    /**
     * 根据实体ID和类型查询文件列表
     * @param entityId 实体ID
     * @param typeId 文件类型
     * @return 文件列表
     */
    List<File> selectByEntityIdAndType(@Param("entityId") Long entityId, @Param("typeId") Integer typeId);

    /**
     * 根据类型查询所有文件
     * @param typeId 文件类型
     * @return 文件列表
     */
    List<File> selectByType(@Param("typeId") Integer typeId);

    /**
     * 查询所有有MinIO对象名的文件（用于定时刷新URL）
     * @return 文件列表
     */
    List<File> selectAllWithObjectName();

    /**
     * 批量更新文件状态
     * @param ids 文件ID列表
     * @param status 新状态
     * @return 更新数量
     */
    int updateStatusBatch(@Param("ids") List<Long> ids, @Param("status") Integer status);

    /**
     * 删除指定实体的所有文件
     * @param entityId 实体ID
     * @param typeId 文件类型
     * @return 删除数量
     */
    int deleteByEntityIdAndType(@Param("entityId") Long entityId, @Param("typeId") Integer typeId);
}
