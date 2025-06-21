package xyz.tjucomments.tjufood.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.entity.Notice;
import xyz.tjucomments.tjufood.service.INoticeService;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "11. 系统公告", description = "前台用户查看系统公告的接口")
@RestController
@RequestMapping("/api/notices")
public class NoticeController {

    @Resource
    private INoticeService noticeService;

    @Operation(summary = "获取系统公告列表")
    @GetMapping
    public Result getNoticeList(
            @Parameter(description = "当前页码") @RequestParam(value = "current", defaultValue = "1") Integer current,
            @Parameter(description = "每页大小") @RequestParam(value = "size", defaultValue = "10") Integer size,
            @Parameter(description = "公告类型 (0=普通, 1=重要)") @RequestParam(value = "type", required = false) Integer type) {
        
        // 创建分页对象
        Page<Notice> page = new Page<>(current, size);
        
        // 构建查询条件
        LambdaQueryWrapper<Notice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Notice::getStatus, 1) // 只查询已发布的公告
                .le(Notice::getPublishTime, LocalDateTime.now()) // 发布时间小于等于当前时间
                .orderByDesc(Notice::getType) // 重要公告优先
                .orderByDesc(Notice::getPublishTime); // 按发布时间倒序
        
        // 如果指定了类型，添加类型过滤
        if (type != null) {
            queryWrapper.eq(Notice::getType, type);
        }
        
        // 执行分页查询
        IPage<Notice> result = noticeService.page(page, queryWrapper);
        
        return Result.ok(result);
    }

    @Operation(summary = "根据ID获取公告详情")
    @GetMapping("/{id}")
    public Result getNoticeById(@Parameter(description = "公告ID") @PathVariable Long id) {
        Notice notice = noticeService.getById(id);
        
        if (notice == null) {
            return Result.fail("公告不存在");
        }
        
        // 检查公告是否已发布且在发布时间内
        if (notice.getStatus() != 1 || notice.getPublishTime().isAfter(LocalDateTime.now())) {
            return Result.fail("公告不存在或未发布");
        }
        
        return Result.ok(notice);
    }

    @Operation(summary = "获取最新公告")
    @GetMapping("/latest")
    public Result getLatestNotices(@Parameter(description = "获取数量") @RequestParam(value = "limit", defaultValue = "5") Integer limit) {
        
        // 构建查询条件
        LambdaQueryWrapper<Notice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Notice::getStatus, 1) // 只查询已发布的公告
                .le(Notice::getPublishTime, LocalDateTime.now()) // 发布时间小于等于当前时间
                .orderByDesc(Notice::getType) // 重要公告优先
                .orderByDesc(Notice::getPublishTime) // 按发布时间倒序
                .last("LIMIT " + limit); // 限制数量
        
        List<Notice> notices = noticeService.list(queryWrapper);
        
        return Result.ok(notices);
    }

    @Operation(summary = "获取重要公告")
    @GetMapping("/important")
    public Result getImportantNotices() {
        
        // 构建查询条件
        LambdaQueryWrapper<Notice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Notice::getStatus, 1) // 只查询已发布的公告
                .eq(Notice::getType, 1) // 只查询重要公告
                .le(Notice::getPublishTime, LocalDateTime.now()) // 发布时间小于等于当前时间
                .orderByDesc(Notice::getPublishTime); // 按发布时间倒序
        
        List<Notice> notices = noticeService.list(queryWrapper);
        
        return Result.ok(notices);
    }
}
