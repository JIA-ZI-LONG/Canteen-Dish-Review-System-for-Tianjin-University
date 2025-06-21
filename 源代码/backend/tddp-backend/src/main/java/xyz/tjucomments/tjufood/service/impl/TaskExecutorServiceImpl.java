// 文件路径: yxq/back/src/main/java/xyz/tjucomments/tjufood/service/impl/TaskExecutorServiceImpl.java
// (功能升级版)
package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.ScheduledTask;
import xyz.tjucomments.tjufood.service.IScheduledTaskService;
import xyz.tjucomments.tjufood.service.ITaskExecutorService;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * 定时任务执行器服务实现类
 * (升级版：支持动态调用、基于数据库配置)
 */
@Slf4j
@Service
public class TaskExecutorServiceImpl implements ITaskExecutorService {

    @Resource
    private ApplicationContext applicationContext; // 用于获取Spring容器中的Bean

//    @Resource
//    private IScheduledTaskService scheduledTaskService; // 用于操作任务数据库

    // 使用Spring的线程池任务调度器，功能更强大
    private final ThreadPoolTaskScheduler taskScheduler;
    private final ConcurrentHashMap<Long, ScheduledFuture<?>> runningTasks = new ConcurrentHashMap<>();

    public TaskExecutorServiceImpl() {
        taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        taskScheduler.setThreadNamePrefix("scheduled-task-");
        taskScheduler.initialize();
    }

    @Override
    public boolean startTask(ScheduledTask task) { // 【重构】参数变为 ScheduledTask
        Long taskId = task.getId();
        if (runningTasks.containsKey(taskId)) {
            log.warn("任务(ID:{})已在运行中，无需重复启动。", taskId);
            return false;
        }

        // 【重构】不再需要从数据库查询 task，直接使用传入的参数
        // ScheduledTask task = scheduledTaskService.getById(taskId);
        if (task == null) {
            log.error("启动失败，传入的任务对象为 null");
            return false;
        }

        try {
            CronTrigger cronTrigger = new CronTrigger(task.getCronExpression());
            ScheduledFuture<?> future = taskScheduler.schedule(() -> executeTaskLogic(task), cronTrigger);
            runningTasks.put(taskId, future);
            log.info("任务 '{}'(ID:{}) 启动成功，CRON: {}", task.getTaskName(), taskId, task.getCronExpression());
            return true;
        } catch (Exception e) {
            log.error("任务 '{}'(ID:{}) 启动失败", task.getTaskName(), taskId, e);
            return false;
        }
    }

    @Override
    public boolean stopTask(Long taskId) {
        ScheduledFuture<?> future = runningTasks.get(taskId);
        if (future != null) {
            future.cancel(true); // true表示即使正在执行也中断
            runningTasks.remove(taskId);
            log.info("任务(ID:{}) 已停止。", taskId);
            return true;
        }
        return false;
    }

    @Override
    public boolean executeTask(ScheduledTask task) { // 【重构】参数变为 ScheduledTask
        if (task == null) {
            log.error("立即执行失败，传入的任务对象为 null");
            return false;
        }
        // 在新线程中执行，避免阻塞当前请求
        taskScheduler.execute(() -> executeTaskLogic(task));
        return true;
    }

    // pause 和 resume 方法可以保持不变，因为它们本质上是调用 stop 和 start
    @Override
    public boolean pauseTask(Long taskId) {
        return stopTask(taskId);
    }


    /**
     * 【核心升级】根据任务配置，动态执行具体的Bean方法
     * @param task 任务实体对象
     */
    private void executeTaskLogic(ScheduledTask task) {
        try {
            log.info("开始执行任务: '{}' (ID: {})", task.getTaskName(), task.getId());

            // 假设 taskName 格式为 "beanName.methodName"
            // 例如: "minioUrlRefreshTask.run"
            String[] parts = task.getTaskName().split("\\.");
            if (parts.length != 2) {
                log.error("任务名称 '{}' 格式不正确，应为 'beanName.methodName'。", task.getTaskName());
                return;
            }

            String beanName = parts[0];
            String methodName = parts[1];

            // 从Spring容器中获取Bean实例
            Object bean = applicationContext.getBean(beanName);
            // 获取要调用的方法
            Method method = bean.getClass().getMethod(methodName);
            // 通过反射调用方法
            method.invoke(bean);

            log.info("任务 '{}' 执行完成。", task.getTaskName());

        } catch (Exception e) {
            log.error("动态执行任务 '{}' (ID: {}) 时发生错误", task.getTaskName(), task.getId(), e);
        }
    }
}