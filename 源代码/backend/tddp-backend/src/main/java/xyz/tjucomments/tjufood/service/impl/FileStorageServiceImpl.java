// src/main/java/xyz/tjucomments/tjufood/service/impl/FileStorageServiceImpl.java
package xyz.tjucomments.tjufood.service.impl;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.config.MinioConfig;
import xyz.tjucomments.tjufood.service.IFileStorageService;

import java.io.InputStream;

@Slf4j
@Service
public class FileStorageServiceImpl implements IFileStorageService {

    @Resource
    private MinioClient minioClient;

    @Resource
    private MinioConfig minioConfig;

    @Override
    public String uploadFile(String objectName, InputStream inputStream, long size, String contentType) throws Exception {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .object(objectName)
                            .stream(inputStream, size, -1) // 使用传入的size
                            .contentType(contentType)
                            .build()
            );
            log.info("文件上传成功，对象名: {}", objectName);
            return objectName;
        } catch (Exception e) {
            log.error("上传文件到MinIO失败", e);
            throw new RuntimeException("文件上传失败，请检查MinIO服务配置。", e);
        }
    }
}