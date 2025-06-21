// 路径: /final/back/src/main/java/xyz/tjucomments/tjufood/controller/admin/CanteenAdminController.java
package xyz.tjucomments.tjufood.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile; // 【新增】导入
import xyz.tjucomments.tjufood.dto.CanteenDTO;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.entity.Canteen;
import xyz.tjucomments.tjufood.entity.Campus;
import xyz.tjucomments.tjufood.entity.CanteenType;
import xyz.tjucomments.tjufood.service.ICanteenService;
import xyz.tjucomments.tjufood.service.ICampusService;
import xyz.tjucomments.tjufood.service.ICanteenTypeService;
import xyz.tjucomments.tjufood.service.IFileService; // 【新增】导入

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "B02. 内容管理 - 食堂管理", description = "对食堂信息进行增删改查及图片管理") // 【修改】更新描述
@SecurityRequirement(name = "authorization")
@RestController
@RequestMapping("/api/admin/canteens")
public class CanteenAdminController {

    @Resource
    private ICanteenService canteenService;
    @Resource
    private ICampusService campusService;
    @Resource
    private ICanteenTypeService canteenTypeService;

    // 【新增】注入文件服务
    @Resource
    private IFileService fileService;

    // 【新增】定义文件类型常量，代码更清晰
    private static final int FILE_TYPE_CANTEEN = 0;

    @Operation(summary = "创建食堂")
    @PostMapping
    public Result createCanteen(@RequestBody Canteen canteen) {
        boolean isSuccess = canteenService.save(canteen);
        return isSuccess ? Result.ok(canteen.getId()) : Result.fail("创建食堂失败！");
    }

    @Operation(summary = "删除食堂")
    @DeleteMapping("/{id}")
    public Result deleteCanteen(@Parameter(description = "食堂ID", required = true) @PathVariable Long id) {
        boolean isSuccess = canteenService.removeById(id);
        return isSuccess ? Result.ok() : Result.fail("删除食堂失败！");
    }

    @Operation(summary = "更新食堂信息")
    @PutMapping("/{id}")
    public Result updateCanteen(@PathVariable Long id, @RequestBody Canteen canteen) {
        canteen.setId(id); // 确保ID一致性
        if (id == null) {
            return Result.fail("更新失败，食堂ID不能为空");
        }
        boolean isSuccess = canteenService.updateById(canteen);
        return isSuccess ? Result.ok() : Result.fail("更新食堂失败！");
    }

    @Operation(summary = "分页查询食堂列表")
    @GetMapping
    public Result getCanteens(
            @Parameter(description = "当前页码") @RequestParam(value = "current", defaultValue = "1") Integer current,
            @Parameter(description = "每页数量") @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        // 1. 创建 QueryWrapper 并添加排序条件，解决SQL语法错误
        QueryWrapper<Canteen> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");

        // 2. 先进行标准的分页查询
        Page<Canteen> page = canteenService.page(new Page<>(current, size), queryWrapper);
        List<Canteen> records = page.getRecords();
        if (records.isEmpty()) {
            return Result.ok(Collections.emptyList(), 0L);
        }

        // 3. 提取关联ID
        List<Long> campusIds = records.stream().map(Canteen::getCampusId).distinct().collect(Collectors.toList());
        List<Long> typeIds = records.stream().map(Canteen::getTypeId).distinct().collect(Collectors.toList());

        // 4. 一次性查出所有关联的名称
        Map<Long, String> campusMap = campusService.listByIds(campusIds).stream()
                .collect(Collectors.toMap(Campus::getId, Campus::getName));
        Map<Long, String> typeMap = canteenTypeService.listByIds(typeIds).stream()
                .collect(Collectors.toMap(CanteenType::getId, CanteenType::getName));

        // 5. 组装成 DTO 列表
        List<CanteenDTO> dtoList = records.stream().map(canteen -> {
            CanteenDTO dto = CanteenDTO.fromEntity(canteen);
            dto.setCampusName(campusMap.get(canteen.getCampusId()));
            dto.setTypeName(typeMap.get(canteen.getTypeId()));
            return dto;
        }).collect(Collectors.toList());

        // 6. 返回最终结果 - 包装成分页结构
        Map<String, Object> result = new HashMap<>();
        result.put("records", dtoList);
        result.put("total", page.getTotal());
        return Result.ok(result);
    }


    @Operation(summary = "查询所有食堂列表（不分页）", description = "用于其他模块的下拉选择")
    @GetMapping("/all")
    public Result getAllCanteens() {
        // 查询所有食堂，不分页
        return Result.ok(canteenService.list(
                new QueryWrapper<Canteen>().orderByAsc("id")
        ));
    }

    @Operation(summary = "根据ID查询单个食堂")
    @GetMapping("/{id}")
    public Result getCanteenById(@Parameter(description = "食堂ID", required = true) @PathVariable Long id) {
        Canteen canteen = canteenService.getById(id);
        if (canteen == null) {
            return Result.fail("食堂不存在！");
        }
        return Result.ok(canteen);
    }

    // 【新增】上传食堂图片接口
    @Operation(summary = "上传食堂图片")
    @PostMapping("/{id}/upload")
    public Result uploadCanteenImage(
            @Parameter(description = "食堂ID") @PathVariable Long id,
            @Parameter(description = "图片文件") @RequestParam("file") MultipartFile file) {
        // 调用集中的文件服务来处理上传
        return fileService.uploadEntityFile(file, id, FILE_TYPE_CANTEEN, "食堂图片");
    }

    // 【新增】查询指定食堂的图片列表接口
    @Operation(summary = "查询食堂图片列表")
    @GetMapping("/{id}/files")
    public Result getCanteenFiles(@Parameter(description = "食堂ID") @PathVariable Long id) {
        return fileService.getFilesByEntityAndType(id, FILE_TYPE_CANTEEN);
    }
}