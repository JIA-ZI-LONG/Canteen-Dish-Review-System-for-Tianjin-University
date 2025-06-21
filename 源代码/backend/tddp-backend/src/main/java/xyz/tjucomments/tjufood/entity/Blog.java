// 文件路径: src/main/java/xyz/tjucomments/tjufood/entity/Blog.java

package xyz.tjucomments.tjufood.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "博客/笔记实体对象")
@Data
@TableName("tb_blog")
public class Blog {

    @Schema(description = "博客唯一ID", accessMode = Schema.AccessMode.READ_ONLY)
    // 用户生成内容，ID由程序生成
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @Schema(description = "作者的用户ID")
    private Long userId;

    @Schema(description = "博客标题")
    private String title;

    @Schema(description = "博客正文")
    private String content;

    @Schema(description = "封面图片URL")

    private String imageUrl;

    @Schema(description = "评分 (1-5分)")
    @TableField(exist = false) // 标记此字段不是数据库表中的列
    private Double rating;

    @Schema(description = "人均价格")
    @TableField(exist = false) // 标记此字段不是数据库表中的列
    private Double price;

    @Schema(description = "标签 (JSON格式)")
    @TableField(exist = false) // 标记此字段不是数据库表中的列
    private String tags;

    @Schema(description = "关联的食堂ID")
    @TableField(exist = false) // 标记此字段不是数据库表中的列，博客与食堂无关
    private Long canteenId;

    @Schema(description = "关联的窗口ID")
    @TableField(exist = false) // 标记此字段不是数据库表中的列，博客与窗口无关
    private Long stallId;

    @Schema(description = "博客类型 (1=美食评价, 2=普通分享)")
    @TableField(exist = false) // 标记此字段不是数据库表中的列，博客类型不存储在数据库中
    private Integer blogType;

    @Schema(description = "博客被点赞的数量")
    private Integer liked;

    @Schema(description = "博客被评论的数量")
    private Integer comments;

    @Schema(description = "博客被收藏的数量", accessMode = Schema.AccessMode.READ_ONLY)
    @TableField(exist = false) // 标记此字段不是数据库表中的列，收藏数通过Redis统计
    private Integer favorites;

    @Schema(description = "博客状态 (0=待审核, 1=审核通过, 2=审核拒绝, 3=用户隐藏, 4=管理员删除)")
    private Integer status;

    @Schema(description = "是否置顶 (1=是, 0=否)")
    private Integer isTop;

    @Schema(description = "是否为优质内容/加精 (1=是, 0=否)")
    private Integer isQuality;

    @Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createTime;

    @Schema(description = "更新时间", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updateTime;
}