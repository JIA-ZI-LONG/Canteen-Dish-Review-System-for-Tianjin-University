// 文件路径: src/main/java/xyz/tjucomments/tjufood/controller/VoucherController.java
package xyz.tjucomments.tjufood.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import xyz.tjucomments.tjufood.aop.Log;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.service.IVoucherService;

@Slf4j
@Tag(name = "08. 优惠券功能", description = "用户查询和兑换优惠券的接口")
@RestController
@RequestMapping("/api/vouchers")
public class VoucherController {

    @Operation(summary = "分页条件查询优惠券列表")
    @GetMapping
    public Result getVouchers(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Integer type,
            @RequestParam(required = false) Long canteenId,
            @RequestParam(required = false) Integer status) {
        try {
            return voucherService.searchVouchers(current, size, type, canteenId, status);
        } catch (Exception e) {
            log.error("查询优惠券列表失败", e);
            return Result.fail("查询优惠券列表失败：" + e.getMessage());
        }
    }

    @Resource
    private IVoucherService voucherService;

    @Operation(summary = "查询指定窗口可用的优惠券列表")
    @GetMapping("/stalls/{stallId}")
    public Result getVouchersByStall(@Parameter(description = "窗口唯一标识ID") @PathVariable Long stallId) {
        try {
            return voucherService.queryVouchersByStallId(stallId);
        } catch (Exception e) {
            log.error("查询窗口优惠券失败", e);
            return Result.fail("查询窗口优惠券失败：" + e.getMessage());
        }
    }

    @Operation(summary = "兑换（秒杀）一张优惠券")
    @SecurityRequirement(name = "authorization")
    @PatchMapping("/{id}/redeem")
    @Log(module = "优惠券模块", operation = "兑换优惠券")
    public Result redeemVoucher(@Parameter(description = "优惠券模板唯一ID") @PathVariable("id") Long voucherId) {
        try {
            // 调用我们重构后的redeemVoucher方法
            return voucherService.redeemVoucher(voucherId);
        } catch (Exception e) {
            log.error("兑换优惠券失败", e);
            return Result.fail("兑换优惠券失败：" + e.getMessage());
        }
    }

    @Operation(summary = "查询我的优惠券")
    @SecurityRequirement(name = "authorization")
    @GetMapping("/my")
    public Result getMyVouchers() {
        return voucherService.queryMyVouchers();
    }

    @Operation(summary = "获取单个优惠券详情")
    @GetMapping("/{id}")
    public Result getVoucher(@Parameter(description = "优惠券ID") @PathVariable Long id) {
        // 这里需要在VoucherService中添加getById方法
        return Result.ok(voucherService.getById(id));
    }
}