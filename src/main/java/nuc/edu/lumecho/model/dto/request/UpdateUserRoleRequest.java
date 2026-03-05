package nuc.edu.lumecho.model.dto.request;

import lombok.Data;
import nuc.edu.lumecho.common.Enum.UserRoleEnum;
import nuc.edu.lumecho.common.Enum.UserStatusEnum;

import javax.validation.constraints.NotNull;

@Data
public class UpdateUserRoleRequest {
    @NotNull(message = "用户ID不能为空")
    private Long id;

    @NotNull(message = "用户角色不能为空")
    private UserRoleEnum role;
}

