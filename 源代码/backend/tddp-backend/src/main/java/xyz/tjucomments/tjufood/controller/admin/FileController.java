// 文件路径: src/main/java/xyz/tjucomments/tjufood/controller/admin/FileController.java

package xyz.tjucomments.tjufood.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.tjucomments.tjufood.aop.Log;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.service.IFileService;

import java.util.List;

@Slf4j
@Tag(name = "09. 文件管理", description = "统一文件上传管理接口")
@RestController("adminFileController")
@RequestMapping("/api/admin/files")
public class FileController {

    @Resource
    private IFileService fileService;

    @Operation(summary = "上传单个文件")
    @SecurityRequirement(name = "authorization")
    @PostMapping("/upload")
    @Log(module = "文件管理", operation = "上传文件")
    public Result uploadFile(
            @Parameter(description = "文件") @RequestParam("file") MultipartFile file,
            @Parameter(description = "关联实体ID") @RequestParam("entityId") Long entityId,
            @Parameter(description = "文件类型 (0=食堂, 1=校区, 2=窗口, 3=菜品, 4=轮播图)") @RequestParam("typeId") Integer typeId,
            @Parameter(description = "文件描述") @RequestParam(value = "description", required = false) String description) {
        try {
            return fileService.uploadEntityFile(file, entityId, typeId, description);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            return Result.fail("上传文件失败：" + e.getMessage());
        }
    }

    @Operation(summary = "批量上传文件")
    @SecurityRequirement(name = "authorization")
    @PostMapping("/upload/batch")
    @Log(module = "文件管理", operation = "批量上传文件")
    public Result uploadFiles(
            @Parameter(description = "文件列表") @RequestParam("files") List<MultipartFile> files,
            @Parameter(description = "关联实体ID") @RequestParam("entityId") Long entityId,
            @Parameter(description = "文件类型") @RequestParam("typeId") Integer typeId) {
        try {
            return fileService.uploadEntityFiles(files, entityId, typeId);
        } catch (Exception e) {
            log.error("批量上传文件失败", e);
            return Result.fail("批量上传文件失败：" + e.getMessage());
        }
    }

    @Operation(summary = "根据实体ID和类型查询文件列表")
    @SecurityRequirement(name = "authorization")
    @GetMapping("/entity/{entityId}/type/{typeId}")
    public Result getFilesByEntityAndType(
            @Parameter(description = "实体ID") @PathVariable Long entityId,
            @Parameter(description = "文件类型") @PathVariable Integer typeId) {
        try {
            return fileService.getFilesByEntityAndType(entityId, typeId);
        } catch (Exception e) {
            log.error("查询文件列表失败", e);
            return Result.fail("查询文件列表失败：" + e.getMessage());
        }
    }

    @Operation(summary = "根据类型查询所有文件")
    @SecurityRequirement(name = "authorization")
    @GetMapping("/type/{typeId}")
    public Result getFilesByType(@Parameter(description = "文件类型") @PathVariable Integer typeId) {
        try {
            return fileService.getFilesByType(typeId);
        } catch (Exception e) {
            log.error("查询文件列表失败", e);
            return Result.fail("查询文件列表失败：" + e.getMessage());
        }
    }

    @Operation(summary = "删除文件")
    @SecurityRequirement(name = "authorization")
    @DeleteMapping("/{fileId}")
    @Log(module = "文件管理", operation = "删除文件")
    public Result deleteFile(@Parameter(description = "文件ID") @PathVariable Long fileId) {
        try {
            return fileService.deleteEntityFile(fileId);
        } catch (Exception e) {
            log.error("删除文件失败", e);
            return Result.fail("删除文件失败：" + e.getMessage());
        }
    }

