package xyz.tjucomments.tjufood.dto;

import lombok.Data;

@Data
public class AdminDTO {
    private Long id;
    private String username;
    private String name;
    /**
     * 【新增】管理员头像URL字段
     */
    private String avatar;
}