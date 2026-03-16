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
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "time") String sort,
            @RequestParam(required = false) Long categoryId, // 新增：允许传分类ID
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "8") int limit) {

        // 参数校验
        if (keyword != null) keyword = keyword.trim();
        if (!"time".equals(sort) && !"hot".equals(sort)) sort = "time";
        if (limit > 50) limit = 50;
        if (offset < 0) offset = 0;

        PostHomePageResponse response;

        if (keyword != null && !keyword.isEmpty()) {
            // 1. 走搜索逻辑 (支持分类 + 排序)
            if ("hot".equals(sort)) {
                response = postService.searchPostsByHot(keyword, categoryId, offset, limit);
            } else {
                response = postService.searchPostsByTime(keyword, categoryId, offset, limit);
            }
        }
        else if (categoryId != null) {
            // 2. 无搜索词，有分类 (支持排序)
            if ("hot".equals(sort)) {
                response = postService.selectHomePostsByCategoryAndHot(categoryId, offset, limit);
            } else {
                response = postService.selectHomePostsByCategory(categoryId, offset, limit);
            }
        }
        else {
            // 3. 无搜索词，无分类 (全局)
            if ("hot".equals(sort)) {
                response = postService.selectHomePostsByHot(offset, limit);
            } else {
                response = postService.selectHomePosts(offset, limit);
            }
        }

        return Result.ok(response);
    }

    @GetMapping("/select/user")
    public Result<PostHomePageResponse> getUserPosts(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "8") int limit) {

        // 1. 参数安全校验 (模仿首页逻辑)
        if (limit > 50) {
            limit = 50;
        }
        if (offset < 0) {
            offset = 0;
        }


        // 2. 调用 Service (按时间排序的逻辑在 Service/Mapper 内部已固定为 create_time DESC)
        PostHomePageResponse response = postService.selectPostsByUserId(userId, offset, limit);

        // 3. 返回统一结果
        return Result.ok(response);
    }

    @GetMapping("/like/list")
    public Result<PostHomePageResponse> getUserLikedPosts(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "8") int limit) {

        PostHomePageResponse response = postService.selectPostsByUserLike(userId, offset, limit);
        return Result.ok(response);
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