    @Operation(summary = "刷新文件URL")
    @SecurityRequirement(name = "authorization")
    @PostMapping("/{fileId}/refresh")
    @Log(module = "文件管理", operation = "刷新文件URL")
    public Result refreshFileUrl(@Parameter(description = "文件ID") @PathVariable Long fileId) {
        try {
            return fileService.refreshFileUrl(fileId);
        } catch (Exception e) {
            log.error("刷新文件URL失败", e);
            return Result.fail("刷新文件URL失败：" + e.getMessage());
        }
    }

    @Operation(summary = "批量刷新实体文件URL")
    @SecurityRequirement(name = "authorization")
    @PostMapping("/entity/{entityId}/type/{typeId}/refresh")
    @Log(module = "文件管理", operation = "批量刷新文件URL")
    public Result refreshEntityFileUrls(
            @Parameter(description = "实体ID") @PathVariable Long entityId,
            @Parameter(description = "文件类型") @PathVariable Integer typeId) {
        try {
            return fileService.refreshEntityFileUrls(entityId, typeId);
        } catch (Exception e) {
            log.error("批量刷新文件URL失败", e);
            return Result.fail("批量刷新文件URL失败：" + e.getMessage());
        }
    }

    @Operation(summary = "上传食堂图片")
    @SecurityRequirement(name = "authorization")
    @PostMapping("/canteen/{canteenId}/upload")
    @Log(module = "文件管理", operation = "上传食堂图片")
    public Result uploadCanteenImage(
            @Parameter(description = "食堂ID") @PathVariable Long canteenId,
            @Parameter(description = "图片文件") @RequestParam("file") MultipartFile file,
            @Parameter(description = "图片描述") @RequestParam(value = "description", required = false) String description) {
        return fileService.uploadEntityFile(file, canteenId, 0, description);
    }

    @Operation(summary = "上传校区图片")
    @SecurityRequirement(name = "authorization")
    @PostMapping("/campus/{campusId}/upload")
    @Log(module = "文件管理", operation = "上传校区图片")
    public Result uploadCampusImage(
            @Parameter(description = "校区ID") @PathVariable Long campusId,
            @Parameter(description = "图片文件") @RequestParam("file") MultipartFile file,
            @Parameter(description = "图片描述") @RequestParam(value = "description", required = false) String description) {
        return fileService.uploadEntityFile(file, campusId, 1, description);
    }

    @Operation(summary = "上传窗口图片")
    @SecurityRequirement(name = "authorization")
    @PostMapping("/stall/{stallId}/upload")
    @Log(module = "文件管理", operation = "上传窗口图片")
    public Result uploadStallImage(
            @Parameter(description = "窗口ID") @PathVariable Long stallId,
            @Parameter(description = "图片文件") @RequestParam("file") MultipartFile file,
            @Parameter(description = "图片描述") @RequestParam(value = "description", required = false) String description) {
        return fileService.uploadEntityFile(file, stallId, 2, description);
    }

    @Operation(summary = "上传菜品图片")
    @SecurityRequirement(name = "authorization")
    @PostMapping("/dish/{dishId}/upload")
    @Log(module = "文件管理", operation = "上传菜品图片")
    public Result uploadDishImage(
            @Parameter(description = "菜品ID") @PathVariable Long dishId,
            @Parameter(description = "图片文件") @RequestParam("file") MultipartFile file,
            @Parameter(description = "图片描述") @RequestParam(value = "description", required = false) String description) {
        return fileService.uploadEntityFile(file, dishId, 3, description);
    }

    @Operation(summary = "上传轮播图")
    @SecurityRequirement(name = "authorization")
    @PostMapping("/banner/upload")
    @Log(module = "文件管理", operation = "上传轮播图")
    public Result uploadBannerImage(
            @Parameter(description = "图片文件") @RequestParam("file") MultipartFile file,
            @Parameter(description = "图片描述") @RequestParam(value = "description", required = false) String description) {
        // 轮播图使用固定的entityId=0
        return fileService.uploadEntityFile(file, 0L, 4, description);
    }
}
