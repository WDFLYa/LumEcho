package nuc.edu.lumecho.model.dto.request.post;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PostIdRequest {
    @NotNull(message = "帖子ID不能为空")
    private Long id;
}
