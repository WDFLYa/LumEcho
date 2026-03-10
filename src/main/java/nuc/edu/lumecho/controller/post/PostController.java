package nuc.edu.lumecho.controller.post;

import nuc.edu.lumecho.common.Result;
import nuc.edu.lumecho.model.dto.request.post.PostIdRequest;
import nuc.edu.lumecho.model.dto.request.post.PublishPostRequest;
import nuc.edu.lumecho.model.dto.request.post.UpdatePostRequest;
import nuc.edu.lumecho.model.dto.response.post.PostDetailResponse;
import nuc.edu.lumecho.model.dto.response.post.PostHomePageResponse;
import nuc.edu.lumecho.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostService postService;

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
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit) {
        if (limit > 50) limit = 50;
        if (offset < 0) offset = 0;

        PostHomePageResponse postHomePageResponse = postService.selectHomePosts(offset, limit);
        return Result.ok(postHomePageResponse);
    }

}
