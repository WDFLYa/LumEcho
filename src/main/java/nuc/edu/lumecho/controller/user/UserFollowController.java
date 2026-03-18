package nuc.edu.lumecho.controller.user;


import nuc.edu.lumecho.common.Result;
import nuc.edu.lumecho.model.dto.response.user.FollowListResponse;
import nuc.edu.lumecho.service.UserFollowService;
import nuc.edu.lumecho.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/userfollow")
public class UserFollowController {

    @Autowired
    private UserFollowService userFollowService;

    @Autowired
    private UserService userService;

    @PostMapping("follow/{targetId}")
    public Result<Boolean> follow(@PathVariable Long targetId) {
        boolean result = userFollowService.follow(targetId);
        return Result.ok(result);
    }

    @PostMapping("unfollow/{targetId}")
    public Result<Boolean> unfollow(@PathVariable Long targetId) {
        boolean result = userFollowService.unfollow(targetId);
        return Result.ok(result);
    }

    @PostMapping("isFollowed/{targetId}")
    public Result<Boolean> isFollowed(@PathVariable Long targetId) {
        boolean result = userFollowService.isFollowed(targetId);
        return Result.ok(result);
    }

    @GetMapping("/follow/list/{userId}")
    public Result<List<FollowListResponse>> getFollowList(@PathVariable Long userId) {
        List<FollowListResponse> followList = userService.selectFollowList(userId);
        return Result.ok(followList);
    }

    @GetMapping("/follower/list/{userId}")
    public Result<List<FollowListResponse>> getFollowerList(@PathVariable Long userId) {
        List<FollowListResponse> followerList = userService.selectFollowerList(userId);
        return Result.ok(followerList);
    }
}
