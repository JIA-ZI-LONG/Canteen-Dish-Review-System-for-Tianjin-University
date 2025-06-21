// 文件路径: new_yxq/back/src/main/java/xyz/tjucomments/tjufood/config/MinioConfig.java

package xyz.tjucomments.tjufood.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "tjufood.minio")
public class MinioConfig {

    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;

    /**
     * 创建并配置 MinioClient 的 Bean
     * @return MinioClient 实例
     */
    @Bean
    public MinioClient minioClient() {
        MinioClient minioClient = null;
        try {
            minioClient = MinioClient.builder()
                    .endpoint(endpoint)
                    .credentials(accessKey, secretKey)
                    .build();

            // 检查存储桶是否存在，如果不存在则自动创建
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                log.info("MinIO 存储桶 '{}' 创建成功！", bucketName);
            } else {
                log.info("MinIO 存储桶 '{}' 已存在。", bucketName);
            }
        } catch (Exception e) {
            log.error("初始化MinioClient失败: {}", e.getMessage());
            // 如果MinIO连接失败，这里可以根据业务需求决定是否要抛出异常来终止程序启动
        }
        return minioClient;
    }
}