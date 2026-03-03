package nuc.edu.lumecho.model.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserUpdateRequest {

    @Size(max = 30, message = "昵称长度不能超过30个字符")
    private String username;

    @Size(max = 200, message = "简介长度不能超过200个字符")
    private String bio;

    @Size(max = 100, message = "邮箱长度不能超过100个字符")
    private String email;

}
