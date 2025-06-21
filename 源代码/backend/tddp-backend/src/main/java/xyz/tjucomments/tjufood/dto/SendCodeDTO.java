package xyz.tjucomments.tjufood.dto;

import lombok.Data;

@Data
public class SendCodeDTO {
    // 邮箱地址
    private String email;
    // 验证类型：0-注册，1-找回密码
    private Integer type;
}