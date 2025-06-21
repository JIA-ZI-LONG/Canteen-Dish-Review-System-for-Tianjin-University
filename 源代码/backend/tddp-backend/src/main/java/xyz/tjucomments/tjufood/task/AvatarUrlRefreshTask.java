// 文件路径: src/main/java/xyz/tjucomments/tjufood/task/AvatarUrlRefreshTask.java

package xyz.tjucomments.tjufood.task;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import xyz.tjucomments.tjufood.entity.Admin;
import xyz.tjucomments.tjufood.entity.File;
import xyz.tjucomments.tjufood.entity.User;
import xyz.tjucomments.tjufood.service.IAdminService;
import xyz.tjucomments.tjufood.service.IFileService;
import xyz.tjucomments.tjufood.service.IMinioUrlService;
import xyz.tjucomments.tjufood.service.IUserService;

import java.util.List;

/**
 * 文件URL自动刷新定时任务
 * 定期检查并刷新即将过期的头像URL和其他文件URL
 */
@Slf4j
@Component
public class AvatarUrlRefreshTask {

    @Resource
    private IAdminService adminService;

    @Resource
    private IUserService userService;

    @Resource
    private IFileService fileService;

    @Resource
    private IMinioUrlService minioUrlService;

    /**
     * 每天凌晨2点执行文件URL刷新任务
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void refreshFileUrls() {
        log.info("开始执行文件URL刷新任务");

        try {
            // 刷新管理员头像URL
            refreshAdminAvatars();

            // 刷新用户头像URL
            refreshUserAvatars();

            // 刷新实体文件URL
            refreshEntityFiles();

            // 刷新MinIO中即将过期的URL缓存
            minioUrlService.refreshExpiringUrls();

            log.info("文件URL刷新任务执行完成");
        } catch (Exception e) {
            log.error("文件URL刷新任务执行失败", e);
        }
    }

    /**
     * 刷新管理员头像URL
     */
    private void refreshAdminAvatars() {
        try {
            // 查询所有有MinIO头像的管理员（头像URL包含MinIO endpoint）
            List<Admin> admins = adminService.lambdaQuery()
                    .isNotNull(Admin::getAvatar)
                    .ne(Admin::getAvatar, "")
                    .like(Admin::getAvatar, "127.0.0.1:9005") // MinIO endpoint
                    .list();

            for (Admin admin : admins) {
                try {
                    // 从URL中提取对象名
                    String objectName = extractObjectNameFromUrl(admin.getAvatar());
                    if (objectName != null && minioUrlService.objectExists(objectName)) {
                        // 重新生成URL
                        String newUrl = minioUrlService.generatePresignedUrl(objectName);

                        // 更新数据库
                        Admin updateAdmin = new Admin();
                        updateAdmin.setId(admin.getId());
                        updateAdmin.setAvatar(newUrl);
                        adminService.updateById(updateAdmin);

                        log.debug("刷新管理员头像URL成功: adminId={}", admin.getId());
                    }
                } catch (Exception e) {
                    log.error("刷新管理员头像URL失败: adminId={}", admin.getId(), e);
                }
            }

            log.info("管理员头像URL刷新完成，处理数量: {}", admins.size());
        } catch (Exception e) {
            log.error("刷新管理员头像URL失败", e);
        }
    }

    /**
     * 刷新用户头像URL
     */
    private void refreshUserAvatars() {
        try {
            // 查询所有有MinIO头像的用户（头像URL包含MinIO endpoint）
            List<User> users = userService.lambdaQuery()
                    .isNotNull(User::getIcon)
                    .ne(User::getIcon, "")
                    .like(User::getIcon, "127.0.0.1:9005") // MinIO endpoint
                    .list();

            for (User user : users) {
                try {
                    // 从URL中提取对象名
                    String objectName = extractObjectNameFromUrl(user.getIcon());
                    if (objectName != null && minioUrlService.objectExists(objectName)) {
                        // 重新生成URL
                        String newUrl = minioUrlService.generatePresignedUrl(objectName);
                        
                        // 更新数据库
                        User updateUser = new User();
                        updateUser.setId(user.getId());
                        updateUser.setIcon(newUrl);
                        userService.updateById(updateUser);
                        
                        log.debug("刷新用户头像URL成功: userId={}", user.getId());
                    }
                } catch (Exception e) {
                    log.error("刷新用户头像URL失败: userId={}", user.getId(), e);
                }
            }
            
            log.info("用户头像URL刷新完成，处理数量: {}", users.size());
        } catch (Exception e) {
            log.error("刷新用户头像URL失败", e);
        }
    }

    /**
     * 刷新实体文件URL
     */
    private void refreshEntityFiles() {
        try {
            // 查询所有有MinIO对象名的文件
            List<File> files = fileService.lambdaQuery()
                    .isNotNull(File::getObjectName)
                    .ne(File::getObjectName, "")
                    .eq(File::getStatus, 1)
                    .list();

            for (File file : files) {
                try {
                    // 检查文件是否存在
                    if (minioUrlService.objectExists(file.getObjectName())) {
                        // 重新生成URL
                        String newUrl = minioUrlService.generatePresignedUrl(file.getObjectName());

                        // 更新数据库
                        File updateFile = new File();
                        updateFile.setId(file.getId());
                        updateFile.setUrl(newUrl);
                        fileService.updateById(updateFile);

                        log.debug("刷新实体文件URL成功: fileId={}, typeId={}", file.getId(), file.getTypeId());
                    } else {
                        log.warn("实体文件不存在: fileId={}, objectName={}",
                                file.getId(), file.getObjectName());
                    }
                } catch (Exception e) {
                    log.error("刷新实体文件URL失败: fileId={}", file.getId(), e);
                }
            }

            log.info("实体文件URL刷新完成，处理数量: {}", files.size());
        } catch (Exception e) {
            log.error("刷新实体文件URL失败", e);
        }
    }

    /**
     * 从URL中提取对象名称
     */
    private String extractObjectNameFromUrl(String url) {
        try {
            // 假设URL格式为: http://127.0.0.1:9005/tjufood/objectName?params
            String bucketName = "tjufood"; // 从配置中获取
            String pattern = "/" + bucketName + "/";

            int startIndex = url.indexOf(pattern);
            if (startIndex > 0) {
                startIndex += pattern.length();
                int endIndex = url.indexOf('?', startIndex);
                if (endIndex > 0) {
                    return url.substring(startIndex, endIndex);
                } else {
                    return url.substring(startIndex);
                }
            }
            return null;
        } catch (Exception e) {
            log.error("提取对象名称失败: {}", url, e);
            return null;
        }
    }
}
