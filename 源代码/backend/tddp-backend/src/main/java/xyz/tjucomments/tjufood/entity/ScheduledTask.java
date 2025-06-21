package xyz.tjucomments.tjufood.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 定时任务实体类
 */
@Schema(description = "定时任务实体对象")
@Data
@TableName("tb_scheduled_task")
public class ScheduledTask {

    @Schema(description = "任务ID", accessMode = Schema.AccessMode.READ_ONLY)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "任务名称")
    private String taskName;

    @Schema(description = "任务分组")
    private String taskGroup;

    @Schema(description = "CRON表达式")
    private String cronExpression;

    @Schema(description = "任务状态 (0=停止, 1=运行中, 2=暂停)")
    private Integer status;

    @Schema(description = "任务描述")
    private String description;

    @Schema(description = "上次执行时间")
    private LocalDateTime lastRunTime;

    @Schema(description = "下次计划执行时间")
    private LocalDateTime nextRunTime;

    @Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createTime;

    @Schema(description = "更新时间", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updateTime;

    // 任务状态常量
    public static final int STATUS_STOPPED = 0;    // 停止
    public static final int STATUS_RUNNING = 1;    // 运行中
    public static final int STATUS_PAUSED = 2;     // 暂停
}
