// MinIO URL管理服务接口
package xyz.tjucomments.tjufood.service;

import java.time.Duration;

/**
 * MinIO URL管理服务
 * 负责生成、刷新和管理MinIO预签名URL
 */
public interface IMinioUrlService {
    
    /**
     * 生成预签名下载URL
     * @param objectName 对象名称
     * @param expiry 过期时间
     * @return 预签名URL
     */
    String generatePresignedUrl(String objectName, Duration expiry);
    
    /**
     * 生成预签名下载URL（默认7天有效期）
     * @param objectName 对象名称
     * @return 预签名URL
     */
    String generatePresignedUrl(String objectName);
    
    /**
     * 生成预签名上传URL
     * @param objectName 对象名称
     * @param expiry 过期时间
     * @return 预签名上传URL
     */
    String generatePresignedUploadUrl(String objectName, Duration expiry);
    
    /**
     * 检查对象是否存在
     * @param objectName 对象名称
     * @return 是否存在
     */
    boolean objectExists(String objectName);
    
    /**
     * 删除对象
     * @param objectName 对象名称
     * @return 是否删除成功
     */
    boolean deleteObject(String objectName);
    
    /**
     * 获取对象的公共访问URL（如果bucket是公共的）
     * @param objectName 对象名称
     * @return 公共URL
     */
    String getPublicUrl(String objectName);
    
    /**
     * 刷新即将过期的URL
     * @param oldUrl 旧的URL
     * @return 新的URL
     */
    String refreshUrl(String oldUrl);

    /**
     * 批量刷新即将过期的URL
     * 可以通过定时任务调用
     */
    void refreshExpiringUrls();
}
