package xyz.tjucomments.tjufood.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j; // 【修复】导入Slf4j
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.entity.AuditLog; // 【修复】使用正确的实体 AuditLog
import xyz.tjucomments.tjufood.service.IAuditLogService; // 【修复】使用正确的Service IAuditLogService

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "D05. 运营管理 - 日志管理", description = "查看系统操作日志")
@SecurityRequirement(name = "authorization")
@RestController
@RequestMapping("/api/admin/logs") // URL路径保持不变
@Slf4j // 【修复】添加注解以启用 log 对象
public class LogAdminController {

    @Resource
    private IAuditLogService auditLogService; // 【修复】注入正确的Service

    @Operation(summary = "分页查询审计日志")
    @GetMapping
    public Result getLogs(
            @Parameter(description = "操作人名称") @RequestParam(required = false) String operatorName,
            @Parameter(description = "操作模块") @RequestParam(required = false) String module,
            @Parameter(description = "IP地址") @RequestParam(required = false) String ip,
            @Parameter(description = "开始时间") @RequestParam(required = false) String startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) String endTime,
            @Parameter(description = "当前页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") Integer size
    ) {
        // 【修复】创建针对 AuditLog 的查询构造器
        LambdaQueryWrapper<AuditLog> queryWrapper = new LambdaQueryWrapper<>();

        // 【修复】使用 AuditLog 中存在的字段进行查询
        if (StringUtils.hasText(operatorName)) {
            queryWrapper.like(AuditLog::getOperatorName, operatorName);
        }
        if (StringUtils.hasText(module)) {
            queryWrapper.like(AuditLog::getModule, module);
        }
        if (StringUtils.hasText(ip)) {
            queryWrapper.like(AuditLog::getIp, ip);
        }

        // 时间范围查询
        if (StringUtils.hasText(startTime)) {
            try {
                LocalDateTime start = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                queryWrapper.ge(AuditLog::getCreateTime, start);
            } catch (Exception e) {
                // 忽略时间格式错误
            }
        }
        if (StringUtils.hasText(endTime)) {
            try {
                LocalDateTime end = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                queryWrapper.le(AuditLog::getCreateTime, end);
            } catch (Exception e) {
                // 忽略时间格式错误
            }
        }

        queryWrapper.orderByDesc(AuditLog::getCreateTime);

        Page<AuditLog> page = auditLogService.page(new Page<>(current, size), queryWrapper);

        // 返回分页结构
        Map<String, Object> result = new HashMap<>();
        result.put("records", page.getRecords());
        result.put("total", page.getTotal());
        return Result.ok(result);
    }

    @Operation(summary = "查询日志详情")
    @GetMapping("/{id}")
    public Result getLogDetail(@Parameter(description = "日志ID") @PathVariable Long id) {
        AuditLog log = auditLogService.getById(id); // 【修复】查询 AuditLog
        return log != null ? Result.ok(log) : Result.fail("日志记录不存在！");
    }

    @Operation(summary = "删除日志记录")
    @DeleteMapping("/{id}")
    public Result deleteLog(@Parameter(description = "日志ID") @PathVariable Long id) {
        boolean isSuccess = auditLogService.removeById(id);
        return isSuccess ? Result.ok() : Result.fail("删除日志记录失败！");
    }

    @Operation(summary = "批量删除日志记录")
    @DeleteMapping("/batch")
    public Result batchDeleteLogs(@RequestBody Map<String, List<Long>> params) {
        List<Long> ids = params.get("ids");
        if (ids == null || ids.isEmpty()) {
            return Result.fail("请选择要删除的日志记录！");
        }
        boolean isSuccess = auditLogService.removeByIds(ids);
        return isSuccess ? Result.ok("批量删除成功") : Result.fail("批量删除失败");
    }

    @Operation(summary = "清理过期日志")
    @DeleteMapping("/cleanup")
    public Result cleanupLogs(@Parameter(description = "保留天数") @RequestParam(defaultValue = "30") Integer days) {
        try {
            auditLogService.cleanupExpiredLogs(days);
            return Result.ok("日志清理任务已执行");
        } catch (Exception e) {
            log.error("清理审计日志失败", e); // 【修复】使用 log 对象记录错误
            return Result.fail("日志清理失败: " + e.getMessage());
        }
    }
}