package xyz.tjucomments.tjufood.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.dto.ScheduledTaskDTO;
import xyz.tjucomments.tjufood.entity.ScheduledTask;

/**
 * 定时任务服务接口
 */
public interface IScheduledTaskService extends IService<ScheduledTask> {

    /**
     * 分页查询定时任务列表
     * @param current 当前页码
     * @param size 每页数量
     * @param taskName 任务名称（可选）
     * @param status 任务状态（可选）
     * @return 分页结果
     */
    Result getTaskList(Integer current, Integer size, String taskName, Integer status);

    /**
     * 创建定时任务
     * @param taskDTO 任务信息
     * @return 创建结果
     */
    Result createTask(ScheduledTaskDTO taskDTO);

    /**
     * 更新定时任务
     * @param taskDTO 任务信息
     * @return 更新结果
     */
    Result updateTask(ScheduledTaskDTO taskDTO);

    /**
     * 删除定时任务
     * @param id 任务ID
     * @return 删除结果
     */
    Result deleteTask(Long id);

    /**
     * 启动定时任务
     * @param id 任务ID
     * @return 启动结果
     */
    Result startTask(Long id);

    /**
     * 停止定时任务
     * @param id 任务ID
     * @return 停止结果
     */
    Result stopTask(Long id);

    /**
     * 暂停定时任务
     * @param id 任务ID
     * @return 暂停结果
     */
    Result pauseTask(Long id);

    /**
     * 恢复定时任务
     * @param id 任务ID
     * @return 恢复结果
     */
    Result resumeTask(Long id);

    /**
     * 立即执行定时任务
     * @param id 任务ID
     * @return 执行结果
     */
    Result executeTask(Long id);

    /**
     * 获取任务执行日志
     * @param id 任务ID
     * @param current 当前页码
     * @param size 每页数量
     * @return 日志列表
     */
    Result getTaskLogs(Long id, Integer current, Integer size);

    /**
     * 验证CRON表达式
     * @param cronExpression CRON表达式
     * @return 验证结果
     */
    Result validateCronExpression(String cronExpression);
}
