package nuc.edu.lumecho.model.dto.request.comment;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateCommentRequest {
    @NotNull(message = "帖子ID不能为空")
    private Long postId;
    @NotNull(message = "内容不能为空")
    private String content;
    private Long parentId;
}
