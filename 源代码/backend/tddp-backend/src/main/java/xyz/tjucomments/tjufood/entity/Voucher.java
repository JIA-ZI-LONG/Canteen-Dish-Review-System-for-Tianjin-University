// 文件路径: src/main/java/xyz/tjucomments/tjufood/entity/Voucher.java

package xyz.tjucomments.tjufood.entity;

// 【新增】导入JsonFormat注解
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "优惠券模板实体对象")
@Data
@TableName("tb_voucher")
public class Voucher {

    @Schema(description = "优惠券模板唯一ID", accessMode = Schema.AccessMode.READ_ONLY)
    // 【核心修正】将ID生成策略从数据库自增(AUTO)改为程序输入(INPUT)
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @Schema(description = "优惠券标题")
    private String title;

    @Schema(description = "详细描述")
    private String description;

    @Schema(description = "优惠券类型 (0=满减券, 1=折扣券)")
    private Integer type;

    @Schema(description = "优惠金额 (用于满减券)")
    private BigDecimal price;

    @Schema(description = "折扣率 (用于折扣券, 如0.88代表88折)")
    private BigDecimal discount;

    @Schema(description = "最低消费金额")
    private BigDecimal minAmount;

    @Schema(description = "限定使用的食堂ID (为NULL则全场通用)")
    private Long canteenId;

    @Schema(description = "生效时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @Schema(description = "过期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @Schema(description = "库存数量")
    private Integer stock;

    @Schema(description = "兑换所需积分")
    private Integer requiredCredits;

    @Schema(description = "优惠券状态 (0=未开始, 1=进行中, 2=已结束)")
    private Integer status;

    @Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createTime;

    @Schema(description = "更新时间", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updateTime;


}