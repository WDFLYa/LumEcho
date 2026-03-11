package nuc.edu.lumecho.controller.comment;


import nuc.edu.lumecho.common.Result;
import nuc.edu.lumecho.model.dto.request.comment.CreateCommentRequest;
import nuc.edu.lumecho.model.vo.CommentNode;
import nuc.edu.lumecho.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/createcomment")
    public Result insertComment(@RequestBody @Valid CreateCommentRequest createCommentRequest) {
        commentService.createComment(createCommentRequest);
        return Result.ok();
    }

    @GetMapping("/{postId}/comments")
    public Result<Map<String, Object>> getComments(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        Map<String, Object> data = commentService.getCommentTreeWithPagination(postId, page, size);
        return Result.ok(data);
    }

    @GetMapping("/post/{postId}")
    public Result<List<CommentNode>> getCommentTree(@PathVariable Long postId) {
        List<CommentNode> commentTree = commentService.getCommentTree(postId);
        return Result.ok(commentTree);
    }
}
