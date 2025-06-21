package xyz.tjucomments.tjufood.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.interceptor.UserHolder;
import xyz.tjucomments.tjufood.service.ISearchService;

@Tag(name = "09. 综合搜索", description = "搜索食堂/菜品/博客/窗口")
@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Resource
    private ISearchService searchService;

    @Operation(summary = "综合搜索")
    @GetMapping
    public Result search(
            @Parameter(description = "关键词", required = true) @RequestParam("keyword") String keyword,
            @Parameter(description = "类型:canteen|dish|blog|stall|all", required = false)
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Long userId = UserHolder.getUser() == null ? null : UserHolder.getUser().getId();
        return searchService.search(keyword, type, current, size, userId);
    }
}
