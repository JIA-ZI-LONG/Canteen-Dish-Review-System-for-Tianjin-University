package xyz.tjucomments.tjufood.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.dto.ScheduledTaskDTO;
import xyz.tjucomments.tjufood.service.IScheduledTaskService;

@Tag(name = "F02. 工具箱 - 任务管理", description = "系统任务管理功能")
@SecurityRequirement(name = "authorization")
@RestController
@RequestMapping("/api/admin/tasks")
public class TaskAdminController {

    @Resource
    private IScheduledTaskService scheduledTaskService;

    @Operation(summary = "分页查询任务列表")
    @GetMapping
    public Result getTasks(
            @Parameter(description = "任务名称") @RequestParam(required = false) String taskName,
            @Parameter(description = "任务状态") @RequestParam(required = false) Integer status,
            @Parameter(description = "当前页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") Integer size
    ) {
        return scheduledTaskService.getTaskList(current, size, taskName, status);
    }

    @Operation(summary = "创建新任务")
    @PostMapping
    public Result createTask(@Valid @RequestBody ScheduledTaskDTO taskDTO) {
        return scheduledTaskService.createTask(taskDTO);
    }

    @Operation(summary = "更新任务")
    @PutMapping
    public Result updateTask(@Valid @RequestBody ScheduledTaskDTO taskDTO) {
        return scheduledTaskService.updateTask(taskDTO);
    }

    @Operation(summary = "删除任务")
    @DeleteMapping("/{id}")
    public Result deleteTask(@Parameter(description = "任务ID") @PathVariable Long id) {
        return scheduledTaskService.deleteTask(id);
    }

    @Operation(summary = "启动任务")
    @PostMapping("/{id}/start")
    public Result startTask(@Parameter(description = "任务ID") @PathVariable Long id) {
        return scheduledTaskService.startTask(id);
    }

    @Operation(summary = "停止任务")
    @PostMapping("/{id}/stop")
    public Result stopTask(@Parameter(description = "任务ID") @PathVariable Long id) {
        return scheduledTaskService.stopTask(id);
    }

    @Operation(summary = "暂停任务")
    @PostMapping("/{id}/pause")
    public Result pauseTask(@Parameter(description = "任务ID") @PathVariable Long id) {
        return scheduledTaskService.pauseTask(id);
    }

    @Operation(summary = "恢复任务")
    @PostMapping("/{id}/resume")
    public Result resumeTask(@Parameter(description = "任务ID") @PathVariable Long id) {
        return scheduledTaskService.resumeTask(id);
    }

    @Operation(summary = "立即执行任务")
    @PostMapping("/{id}/execute")
    public Result executeTask(@Parameter(description = "任务ID") @PathVariable Long id) {
        return scheduledTaskService.executeTask(id);
    }

    @Operation(summary = "获取任务执行日志")
    @GetMapping("/{id}/logs")
    public Result getTaskLogs(
            @Parameter(description = "任务ID") @PathVariable Long id,
            @Parameter(description = "当前页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") Integer size
    ) {
        return scheduledTaskService.getTaskLogs(id, current, size);
    }

    @Operation(summary = "验证CRON表达式")
    @PostMapping("/validate-cron")
    public Result validateCronExpression(@RequestParam String cronExpression) {
        return scheduledTaskService.validateCronExpression(cronExpression);
    }
}
