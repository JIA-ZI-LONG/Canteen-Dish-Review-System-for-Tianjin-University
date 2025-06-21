// 文件路径: new_yxq/back/src/main/java/xyz/tjucomments/tjufood/controller/admin/ExportAdminController.java

package xyz.tjucomments.tjufood.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import xyz.tjucomments.tjufood.aop.Log;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.service.IDatabaseExportService; // 下一步创建

@Tag(name = "F06. 工具箱 - 数据导出", description = "提供数据库备份和日志导出功能")
@SecurityRequirement(name = "authorization")
@RestController
@RequestMapping("/api/admin/toolbox")
public class ExportAdminController {

    @Resource
    private IDatabaseExportService databaseExportService; // 下一步创建

    @Operation(summary = "导出数据库备份")
    @PostMapping("/export-db")
    @Log(module = "工具箱", operation = "导出数据库备份")
    public Result exportDatabase() {
        try {
            String fileUrl = databaseExportService.exportDatabase();
            return Result.ok(fileUrl);
        } catch (Exception e) {
            return Result.fail("数据库备份失败: " + e.getMessage());
        }
    }
}