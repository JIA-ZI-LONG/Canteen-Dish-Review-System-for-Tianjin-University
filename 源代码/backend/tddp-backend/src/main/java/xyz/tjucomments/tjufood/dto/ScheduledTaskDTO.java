package xyz.tjucomments.tjufood.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 定时任务数据传输对象
 */
@Schema(description = "定时任务数据传输对象")
@Data
public class ScheduledTaskDTO {

    @Schema(description = "任务ID")
    private Long id;

    @Schema(description = "任务名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "任务名称不能为空")
    private String taskName;

    @Schema(description = "任务分组", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "任务分组不能为空")
    private String taskGroup;

    @Schema(description = "CRON表达式", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "CRON表达式不能为空")
    private String cronExpression;

    @Schema(description = "任务状态 (0=停止, 1=运行中, 2=暂停)")
    @NotNull(message = "任务状态不能为空")
    private Integer status;

    @Schema(description = "任务描述")
    private String description;

    @Schema(description = "上次执行时间")
    private LocalDateTime lastRunTime;

    @Schema(description = "下次计划执行时间")
    private LocalDateTime nextRunTime;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
