// 文件路径: src/main/java/xyz/tjucomments/tjufood/controller/admin/VoucherAdminController.java

package xyz.tjucomments.tjufood.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.entity.Voucher;
import xyz.tjucomments.tjufood.service.IVoucherService;
import xyz.tjucomments.tjufood.utils.id.RedisIdWorker;
import java.util.Map; // 新增导入
import java.util.HashMap; // 新增导入

@Tag(name = "D02. 运营管理 - 优惠券管理", description = "对优惠券模板进行增删改查")
@SecurityRequirement(name = "authorization")
@RestController
@RequestMapping("/api/admin/vouchers")
public class VoucherAdminController {


    @Resource
    private IVoucherService voucherService;

    // 注入 StringRedisTemplate
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisIdWorker redisIdWorker; // 【新增】注入ID生成器

    @Operation(summary = "创建优惠券模板")
    @PostMapping
    public Result createVoucher(@RequestBody Voucher voucher) {
        // 【核心修正】在保存前，手动生成并设置ID
        long voucherId = redisIdWorker.nextId("voucher");
        voucher.setId(voucherId);

        boolean isSuccess = voucherService.save(voucher);
        if (isSuccess && voucher.getStock() != null) {
            String stockKey = "seckill:stock:" + voucher.getId();
            stringRedisTemplate.opsForValue().set(stockKey, voucher.getStock().toString());
        }
        return isSuccess ? Result.ok(voucher.getId()) : Result.fail("新增优惠券失败！");
    }

    @Operation(summary = "更新优惠券模板")
    @PutMapping("/{id}")
    public Result updateVoucher(@PathVariable Long id, @RequestBody Voucher voucher) {
        voucher.setId(id); // 确保ID一致性
        if (id == null) {
            return Result.fail("更新失败，ID不能为空");
        }
        boolean isSuccess = voucherService.updateById(voucher);
        if (isSuccess) {
            // 【核心新增】更新库存到Redis
            String stockKey = "seckill:stock:" + voucher.getId();
            stringRedisTemplate.opsForValue().set(stockKey, voucher.getStock().toString());
        }
        return isSuccess ? Result.ok() : Result.fail("更新优惠券失败！");
    }

    @Operation(summary = "分页查询优惠券模板列表")
    @GetMapping
    public Result getVouchers(
            @Parameter(description = "优惠券标题关键词") @RequestParam(required = false) String title,
            @Parameter(description = "优惠券状态 (1=正常, 2=暂停)") @RequestParam(required = false) Integer status,
            @Parameter(description = "当前页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") Integer size
    ) {
        QueryWrapper<Voucher> queryWrapper = new QueryWrapper<>();
        if (StringUtils.hasText(title)) {
            queryWrapper.like("title", title);
        }
        if (status != null) {
            queryWrapper.eq("status", status);
        }
        // 【重要】为分页查询添加排序，避免在某些数据库（如SQL Server）下报错
        queryWrapper.orderByDesc("create_time");

        Page<Voucher> page = voucherService.page(new Page<>(current, size), queryWrapper);

        // 【核心修正】将分页结果包装成 Map，以匹配前端期望的 { records: [], total: x } 格式
        Map<String, Object> resultData = new HashMap<>();
        resultData.put("records", page.getRecords());
        resultData.put("total", page.getTotal());

        return Result.ok(resultData);
    }

    @Operation(summary = "获取单个优惠券详情")
    @GetMapping("/{id}")
    public Result getVoucher(@PathVariable Long id) {
        Voucher voucher = voucherService.getById(id);
        if (voucher == null) {
            return Result.fail("优惠券不存在");
        }
        return Result.ok(voucher);
    }

    @Operation(summary = "删除优惠券")
    @DeleteMapping("/{id}")
    public Result deleteVoucher(@PathVariable Long id) {
        boolean isSuccess = voucherService.removeById(id);
        return isSuccess ? Result.ok() : Result.fail("删除优惠券失败");
    }

    @Operation(summary = "部分更新优惠券（如状态）")
    @PatchMapping("/{id}")
    public Result patchVoucher(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Voucher voucher = voucherService.getById(id);
        if (voucher == null) {
            return Result.fail("未找到该优惠券");
        }

        // 处理状态更新
        if (updates.containsKey("status")) {
            Integer status = (Integer) updates.get("status");
            if (status == null || (status < 0 || status > 2)) {
                return Result.fail("无效的状态值，必须是0-2之间");
            }
            voucher.setStatus(status);
        }

        // 可以扩展处理其他字段的部分更新

        boolean isSuccess = voucherService.updateById(voucher);
        return isSuccess ? Result.ok() : Result.fail("更新失败");
    }
}