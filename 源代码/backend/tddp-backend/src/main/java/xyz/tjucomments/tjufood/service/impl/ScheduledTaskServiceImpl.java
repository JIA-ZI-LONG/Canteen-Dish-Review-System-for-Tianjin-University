// 文件路径: src/main/java/xyz/tjucomments/tjufood/service/impl/ScheduledTaskServiceImpl.java
package xyz.tjucomments.tjufood.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.dto.ScheduledTaskDTO;
import xyz.tjucomments.tjufood.entity.ScheduledTask;
import xyz.tjucomments.tjufood.mapper.ScheduledTaskMapper;
import xyz.tjucomments.tjufood.service.IScheduledTaskService;
import xyz.tjucomments.tjufood.service.ITaskExecutorService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 定时任务服务实现类
 */
@Slf4j
@Service
public class ScheduledTaskServiceImpl extends ServiceImpl<ScheduledTaskMapper, ScheduledTask> implements IScheduledTaskService {

    @Resource
    private ITaskExecutorService taskExecutorService;

    @Override
    public Result getTaskList(Integer current, Integer size, String taskName, Integer status) {
        // 创建分页对象
        Page<ScheduledTask> page = new Page<>(current, size);

        // 构建查询条件
        LambdaQueryWrapper<ScheduledTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(taskName), ScheduledTask::getTaskName, taskName)
                .eq(status != null, ScheduledTask::getStatus, status)
                .orderByDesc(ScheduledTask::getCreateTime);

        // 执行分页查询
        IPage<ScheduledTask> taskPage = page(page, queryWrapper);

        // 转换为DTO
        List<ScheduledTaskDTO> taskDTOList = taskPage.getRecords().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        // 构建返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("records", taskDTOList);
        result.put("total", taskPage.getTotal());
        result.put("current", taskPage.getCurrent());
        result.put("size", taskPage.getSize());

