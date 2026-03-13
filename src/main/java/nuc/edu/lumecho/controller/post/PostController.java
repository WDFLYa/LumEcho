package nuc.edu.lumecho.controller.post;

import nuc.edu.lumecho.common.Result;
import nuc.edu.lumecho.model.dto.request.post.PostIdRequest;
import nuc.edu.lumecho.model.dto.request.post.PublishPostRequest;
import nuc.edu.lumecho.model.dto.request.post.UpdatePostRequest;
import nuc.edu.lumecho.model.dto.response.post.PostDetailResponse;
import nuc.edu.lumecho.model.dto.response.post.PostHomePageResponse;
import nuc.edu.lumecho.service.PostLikeService;
import nuc.edu.lumecho.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostLikeService postLikeService;

    @RequestMapping("/publish")
    public Result publishPost(@RequestBody @Valid PublishPostRequest publishPostRequest) {
        postService.publishPost(publishPostRequest);
        return Result.ok();
    }

    @PostMapping("/update")
    public Result<Void> updatePost(@RequestBody @Valid UpdatePostRequest updatePostRequest) {
        postService.updatePost(updatePostRequest);
        return Result.ok();
    }

    @PostMapping("/delete")
    public Result<Void> deletePost(@RequestBody @Valid PostIdRequest postIdRequest) {
        postService.deletePost(postIdRequest);
        return Result.ok();
    }

    @GetMapping("/selectById/{id}")
    public Result<PostDetailResponse> getPostById(@PathVariable Long id) {
        PostDetailResponse postDetailResponse = postService.selectPostById(id);
        return Result.ok(postDetailResponse);
    }

    @GetMapping("/select/all")
    public Result<PostHomePageResponse> getHomePosts(
            @RequestParam(defaultValue = "time") String sort,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "8") int limit) {

        if (!"time".equals(sort) && !"hot".equals(sort)) {
            sort = "time";
        }

        if (limit > 50) limit = 50;
        if (offset < 0) offset = 0;

        PostHomePageResponse postHomePageResponse;
        if ("hot".equals(sort)) {
            postHomePageResponse = postService.selectHomePostsByHot(offset, limit);
        } else {
            postHomePageResponse = postService.selectHomePosts(offset, limit);
        }

        return Result.ok(postHomePageResponse);
    }

    @PostMapping("/{postId}/like")
    public Result<Void> toggleLike(@PathVariable Long postId) {
        postLikeService.toggleLike(postId);
        return Result.ok();
    }

    @GetMapping("/{postId}/like-status")
    public Result<Boolean> getLikeStatus(@PathVariable Long postId) {
        boolean liked = postLikeService.isLiked(postId);
        return Result.ok(liked);
    }

    @PostMapping("/likes/statuses")
    public Result<Map<Long, Boolean>> getLikeStatuses(@RequestBody List<Long> postIds) {
        Map<Long, Boolean> statuses = postLikeService.getLikeStatuses(postIds);
        return Result.ok(statuses);
    }
}
