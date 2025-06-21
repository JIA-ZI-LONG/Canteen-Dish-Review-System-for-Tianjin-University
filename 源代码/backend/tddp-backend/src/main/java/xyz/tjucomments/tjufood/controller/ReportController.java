package xyz.tjucomments.tjufood.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.entity.Report;
import xyz.tjucomments.tjufood.service.IReportService;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 前台用户举报接口
 */
@Tag(name = "05-1. 举报", description = "普通用户内容举报接口")
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Resource
    private IReportService reportService;

    /**
     * 提交举报
     * @param reportData 请求体：targetId, targetType, reason, description
     * @return Result 含举报记录ID
     */
    @Operation(summary = "提交举报")
    @PostMapping
    public Result submitReport(
            @Parameter(description = "举报数据")
            @RequestBody Map<String, Object> reportData) {

        Long targetId = reportData.get("targetId") == null ? null : Long.valueOf(reportData.get("targetId").toString());
        Integer targetType = reportData.get("targetType") == null ? null : Integer.valueOf(reportData.get("targetType").toString());
        String reason = (String) reportData.get("reason");
        String description = (String) reportData.get("description");

        if (targetId == null || targetType == null || !StringUtils.hasText(reason)) {
            return Result.fail("参数不完整");
        }

        Report report = new Report();
        report.setTargetId(targetId);
        report.setTargetType(targetType);
        report.setReason(reason);
        report.setDescription(description);
        report.setCreateTime(LocalDateTime.now());
        report.setStatus(0); // 0=待处理

        boolean saved = reportService.save(report);
        if (!saved) {
            return Result.fail("举报提交失败");
        }
        return Result.ok(report.getId());
    }
}
