package xyz.tjucomments.tjufood.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.entity.Campus;
import xyz.tjucomments.tjufood.service.ICampusService;

import java.util.List;

/**
 * 前台 - 校区接口
 * 提供无权限限制的校区查询，供博客 / 食堂 / 菜品等页面下拉使用。
 */
@Tag(name = "02. 校区信息", description = "提供校区下拉等公共查询接口")
@RestController
@RequestMapping("/api/campuses")
public class CampusController {

    @Resource
    private ICampusService campusService;

    @Operation(summary = "查询所有校区列表")
    @GetMapping
    public Result getCampuses() {
        List<Campus> list = campusService.list();
        return Result.ok(list);
    }

    @Operation(summary = "根据ID查询单个校区")
    @GetMapping("/{id}")
    public Result getCampus(@Parameter(description = "校区ID", required = true) @PathVariable Long id) {
        Campus campus = campusService.getById(id);
        if (campus == null) {
            return Result.fail("校区不存在！");
        }
        return Result.ok(campus);
    }
}
