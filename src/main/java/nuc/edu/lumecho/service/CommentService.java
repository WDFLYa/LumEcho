package nuc.edu.lumecho.service;

import nuc.edu.lumecho.model.dto.request.comment.CreateCommentRequest;
import nuc.edu.lumecho.model.entity.Comment;
import nuc.edu.lumecho.model.vo.CommentNode;

import java.util.List;

public interface CommentService {
    void createComment(CreateCommentRequest createCommentRequest);
    List<CommentNode> getCommentTree(Long postId);
}
