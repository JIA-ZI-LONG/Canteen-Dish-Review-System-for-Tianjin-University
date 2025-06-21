package xyz.tjucomments.tjufood.dto;

import lombok.Data;

/**
 * 登录成功后返回给前端的数据视图对象 (View Object)
 */
@Data
public class LoginSuccessVO {
    private String token;
    private Long id;
    private String nickname;
    private String icon;
}