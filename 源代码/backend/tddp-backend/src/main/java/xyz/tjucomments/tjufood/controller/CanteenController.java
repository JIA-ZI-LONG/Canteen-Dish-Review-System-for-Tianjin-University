package xyz.tjucomments.tjufood.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;

import org.springframework.web.bind.annotation.*;
import xyz.tjucomments.tjufood.dto.Result;

import xyz.tjucomments.tjufood.entity.Canteen;
import xyz.tjucomments.tjufood.service.ICanteenService;



@Tag(name = "02. 食堂管理", description = "食堂信息的查接口")
@RestController
@RequestMapping("/api/canteens")
public class CanteenController {
    @Resource
    private ICanteenService canteenService;
    
    @Operation(summary = "查询食堂列表", description = "支持按校区过滤，支持获取所有食堂")
    @GetMapping
    public Result getCanteens(
            @RequestParam(value = "campusId", required = false) Long campusId,
            @RequestParam(value = "all", defaultValue = "false") boolean all) {

        if (all) {
            // 查询所有食堂，不分页，用于下拉选择
            return Result.ok(canteenService.list(
                    new QueryWrapper<Canteen>().orderByAsc("id")
            ));
        } else {
            // 默认查询，支持按校区过滤
            return canteenService.listCanteens(campusId);
        }
    }

    @Operation(summary = "查询单个食堂详情")
    @GetMapping("/{id}")
    public Result getCanteen(@Parameter(description = "食堂的唯一ID", required = true) @PathVariable("id") Long id) {
        return canteenService.queryCanteenById(id);
    }


}
