package nuc.edu.lumecho.model.dto.request.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UserEmailLoginRequest {


    @NotBlank(message = "邮箱不能为空")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "邮箱格式不正确"
    )
    private String email;

    @NotBlank(message = "验证码不能为空")
    @Pattern(
            regexp = "^\\d{6}$",
            message = "验证码必须是6位数字"
    )
    private String code;

}
