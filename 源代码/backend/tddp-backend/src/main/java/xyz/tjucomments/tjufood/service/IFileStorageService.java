// 文件路径: src/main/java/xyz/tjucomments/tjufood/service/IFileStorageService.java
package xyz.tjucomments.tjufood.service;

import java.io.InputStream;

public interface IFileStorageService {
    /**
     * 上传文件到MinIO
     *
     * @param objectName  在MinIO中存储的对象名称 (包含路径和文件名)
     * @param inputStream 文件的输入流
     * @param size        【新增】文件的总大小（字节）
     * @param contentType 文件的MIME类型
     * @return 文件的对象名称
     * @throws Exception 上传过程中可能出现的异常
     */
    String uploadFile(String objectName, InputStream inputStream, long size, String contentType) throws Exception;
}