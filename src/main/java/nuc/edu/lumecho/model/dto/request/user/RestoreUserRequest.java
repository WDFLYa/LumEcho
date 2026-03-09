package nuc.edu.lumecho.model.dto.request.user;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RestoreUserRequest {
    @NotNull(message = "用户ID不能为空")
    private Long id;
}