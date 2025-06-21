// MinIO URL管理服务实现
package xyz.tjucomments.tjufood.service.impl;

import io.minio.*;
import io.minio.http.Method;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.config.MinioConfig;
import xyz.tjucomments.tjufood.service.IMinioUrlService;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class MinioUrlServiceImpl implements IMinioUrlService {

    @Resource
    private MinioClient minioClient;

    @Resource
    private MinioConfig minioConfig;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    // Redis缓存键前缀
    private static final String MINIO_URL_CACHE_PREFIX = "minio:url:";
    
    // 默认过期时间：7天
    private static final Duration DEFAULT_EXPIRY = Duration.ofDays(7);
    
    // URL缓存时间：6天（比实际过期时间短1天，提前刷新）
    private static final Duration CACHE_EXPIRY = Duration.ofDays(6);

    @Override
    public String generatePresignedUrl(String objectName, Duration expiry) {
        try {
            // 先检查Redis缓存
            String cacheKey = MINIO_URL_CACHE_PREFIX + objectName;
            String cachedUrl = stringRedisTemplate.opsForValue().get(cacheKey);
            
            if (cachedUrl != null) {
                log.debug("从缓存获取MinIO URL: {}", objectName);
                return cachedUrl;
            }

            // 生成新的预签名URL
            String presignedUrl = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(minioConfig.getBucketName())
                    .object(objectName)
                    .expiry((int) expiry.getSeconds(), TimeUnit.SECONDS)
                    .build()
            );

            // 缓存URL（缓存时间比实际过期时间短）
            long cacheSeconds = Math.min(expiry.getSeconds() - 86400, CACHE_EXPIRY.getSeconds()); // 至少提前1天过期
            stringRedisTemplate.opsForValue().set(cacheKey, presignedUrl, Duration.ofSeconds(cacheSeconds));

            log.info("生成MinIO预签名URL成功: {} -> {}", objectName, presignedUrl);
            return presignedUrl;

        } catch (Exception e) {
            log.error("生成MinIO预签名URL失败: {}", objectName, e);
            throw new RuntimeException("生成文件访问链接失败", e);
        }
    }

    @Override
    public String generatePresignedUrl(String objectName) {
        return generatePresignedUrl(objectName, DEFAULT_EXPIRY);
    }

    @Override
    public String generatePresignedUploadUrl(String objectName, Duration expiry) {
        try {
            String presignedUrl = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                    .method(Method.PUT)
                    .bucket(minioConfig.getBucketName())
                    .object(objectName)
                    .expiry((int) expiry.getSeconds(), TimeUnit.SECONDS)
                    .build()
            );

            log.info("生成MinIO预签名上传URL成功: {} -> {}", objectName, presignedUrl);
            return presignedUrl;

        } catch (Exception e) {
            log.error("生成MinIO预签名上传URL失败: {}", objectName, e);
            throw new RuntimeException("生成文件上传链接失败", e);
        }
    }

    @Override
    public boolean objectExists(String objectName) {
        try {
            minioClient.statObject(
                StatObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(objectName)
                    .build()
            );
            return true;
        } catch (Exception e) {
            log.debug("对象不存在: {}", objectName);
            return false;
        }
    }

    @Override
    public boolean deleteObject(String objectName) {
        try {
            minioClient.removeObject(
                RemoveObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(objectName)
                    .build()
            );

            // 删除缓存
            String cacheKey = MINIO_URL_CACHE_PREFIX + objectName;
            stringRedisTemplate.delete(cacheKey);

            log.info("删除MinIO对象成功: {}", objectName);
            return true;
        } catch (Exception e) {
            log.error("删除MinIO对象失败: {}", objectName, e);
            return false;
        }
    }

    @Override
    public String getPublicUrl(String objectName) {
        // 如果bucket设置为公共访问，可以直接返回公共URL
        return String.format("%s/%s/%s", minioConfig.getEndpoint(), minioConfig.getBucketName(), objectName);
    }

    @Override
    public String refreshUrl(String oldUrl) {
        try {
            // 从URL中提取objectName
            String objectName = extractObjectNameFromUrl(oldUrl);
            if (objectName == null) {
                log.warn("无法从URL中提取对象名称: {}", oldUrl);
                return oldUrl;
            }

            // 删除缓存，强制重新生成
            String cacheKey = MINIO_URL_CACHE_PREFIX + objectName;
            stringRedisTemplate.delete(cacheKey);

            // 重新生成URL
            return generatePresignedUrl(objectName);

        } catch (Exception e) {
            log.error("刷新MinIO URL失败: {}", oldUrl, e);
            return oldUrl;
        }
    }

    /**
     * 从URL中提取对象名称
     * @param url 完整的MinIO URL
     * @return 对象名称
     */
    private String extractObjectNameFromUrl(String url) {
        try {
            String bucketName = minioConfig.getBucketName();
            String endpoint = minioConfig.getEndpoint();
            
            // 移除endpoint和bucket部分
            String prefix = endpoint + "/" + bucketName + "/";
            if (url.startsWith(prefix)) {
                String objectName = url.substring(prefix.length());
                // 移除查询参数（预签名URL的参数部分）
                int queryIndex = objectName.indexOf('?');
                if (queryIndex > 0) {
                    objectName = objectName.substring(0, queryIndex);
                }
                return objectName;
            }
            return null;
        } catch (Exception e) {
            log.error("提取对象名称失败: {}", url, e);
            return null;
        }
    }

    /**
     * 批量刷新即将过期的URL
     * 可以通过定时任务调用
     */
    @Override
    public void refreshExpiringUrls() {
        try {
            // 获取所有缓存的URL键
            String pattern = MINIO_URL_CACHE_PREFIX + "*";
            var keys = stringRedisTemplate.keys(pattern);
            
            if (keys != null) {
                for (String key : keys) {
                    // 检查TTL，如果即将过期（小于1小时），则刷新
                    Long ttl = stringRedisTemplate.getExpire(key);
                    if (ttl != null && ttl < 3600) { // 小于1小时
                        String objectName = key.substring(MINIO_URL_CACHE_PREFIX.length());
                        log.info("刷新即将过期的URL: {}", objectName);
                        generatePresignedUrl(objectName);
                    }
                }
            }
        } catch (Exception e) {
            log.error("批量刷新URL失败", e);
        }
    }
}
