package xyz.tjucomments.tjufood.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "管理员修改密码的数据传输对象")
public class AdminPasswordChangeDTO {

    @NotBlank(message = "当前密码不能为空")
    @Schema(description = "当前密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "当前密码")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    @Size(min = 6, max = 20, message = "新密码长度必须在6-20位之间")
    @Schema(description = "新密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "新密码")
    private String newPassword;
}
