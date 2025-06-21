package xyz.tjucomments.tjufood.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "管理员更新个人信息的数据传输对象")
public class AdminProfileUpdateDTO {

    @NotBlank(message = "用户名不能为空")
    @Schema(description = "管理员登录账号", requiredMode = Schema.RequiredMode.REQUIRED, example = "admin_user")
    private String username;

    @NotBlank(message = "姓名不能为空")
    @Schema(description = "管理员真实姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    private String name;
}
