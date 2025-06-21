package xyz.tjucomments.tjufood.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 搜索历史实体
 */
@Data
@TableName("tb_search_history")
@Schema(description = "搜索历史实体")
public class SearchHistory {

    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "搜索关键字")
    private String keyword;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
