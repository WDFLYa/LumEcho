package nuc.edu.lumecho.model.dto.request.post;

import lombok.Data;
import nuc.edu.lumecho.common.Enum.PostStatusEnum;


import javax.validation.constraints.NotNull;

@Data
public class UpdatePostStatusRequest {

    @NotNull(message = "帖子ID不能为空")
    private Long id;

    @NotNull(message = "帖子状态不能为空")
    private PostStatusEnum status;

}
