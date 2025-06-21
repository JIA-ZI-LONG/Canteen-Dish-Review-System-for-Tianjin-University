// 文件路径: src/main/java/xyz/tjucomments/tjufood/entity/File.java

package xyz.tjucomments.tjufood.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "文件管理实体对象")
@Data
@TableName("tb_file")
public class File {

    @Schema(description = "文件唯一ID", accessMode = Schema.AccessMode.READ_ONLY)
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @Schema(description = "关联对象ID (食堂ID、校区ID、窗口ID、菜品ID等)")
    @TableField("entity_id")
    private Long entityId;

    @Schema(description = "文件类型 (0=食堂, 1=校区, 2=窗口, 3=菜品, 4=轮播图)")
    @TableField("type_id")
    private Integer typeId;

    @Schema(description = "文件访问URL")
    @TableField("url")
    private String url;

    @Schema(description = "MinIO对象名称")
    @TableField("object_name")
    private String objectName;

    @Schema(description = "原始文件名")
    @TableField("original_name")
    private String originalName;

    @Schema(description = "文件大小(字节)")
    @TableField("file_size")
    private Long fileSize;

    @Schema(description = "文件MIME类型")
    @TableField("content_type")
    private String contentType;

    @Schema(description = "文件描述")
    @TableField("description")
    private String description;

    @Schema(description = "排序顺序")
    @TableField("sort_order")
    private Integer sortOrder;

    @Schema(description = "状态 (0=禁用, 1=启用)")
    @TableField("status")
    private Integer status;

    @Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
    @TableField("create_time")
    private LocalDateTime createTime;

    @Schema(description = "更新时间", accessMode = Schema.AccessMode.READ_ONLY)
    @TableField("update_time")
    private LocalDateTime updateTime;

    @Schema(description = "创建者ID")
    @TableField("creator_id")
    private Long creatorId;

    @Schema(description = "更新者ID")
    @TableField("updater_id")
    private Long updaterId;
}
