// src/main/java/xyz/tjucomments/tjufood/service/IFileService.java
package xyz.tjucomments.tjufood.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.entity.File;

import java.util.List;

/**
 * 文件管理服务接口
 */
public interface IFileService extends IService<File> {

    /**
     * 上传图片文件（原有方法保持兼容）
     * @param imageFile 图片文件
     * @return 包含图片URL的Result对象
     */
    Result uploadImage(MultipartFile imageFile);

    /**
     * 上传文件到指定实体
     * @param file 文件
     * @param entityId 关联实体ID
     * @param typeId 文件类型 (0=食堂, 1=校区, 2=窗口, 3=菜品, 4=轮播图)
     * @param description 文件描述
     * @return 操作结果
     */
    Result uploadEntityFile(MultipartFile file, Long entityId, Integer typeId, String description);

    /**
     * 批量上传文件
     * @param files 文件列表
     * @param entityId 关联实体ID
     * @param typeId 文件类型
     * @return 操作结果
     */
    Result uploadEntityFiles(List<MultipartFile> files, Long entityId, Integer typeId);

    /**
     * 根据实体ID和类型查询文件列表
     * @param entityId 实体ID
     * @param typeId 文件类型
     * @return 文件列表
     */
    Result getFilesByEntityAndType(Long entityId, Integer typeId);

    /**
     * 根据类型查询所有文件
     * @param typeId 文件类型
     * @return 文件列表
     */
    Result getFilesByType(Integer typeId);

    /**
     * 删除文件
     * @param fileId 文件ID
     * @return 操作结果
     */
    Result deleteEntityFile(Long fileId);

    /**
     * 刷新文件URL
     * @param fileId 文件ID
     * @return 操作结果
     */
    Result refreshFileUrl(Long fileId);

    /**
     * 批量刷新实体文件URL
     * @param entityId 实体ID
     * @param typeId 文件类型
     * @return 操作结果
     */
    Result refreshEntityFileUrls(Long entityId, Integer typeId);
}