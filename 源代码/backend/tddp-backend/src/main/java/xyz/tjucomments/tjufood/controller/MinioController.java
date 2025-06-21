// MinIO文件管理控制器
package xyz.tjucomments.tjufood.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.service.IMinioUrlService;

@Tag(name = "12. MinIO文件管理", description = "MinIO文件存储和URL管理接口")
@RestController
@RequestMapping("/api/minio")
public class MinioController {

    @Resource
    private IMinioUrlService minioUrlService;

    @Operation(summary = "刷新文件访问URL", description = "刷新即将过期或已过期的MinIO预签名URL")
    @PostMapping("/refresh-url")
    public Result refreshUrl(@Parameter(description = "需要刷新的URL") @RequestBody String url) {
        try {
            String newUrl = minioUrlService.refreshUrl(url);
            return Result.ok(newUrl);
        } catch (Exception e) {
            return Result.fail("URL刷新失败：" + e.getMessage());
        }
    }

    @Operation(summary = "生成文件访问URL", description = "为指定的文件对象生成新的预签名URL")
    @GetMapping("/generate-url/{objectName}")
    public Result generateUrl(@Parameter(description = "文件对象名称") @PathVariable String objectName) {
        try {
            String url = minioUrlService.generatePresignedUrl(objectName);
            return Result.ok(url);
        } catch (Exception e) {
            return Result.fail("URL生成失败：" + e.getMessage());
        }
    }

    @Operation(summary = "检查文件是否存在", description = "检查MinIO中指定的文件对象是否存在")
    @GetMapping("/exists/{objectName}")
    public Result checkExists(@Parameter(description = "文件对象名称") @PathVariable String objectName) {
        try {
            boolean exists = minioUrlService.objectExists(objectName);
            return Result.ok(exists);
        } catch (Exception e) {
            return Result.fail("检查文件失败：" + e.getMessage());
        }
    }

    @Operation(summary = "删除文件", description = "删除MinIO中指定的文件对象")
    @DeleteMapping("/{objectName}")
    public Result deleteFile(@Parameter(description = "文件对象名称") @PathVariable String objectName) {
        try {
            boolean success = minioUrlService.deleteObject(objectName);
            return success ? Result.ok("文件删除成功") : Result.fail("文件删除失败");
        } catch (Exception e) {
            return Result.fail("删除文件失败：" + e.getMessage());
        }
    }

    @Operation(summary = "获取公共访问URL", description = "获取文件的公共访问URL（如果bucket是公共的）")
    @GetMapping("/public-url/{objectName}")
    public Result getPublicUrl(@Parameter(description = "文件对象名称") @PathVariable String objectName) {
        try {
            String url = minioUrlService.getPublicUrl(objectName);
            return Result.ok(url);
        } catch (Exception e) {
            return Result.fail("获取公共URL失败：" + e.getMessage());
        }
    }
}
