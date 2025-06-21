package xyz.tjucomments.tjufood.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper; // 【修复】导入 LambdaQueryWrapper
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j; // 【修复】导入 Slf4j
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.tjucomments.tjufood.aop.Log;
import xyz.tjucomments.tjufood.dto.AuditLogExportDTO;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.entity.AuditLog;
import xyz.tjucomments.tjufood.service.IAuditLogService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 审计日志管理控制器 (修复整合版)
 * 包括操作日志、登录日志和安全日志的管理
 */
@Tag(name = "F04. 工具箱 - 审计日志", description = "审计日志查询、统计、导出与管理")
@SecurityRequirement(name = "authorization")
@RestController
@RequestMapping("/api/admin/audit-logs")
@Slf4j
public class AuditLogController {

    @Resource
    private IAuditLogService auditLogService; // 【

    @Operation(summary = "分页查询审计日志")
    @GetMapping
    @Log(module = "审计日志", operation = "查询审计日志")
    public Result getAuditLogs(
            @Parameter(description = "当前页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "日志类型 (1=操作日志, 2=登录日志, 3=安全日志)") @RequestParam(required = false) Integer logType,
            @Parameter(description = "开始时间 (格式: yyyy-MM-dd)") @RequestParam(required = false) String startTime,
            @Parameter(description = "结束时间 (格式: yyyy-MM-dd)") @RequestParam(required = false) String endTime,
            @Parameter(description = "关键词搜索") @RequestParam(required = false) String keyword
    ) {
        return auditLogService.getAuditLogs(current, size, logType, startTime, endTime, keyword);
    }

    @Operation(summary = "导出审计日志为Excel")
    @GetMapping("/export")
    @Log(module = "审计日志", operation = "导出Excel")
    public ResponseEntity<byte[]> exportLogs(
            @Parameter(description = "日志类型 (1=操作日志, 2=登录日志, 3=安全日志)") @RequestParam(required = false) Integer logType,
            @Parameter(description = "开始时间") @RequestParam(required = false) String startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) String endTime,
            @Parameter(description = "关键词搜索") @RequestParam(required = false) String keyword
    ) throws IOException {

        // 1. 查询数据
        LambdaQueryWrapper<AuditLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(logType != null, AuditLog::getLogType, logType);

        if (keyword != null && !keyword.trim().isEmpty()) {
            queryWrapper.and(wrapper -> wrapper
                    // 【修复】使用正确的实体类方法引用
                    .like(AuditLog::getDescription, keyword).or()
                    .like(AuditLog::getOperatorName, keyword).or()
                    .like(AuditLog::getModule, keyword).or()
                    .like(AuditLog::getOperation, keyword)
            );
        }

        if (startTime != null && !startTime.trim().isEmpty()) {
            try {
                LocalDateTime start = LocalDateTime.parse(startTime + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                queryWrapper.ge(AuditLog::getCreateTime, start);
            } catch (Exception e) {
                // 时间格式错误时忽略
            }
        }
        if (endTime != null && !endTime.trim().isEmpty()) {
            try {
                LocalDateTime end = LocalDateTime.parse(endTime + " 23:59:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                queryWrapper.le(AuditLog::getCreateTime, end);
            } catch (Exception e) {
                // 时间格式错误时忽略
            }
        }
        queryWrapper.orderByDesc(AuditLog::getCreateTime);
        List<AuditLog> logs = auditLogService.list(queryWrapper);

        // 2. 转换为导出DTO
        List<AuditLogExportDTO> exportList = logs.stream().map(log -> {
            AuditLogExportDTO dto = new AuditLogExportDTO(); // 【修复】使用正确的DTO
            BeanUtil.copyProperties(log, dto);
            dto.setStatus(log.getStatus() != null && log.getStatus() == 1 ? "成功" : "失败");
            if (log.getCreateTime() != null) {
                dto.setCreateTime(log.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
            return dto;
        }).collect(Collectors.toList());

        // 3. 使用EasyExcel写入内存
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        EasyExcel.write(out, AuditLogExportDTO.class).sheet("审计日志").doWrite(exportList); // 【修复】使用正确的DTO
        byte[] bytes = out.toByteArray();

        // 4. 构建HTTP响应头
        HttpHeaders headers = new HttpHeaders();
        String fileName = "审计日志-" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xlsx";
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        headers.setContentDispositionFormData("attachment", encodedFileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    // 整合原LogAdminController中的删除功能
    @Operation(summary = "删除单条日志记录")
    @DeleteMapping("/{id}")
    @Log(module = "审计日志", operation = "删除日志")
    public Result deleteLog(@Parameter(description = "日志ID") @PathVariable Long id) {
        boolean isSuccess = auditLogService.removeById(id);
        return isSuccess ? Result.ok() : Result.fail("删除日志记录失败！");
    }

    // 【新增】整合原LogAdminController中的日志清理功能
    @Operation(summary = "清理指定天数前的日志")
    @DeleteMapping("/cleanup")
    @Log(module = "审计日志", operation = "清理过期日志")
    public Result cleanupLogs(@Parameter(description = "保留最近的天数") @RequestParam(defaultValue = "30") Integer days) {
        try {
            auditLogService.cleanupExpiredLogs(days);
            return Result.ok("清理任务已执行");
        } catch (Exception e) {
            log.error("清理审计日志失败", e); // 【修复】使用 @Slf4j 的 log
            return Result.fail("清理失败: " + e.getMessage());
        }
    }

    // ... 其他统计相关的接口保持不变 ...
    @Operation(summary = "获取审计统计信息")
    @GetMapping("/statistics")
    public Result getAuditStatistics() {
        return auditLogService.getAuditStatistics();
    }

    @Operation(summary = "获取登录统计")
    @GetMapping("/login-stats")
    public Result getLoginStatistics(
            @Parameter(description = "开始时间") @RequestParam(required = false) String startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) String endTime
    ) {
        return auditLogService.getLoginStatistics(startTime, endTime);
    }

    @Operation(summary = "获取操作统计")
    @GetMapping("/operation-stats")
    public Result getOperationStatistics(
            @Parameter(description = "开始时间") @RequestParam(required = false) String startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) String endTime
    ) {
        return auditLogService.getOperationStatistics(startTime, endTime);
    }

}