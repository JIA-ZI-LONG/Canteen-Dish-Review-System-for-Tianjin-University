package xyz.tjucomments.tjufood.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.entity.Role;
import xyz.tjucomments.tjufood.service.IRoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;

@Tag(name = "E02. 平台设置 - 角色管理", description = "后台角色增删改查及权限分配")
@SecurityRequirement(name = "authorization")
@RestController
@RequestMapping("/api/admin/roles")
public class RoleAdminController {

    private static final String PERMISSION_PATH = "/{id}/permissions";

    @Resource
    private IRoleService roleService;

    /**
     * 分页查询角色列表
     * @param current 当前页码
     * @param size 每页数量
     * @param name 角色名称 (用于搜索)
     * @return 角色分页数据
     */
    @GetMapping
    public Result getRoles(@RequestParam(value = "current", defaultValue = "1") Integer current,
                            @RequestParam(value = "size", defaultValue = "10") Integer size,
                            @RequestParam(value = "name", required = false) String name) {
        Page<Role> page = new Page<>(current, size);
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            queryWrapper.like("name", name);
        }
        queryWrapper.orderByDesc("create_time");
        roleService.page(page, queryWrapper);
        return Result.ok(page);
    }

    /**
     * 新增角色
     * @param role 角色信息
     * @return
     */
    @PostMapping
    public Result addRole(@RequestBody Role role) {
        // 设置默认状态和创建时间
        if (role.getStatus() == null) {
            role.setStatus(1); // 默认为正常状态
        }
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        boolean success = roleService.save(role);
        return success ? Result.ok() : Result.fail("新增角色失败");
    }

    /**
     * 修改角色信息 (不包括状态)
     * @param role 角色信息
     * @return
     */
    @PutMapping
    public Result updateRole(@RequestBody Role role) {
        role.setUpdateTime(LocalDateTime.now());
        boolean success = roleService.updateById(role);
        return success ? Result.ok() : Result.fail("修改角色失败");
    }

    /**
     * 获取角色已分配的权限ID列表
     */
    @Operation(summary = "获取角色权限ID列表")
    @GetMapping(PERMISSION_PATH)
    public Result getRolePermissions(@PathVariable("id") Long id) {
        return roleService.getPermissionIdsByRoleId(id);
    }

    /**
     * 更新角色的权限列表
     */
    @Operation(summary = "更新角色权限")
    @PutMapping(PERMISSION_PATH)
    public Result updateRolePermissions(@PathVariable("id") Long id,
                                        @RequestBody List<Long> permissionIds) {
        return roleService.assignPermissionsToRole(id, permissionIds);
    }
}