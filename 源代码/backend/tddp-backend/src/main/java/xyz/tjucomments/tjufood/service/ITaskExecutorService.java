package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.ScheduledTask;

/**
 * 定时任务执行器服务接口 (最终版)
 */
public interface ITaskExecutorService {

    /**
     * 启动定时任务
     * @param task 任务实体对象
     * @return 是否启动成功
     */
    boolean startTask(ScheduledTask task);

    /**
     * 停止定时任务
     * @param taskId 任务ID
     * @return 是否停止成功
     */
    boolean stopTask(Long taskId);

    /**
     * 暂停定时任务
     * @param taskId 任务ID
     * @return 是否暂停成功
     */
    boolean pauseTask(Long taskId);

    /**
     * 立即执行定时任务
     * @param task 任务实体对象
     * @return 是否执行成功
     */
    boolean executeTask(ScheduledTask task);


}