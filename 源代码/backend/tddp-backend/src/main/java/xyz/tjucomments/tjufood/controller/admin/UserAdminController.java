// 文件路径: src/main/java/xyz/tjucomments/tjufood/controller/admin/UserAdminController.java

package xyz.tjucomments.tjufood.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.entity.User;
import xyz.tjucomments.tjufood.service.IUserService;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "C01. 社区管理 - 用户管理", description = "对普通用户信息进行查询和状态管理")
@SecurityRequirement(name = "authorization")
@RestController
@RequestMapping("/api/admin/users")
public class UserAdminController {

    @Resource
    private IUserService userService;

    @Operation(summary = "分页查询用户列表", description = "支持按用户ID、昵称、邮箱进行筛选")
    @GetMapping
    public Result getUsers(
            @Parameter(description = "用户ID") @RequestParam(required = false) Long id,
            @Parameter(description = "用户昵称关键字") @RequestParam(required = false) String nickname,
            @Parameter(description = "用户邮箱") @RequestParam(required = false) String email,
            @Parameter(description = "当前页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") Integer size
    ) {
        // 构建查询条件
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (id != null) {
            queryWrapper.eq(User::getId, id);
        }
        if (StringUtils.hasText(nickname)) {
            queryWrapper.like(User::getNickname, nickname);
        }
        if (StringUtils.hasText(email)) {
            queryWrapper.like(User::getEmail, email);
        }
        queryWrapper.orderByDesc(User::getCreateTime);

        // 执行分页查询
        Page<User> page = userService.page(new Page<>(current, size), queryWrapper);

        // 返回分页结构
        Map<String, Object> result = new HashMap<>();
        result.put("records", page.getRecords());
        result.put("total", page.getTotal());
        return Result.ok(result);
    }

    @Operation(summary = "部分更新用户信息", description = "支持更新用户状态等字段 (0=正常, 1=禁用)")
    @PatchMapping("/{id}")
    public Result patchUser(
            @Parameter(description = "用户ID") @PathVariable Long id,
            @Parameter(description = "包含要更新字段的请求体") @RequestBody Map<String, Object> updates) {

        User user = userService.getById(id);
        if (user == null) {
            return Result.fail("用户不存在！");
        }

        // 处理状态更新
        if (updates.containsKey("status")) {
            Integer newStatus = (Integer) updates.get("status");
            if (newStatus == null || (newStatus != 0 && newStatus != 1)) {
                return Result.fail("无效的用户状态值！必须是0或1。");
            }
            user.setStatus(newStatus);
        }

        // 可以扩展处理其他字段的部分更新

        boolean isSuccess = userService.updateById(user);
        return isSuccess ? Result.ok() : Result.fail("更新用户信息失败！");
    }

    @Operation(summary = "查询单个用户详情", description = "获取指定ID用户的详细信息")
    @GetMapping("/{id}")
    public Result getUser(@Parameter(description = "用户ID") @PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null) {
            return Result.fail("用户不存在！");
        }
        // 出于安全考虑，手动将密码设置为空，不返回给前端
        user.setPassword(null);
        return Result.ok(user);
    }
}