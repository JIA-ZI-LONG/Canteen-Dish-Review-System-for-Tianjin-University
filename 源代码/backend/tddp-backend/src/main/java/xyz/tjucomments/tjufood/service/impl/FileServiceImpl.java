// src/main/java/xyz/tjucomments/tjufood/service/impl/FileServiceImpl.java
package xyz.tjucomments.tjufood.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.entity.File;
import xyz.tjucomments.tjufood.interceptor.AdminHolder;
import xyz.tjucomments.tjufood.mapper.FileMapper;
import xyz.tjucomments.tjufood.service.IFileService;
import xyz.tjucomments.tjufood.service.IFileStorageService;
import xyz.tjucomments.tjufood.service.IMinioUrlService;
import xyz.tjucomments.tjufood.utils.constants.IdPrefixConstants;
import xyz.tjucomments.tjufood.utils.id.RedisIdWorker;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {

    /**
     * 从 application.yaml 注入Nginx配置的图片访问URL前缀
     * 例如：http://your-server-ip/imgs/
     */
    @Value("${tjufood.image-access-url}")
    private String imageAccessUrl;

    /**
     * 从 application.yaml 注入文件上传的本地磁盘路径
     * 例如：D:/lesson/nginx-1.18.0/html/tjufood/imgs/
     */
    @Value("${tjufood.image-upload-dir}")
    private String uploadDir;

    @Resource
    private IFileStorageService fileStorageService;

    @Resource
    private IMinioUrlService minioUrlService;

    @Resource
    private RedisIdWorker redisIdWorker;

    @Override
    public Result uploadImage(MultipartFile imageFile) {
        // 1. 校验文件是否为空
        if (imageFile == null || imageFile.isEmpty()) {
            return Result.fail("上传的文件不能为空！");
        }

        // 2. 获取原始文件名
        String originalFilename = imageFile.getOriginalFilename();
        if (originalFilename == null) {
            return Result.fail("文件名异常！");
        }

        // 3. 生成新的唯一文件名，防止重名覆盖
        // 格式：UUID.后缀名
        String fileExtension = FileUtil.extName(originalFilename);
        String newFileName = UUID.randomUUID().toString(true) + "." + fileExtension;

        // 4. 创建目标保存文件对象
        java.io.File destFile = new java.io.File(uploadDir, newFileName);

        // 5. 确保父目录存在
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }

        try {
            // 6. 将上传的文件保存到目标位置
            imageFile.transferTo(destFile);
        } catch (IOException e) {
            log.error("文件上传失败", e);
            return Result.fail("文件上传失败，服务器内部错误！");
        }

        // 7. 拼接并返回可访问的URL
        String fileUrl = imageAccessUrl + newFileName;
        log.info("文件上传成功，访问URL: {}", fileUrl);
        return Result.ok(fileUrl);
    }

    @Override
    @Transactional
    public Result uploadEntityFile(MultipartFile file, Long entityId, Integer typeId, String description) {
        try {
            // 1. 校验参数
            if (file == null || file.isEmpty()) {
                return Result.fail("上传的文件不能为空");
            }
            if (entityId == null || typeId == null) {
                return Result.fail("实体ID和文件类型不能为空");
            }

            // 2. 校验文件类型
            if (!isValidFileType(file, typeId)) {
                return Result.fail("不支持的文件类型");
            }

            // 3. 生成文件对象名
            String originalFilename = file.getOriginalFilename();
            String fileExtension = FileUtil.extName(originalFilename);
            String objectName = generateObjectName(typeId, entityId, fileExtension);

            // 4. 上传到MinIO
            try (InputStream inputStream = file.getInputStream()) {
                fileStorageService.uploadFile(objectName, inputStream, file.getSize(), file.getContentType());
            }

            // 5. 生成访问URL
            String fileUrl = minioUrlService.generatePresignedUrl(objectName);

            // 6. 保存文件记录到数据库
            File fileEntity = new File();
            fileEntity.setId(redisIdWorker.nextId(IdPrefixConstants.FILE_ID_PREFIX));
            fileEntity.setEntityId(entityId);
            fileEntity.setTypeId(typeId);
            fileEntity.setUrl(fileUrl);
            fileEntity.setObjectName(objectName);
            fileEntity.setOriginalName(originalFilename);
            fileEntity.setFileSize(file.getSize());
            fileEntity.setContentType(file.getContentType());
            fileEntity.setDescription(description);
            fileEntity.setSortOrder(0);
            fileEntity.setStatus(1);
            fileEntity.setCreatorId(getCurrentAdminId());

            boolean success = this.save(fileEntity);
            if (success) {
                log.info("文件上传成功: entityId={}, typeId={}, objectName={}", entityId, typeId, objectName);
                return Result.ok(fileEntity);
            } else {
                return Result.fail("保存文件记录失败");
            }

        } catch (Exception e) {
            log.error("上传文件失败: entityId={}, typeId={}", entityId, typeId, e);
            return Result.fail("上传文件失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result uploadEntityFiles(List<MultipartFile> files, Long entityId, Integer typeId) {
        if (files == null || files.isEmpty()) {
            return Result.fail("文件列表不能为空");
        }

        List<File> uploadedFiles = new ArrayList<>();
        List<String> errorMessages = new ArrayList<>();

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String description = "批量上传文件 " + (i + 1);

            Result result = uploadEntityFile(file, entityId, typeId, description);
            if (result.getSuccess()) {
                uploadedFiles.add((File) result.getData());
            } else {
                errorMessages.add("文件" + (i + 1) + ": " + result.getErrorMsg());
            }
        }

        if (errorMessages.isEmpty()) {
            return Result.ok(uploadedFiles);
        } else if (uploadedFiles.isEmpty()) {
            return Result.fail("所有文件上传失败: " + String.join("; ", errorMessages));
        } else {
            // 部分成功的情况，返回成功的文件列表，但在日志中记录错误
            log.warn("部分文件上传失败: {}", String.join("; ", errorMessages));
            return Result.ok(uploadedFiles);
        }
    }

    @Override
    public Result getFilesByEntityAndType(Long entityId, Integer typeId) {
        try {
            List<File> files = baseMapper.selectByEntityIdAndType(entityId, typeId);
            return Result.ok(files);
        } catch (Exception e) {
            log.error("查询文件列表失败: entityId={}, typeId={}", entityId, typeId, e);
            return Result.fail("查询文件列表失败");
        }
    }

    @Override
    public Result getFilesByType(Integer typeId) {
        try {
            List<File> files = baseMapper.selectByType(typeId);
            return Result.ok(files);
        } catch (Exception e) {
            log.error("查询文件列表失败: typeId={}", typeId, e);
            return Result.fail("查询文件列表失败");
        }
    }

    @Override
    @Transactional
    public Result deleteEntityFile(Long fileId) {
        try {
            File file = this.getById(fileId);
            if (file == null) {
                return Result.fail("文件不存在");
            }

            // 删除MinIO中的文件
            if (file.getObjectName() != null && !file.getObjectName().isEmpty()) {
                try {
                    minioUrlService.deleteObject(file.getObjectName());
                } catch (Exception e) {
                    log.warn("删除MinIO文件失败: {}", file.getObjectName(), e);
                }
            }

            // 删除数据库记录
            boolean success = this.removeById(fileId);
            if (success) {
                log.info("删除文件成功: fileId={}, objectName={}", fileId, file.getObjectName());
                return Result.ok("删除成功");
            } else {
                return Result.fail("删除文件记录失败");
            }

        } catch (Exception e) {
            log.error("删除文件失败: fileId={}", fileId, e);
            return Result.fail("删除文件失败");
        }
    }

    @Override
    public Result refreshFileUrl(Long fileId) {
        try {
            File file = this.getById(fileId);
            if (file == null) {
                return Result.fail("文件不存在");
            }

            if (file.getObjectName() == null || file.getObjectName().isEmpty()) {
                return Result.fail("文件缺少对象名，无法刷新URL");
            }

            // 检查MinIO中文件是否存在
            if (!minioUrlService.objectExists(file.getObjectName())) {
                return Result.fail("文件在存储中不存在");
            }

            // 重新生成URL
            String newUrl = minioUrlService.generatePresignedUrl(file.getObjectName());

            // 更新数据库
            File updateFile = new File();
            updateFile.setId(fileId);
            updateFile.setUrl(newUrl);
            updateFile.setUpdaterId(getCurrentAdminId());

            boolean success = this.updateById(updateFile);
            if (success) {
                return Result.ok(newUrl);
            } else {
                return Result.fail("更新文件URL失败");
            }

        } catch (Exception e) {
            log.error("刷新文件URL失败: fileId={}", fileId, e);
            return Result.fail("刷新文件URL失败");
        }
    }

    @Override
    public Result refreshEntityFileUrls(Long entityId, Integer typeId) {
        try {
            List<File> files = baseMapper.selectByEntityIdAndType(entityId, typeId);
            if (files.isEmpty()) {
                return Result.ok("没有需要刷新的文件");
            }

            int successCount = 0;
            List<String> errorMessages = new ArrayList<>();

            for (File file : files) {
                Result result = refreshFileUrl(file.getId());
                if (result.getSuccess()) {
                    successCount++;
                } else {
                    errorMessages.add("文件" + file.getId() + ": " + result.getErrorMsg());
                }
            }

            if (errorMessages.isEmpty()) {
                return Result.ok("成功刷新" + successCount + "个文件URL");
            } else {
                log.warn("部分文件URL刷新失败: {}", String.join("; ", errorMessages));
                return Result.ok("成功刷新" + successCount + "个文件URL");
            }

        } catch (Exception e) {
            log.error("批量刷新文件URL失败: entityId={}, typeId={}", entityId, typeId, e);
            return Result.fail("批量刷新文件URL失败");
        }
    }

    /**
     * 校验文件类型是否有效
     */
    private boolean isValidFileType(MultipartFile file, Integer typeId) {
        String contentType = file.getContentType();
        if (contentType == null) {
            return false;
        }

        // 图片类型文件
        if (typeId >= 0 && typeId <= 4) {
            return contentType.startsWith("image/");
        }

        return false;
    }

    /**
     * 生成对象名称
     */
    private String generateObjectName(Integer typeId, Long entityId, String fileExtension) {
        String typePrefix = getTypePrefix(typeId);
        long timestamp = System.currentTimeMillis();
        return String.format("%s/%s-%d.%s", typePrefix, entityId, timestamp, fileExtension);
    }

    /**
     * 获取类型前缀
     */
    private String getTypePrefix(Integer typeId) {
        switch (typeId) {
            case 0: return "canteens";
            case 1: return "campuses";
            case 2: return "stalls";
            case 3: return "dishes";
            case 4: return "banners";
            default: return "others";
        }
    }

    /**
     * 获取当前管理员ID
     */
    private Long getCurrentAdminId() {
        try {
            return AdminHolder.getAdmin() != null ? AdminHolder.getAdmin().getId() : null;
        } catch (Exception e) {
            return null;
        }
    }
}