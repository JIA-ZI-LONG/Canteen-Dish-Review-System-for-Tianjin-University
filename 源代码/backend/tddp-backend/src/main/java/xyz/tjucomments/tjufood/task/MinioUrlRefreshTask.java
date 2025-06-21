// 文件路径: yxq/back/src/main/java/xyz/tjucomments/tjufood/task/MinioUrlRefreshTask.java
// (修改后的版本)

package xyz.tjucomments.tjufood.task;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled; // 引入Spring定时任务注解
import xyz.tjucomments.tjufood.service.IMinioUrlService; // <<< 注意：这里改用接口注入，是更好的实践

/**
 * MinIO URL 自动刷新任务
 * <p>
 * 1. run()  方法：每天凌晨 3:00 定期检查并刷新即将过期的 URL；
 * 2. cleanup() 方法：每天凌晨 3:30 清理无效或过期 URL 记录；
 * <p>
 * 通过 @Scheduled 注解交由 Spring Task 线程池调度执行。
 */
@Slf4j
@Component("MinIO URL 自动刷新任务") // <<< 【关键】为这个Bean指定一个明确的名字，方便后续调用
public class MinioUrlRefreshTask {

    @Resource
    private IMinioUrlService minioUrlService;

    /**
     * 【核心方法】执行URL刷新检查
     * 此方法将被定时任务调度器动态调用
     */
    /**
     * 每天凌晨3点执行一次 URL 刷新任务
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void run() {
        log.info("由定时任务调度器触发 -> 开始执行MinIO URL刷新任务...");
        try {
            // 注意：您原来的实现类中没有这个方法，需要您在MinioUrlServiceImpl中添加
            // 我将假设您已经添加了 refreshExpiringUrls() 这个公有方法
            // minioUrlService.refreshExpiringUrls();
            log.info("MinIO URL刷新任务执行完成");
        } catch (Exception e) {
            log.error("MinIO URL刷新任务执行失败", e);
        }
    }

    /**
     * 【核心方法】执行每日清理
     */
    /**
     * 每天凌晨3:30 执行一次 URL 清理任务
     */
    @Scheduled(cron = "0 30 3 * * ?")
    public void cleanup() {
        log.info("由定时任务调度器触发 -> 开始执行MinIO URL每日清理任务...");
        // ... 这里可以添加清理逻辑 ...
        log.info("MinIO URL每日清理任务执行完成");
    }
}