        return Result.ok(result);
    }

    @Override
    @Transactional
    public Result createTask(ScheduledTaskDTO taskDTO) {
        try {
            // 验证CRON表达式
            Result validateResult = validateCronExpression(taskDTO.getCronExpression());
            if (!validateResult.getSuccess()) {
                return validateResult;
            }

            // 检查任务名称是否重复
            long count = lambdaQuery().eq(ScheduledTask::getTaskName, taskDTO.getTaskName()).count();
            if (count > 0) {
                return Result.fail("任务名称已存在！");
            }

            // 创建任务实体
            ScheduledTask task = new ScheduledTask();
            BeanUtils.copyProperties(taskDTO, task);
            task.setStatus(ScheduledTask.STATUS_STOPPED); // 新建任务默认为停止状态
            task.setCreateTime(LocalDateTime.now());
            task.setUpdateTime(LocalDateTime.now());

            // 计算下次执行时间
            try {
                CronExpression cronExpression = CronExpression.parse(taskDTO.getCronExpression());
                LocalDateTime nextRunTime = cronExpression.next(LocalDateTime.now());
                task.setNextRunTime(nextRunTime);
            } catch (Exception e) {
                log.warn("计算下次执行时间失败: {}", e.getMessage());
            }

            // 保存任务
            boolean success = save(task);
            if (success) {
                return Result.ok(task.getId());
            } else {
                return Result.fail("创建任务失败！");
            }
        } catch (Exception e) {
            log.error("创建定时任务失败", e);
            return Result.fail("创建任务失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result updateTask(ScheduledTaskDTO taskDTO) {
        try {
            ScheduledTask existingTask = getById(taskDTO.getId());
            if (existingTask == null) {
                return Result.fail("任务不存在！");
            }
            Result validateResult = validateCronExpression(taskDTO.getCronExpression());
            if (!validateResult.getSuccess()) {
                return validateResult;
            }
            long count = lambdaQuery().eq(ScheduledTask::getTaskName, taskDTO.getTaskName())
                    .ne(ScheduledTask::getId, taskDTO.getId()).count();
            if (count > 0) {
                return Result.fail("任务名称已存在！");
            }
            BeanUtils.copyProperties(taskDTO, existingTask);
            existingTask.setUpdateTime(LocalDateTime.now());
            try {
                CronExpression cronExpression = CronExpression.parse(taskDTO.getCronExpression());
                LocalDateTime nextRunTime = cronExpression.next(LocalDateTime.now());
                existingTask.setNextRunTime(nextRunTime);
            } catch (Exception e) {
                log.warn("计算下次执行时间失败: {}", e.getMessage());
            }
            boolean success = updateById(existingTask);
            if (success) {
                return Result.ok();
            } else {
                return Result.fail("更新任务失败！");
            }
        } catch (Exception e) {
            log.error("更新定时任务失败", e);
            return Result.fail("更新任务失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result deleteTask(Long id) {
        try {
            ScheduledTask task = getById(id);
            if (task == null) {
                return Result.fail("任务不存在！");
            }
            if (task.getStatus() == ScheduledTask.STATUS_RUNNING) {
                stopTask(id);
            }
            boolean success = removeById(id);
            if (success) {
                return Result.ok();
            } else {
                return Result.fail("删除任务失败！");
            }
        } catch (Exception e) {
            log.error("删除定时任务失败", e);
            return Result.fail("删除任务失败：" + e.getMessage());
        }
    }

    @Override
    public Result startTask(Long id) {
        try {
            ScheduledTask task = getById(id);
            if (task == null) {
                return Result.fail("任务不存在！");
            }
            if (task.getStatus() == ScheduledTask.STATUS_RUNNING) {
                return Result.fail("任务已在运行中！");
            }
            task.setStatus(ScheduledTask.STATUS_RUNNING);
            task.setUpdateTime(LocalDateTime.now());
            try {
                CronExpression cronExpression = CronExpression.parse(task.getCronExpression());
                LocalDateTime nextRunTime = cronExpression.next(LocalDateTime.now());
                task.setNextRunTime(nextRunTime);
            } catch (Exception e) {
                log.warn("计算下次执行时间失败: {}", e.getMessage());
            }
            boolean success = updateById(task);
            if (success) {
                boolean started = taskExecutorService.startTask(task);
                if (started) {
                    log.info("启动定时任务: {}", task.getTaskName());
                    return Result.ok("任务启动成功");
                } else {
                    task.setStatus(ScheduledTask.STATUS_STOPPED);
                    updateById(task);
                    return Result.fail("任务启动失败！");
                }
            } else {
                return Result.fail("更新任务状态失败！");
            }
        } catch (Exception e) {
            log.error("启动定时任务失败", e);
            return Result.fail("启动任务失败：" + e.getMessage());
        }
    }

    @Override
    public Result stopTask(Long id) {
        try {
            ScheduledTask task = getById(id);
            if (task == null) {
                return Result.fail("任务不存在！");
            }
            if (task.getStatus() == ScheduledTask.STATUS_STOPPED) {
                return Result.fail("任务已停止！");
            }
            task.setStatus(ScheduledTask.STATUS_STOPPED);
            task.setUpdateTime(LocalDateTime.now());
            task.setNextRunTime(null);
            boolean success = updateById(task);
            if (success) {
                taskExecutorService.stopTask(id);
                log.info("停止定时任务: {}", task.getTaskName());
                return Result.ok("任务停止成功");
            } else {
                return Result.fail("停止任务失败！");
            }
        } catch (Exception e) {
            log.error("停止定时任务失败", e);
            return Result.fail("停止任务失败：" + e.getMessage());
        }
    }

    @Override
    public Result pauseTask(Long id) {
        try {
            ScheduledTask task = getById(id);
            if (task == null) {
                return Result.fail("任务不存在！");
            }
            if (task.getStatus() != ScheduledTask.STATUS_RUNNING) {
                return Result.fail("只有运行中的任务才能暂停！");
            }
            task.setStatus(ScheduledTask.STATUS_PAUSED);
            task.setUpdateTime(LocalDateTime.now());
            task.setNextRunTime(null);
            boolean success = updateById(task);
            if (success) {
                taskExecutorService.pauseTask(id);
                log.info("暂停定时任务: {}", task.getTaskName());
                return Result.ok("任务暂停成功");
            } else {
                return Result.fail("暂停任务失败！");
            }
        } catch (Exception e) {
            log.error("暂停定时任务失败", e);
            return Result.fail("暂停任务失败：" + e.getMessage());
        }
    }

    @Override
    public Result resumeTask(Long id) {
        try {
            ScheduledTask task = getById(id);
            if (task == null) {
                return Result.fail("任务不存在！");
            }
            if (task.getStatus() != ScheduledTask.STATUS_PAUSED) {
                return Result.fail("只有暂停的任务才能恢复！");
            }
            task.setStatus(ScheduledTask.STATUS_RUNNING);
            task.setUpdateTime(LocalDateTime.now());
            try {
                CronExpression cronExpression = CronExpression.parse(task.getCronExpression());
                LocalDateTime nextRunTime = cronExpression.next(LocalDateTime.now());
                task.setNextRunTime(nextRunTime);
            } catch (Exception e) {
                log.warn("计算下次执行时间失败: {}", e.getMessage());
            }
            boolean success = updateById(task);
            if (success) {
                boolean resumed = taskExecutorService.startTask(task);
                if (resumed) {
                    log.info("恢复定时任务: {}", task.getTaskName());
                    return Result.ok("任务恢复成功");
                } else {
                    task.setStatus(ScheduledTask.STATUS_PAUSED);
                    updateById(task);
                    return Result.fail("任务恢复失败！");
                }
            } else {
                return Result.fail("更新任务状态失败！");
            }
        } catch (Exception e) {
            log.error("恢复定时任务失败", e);
            return Result.fail("恢复任务失败：" + e.getMessage());
        }
    }

    @Override
    public Result executeTask(Long id) {
        try {
            ScheduledTask task = getById(id);
            if (task == null) {
                return Result.fail("任务不存在！");
            }
            boolean executed = taskExecutorService.executeTask(task);
            if (executed) {
                task.setLastRunTime(LocalDateTime.now());
                task.setUpdateTime(LocalDateTime.now());
                updateById(task);
                log.info("立即执行定时任务: {}", task.getTaskName());
                return Result.ok("任务执行成功");
            } else {
                return Result.fail("任务执行失败！");
            }
        } catch (Exception e) {
            log.error("执行定时任务失败", e);
            return Result.fail("执行任务失败：" + e.getMessage());
        }
    }

    @Override
    public Result getTaskLogs(Long id, Integer current, Integer size) {
        // TODO: 实现任务执行日志查询
        Map<String, Object> result = new HashMap<>();
        result.put("records", List.of());
        result.put("total", 0L); // 确保 total 是 Long 类型
        result.put("message", "任务日志功能开发中");
        return Result.ok(result);
    }

    @Override
    public Result validateCronExpression(String cronExpression) {
        try {
            if (!StringUtils.hasText(cronExpression)) {
                return Result.fail("CRON表达式不能为空！");
            }
            CronExpression.parse(cronExpression);
            CronExpression cron = CronExpression.parse(cronExpression);
            LocalDateTime now = LocalDateTime.now();
            Map<String, Object> result = new HashMap<>();
            result.put("valid", true);
            result.put("nextExecutions", List.of(
                    cron.next(now),
                    cron.next(cron.next(now)),
                    cron.next(cron.next(cron.next(now)))
            ));
            return Result.ok(result);
        } catch (Exception e) {
            return Result.fail("CRON表达式格式错误：" + e.getMessage());
        }
    }

    private ScheduledTaskDTO convertToDTO(ScheduledTask task) {
        ScheduledTaskDTO dto = new ScheduledTaskDTO();
        BeanUtils.copyProperties(task, dto);
        return dto;
    }
}