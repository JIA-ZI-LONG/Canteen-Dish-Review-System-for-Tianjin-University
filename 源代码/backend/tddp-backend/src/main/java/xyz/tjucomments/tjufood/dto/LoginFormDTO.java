package xyz.tjucomments.tjufood.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "用户登录请求数据")
public class LoginFormDTO {

    @NotBlank(message = "账号不能为空")
    @Schema(description = "用户账号（用户ID或邮箱）", requiredMode = Schema.RequiredMode.REQUIRED, example = "user123@example.com")
    private String account;

    @NotBlank(message = "密码不能为空")
    @Schema(description = "用户密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "password123")
    private String password;

    @NotNull(message = "验证码滑动距离不能为空")
    @Schema(description = "滑动验证码的X坐标距离", requiredMode = Schema.RequiredMode.REQUIRED, example = "120")
    private Integer x;

    @NotBlank(message = "验证码UUID不能为空")
    @Schema(description = "验证码的唯一标识", requiredMode = Schema.RequiredMode.REQUIRED, example = "550e8400-e29b-41d4-a716-446655440000")
    private String uuid;
}