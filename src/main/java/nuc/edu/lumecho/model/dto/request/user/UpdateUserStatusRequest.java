package nuc.edu.lumecho.model.dto.request.user;

import lombok.Data;
import nuc.edu.lumecho.common.Enum.UserStatusEnum;

import javax.validation.constraints.NotNull;

@Data
public class UpdateUserStatusRequest {
    @NotNull(message = "用户ID不能为空")
    private Long id;

    @NotNull(message = "用户状态不能为空")
    private UserStatusEnum status;
}
