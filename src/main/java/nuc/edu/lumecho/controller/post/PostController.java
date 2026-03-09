package nuc.edu.lumecho.controller.post;

import nuc.edu.lumecho.common.Result;
import nuc.edu.lumecho.model.dto.request.post.PostIdRequest;
import nuc.edu.lumecho.model.dto.request.post.PublishPostRequest;
import nuc.edu.lumecho.model.dto.request.post.UpdatePostRequest;
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

}
