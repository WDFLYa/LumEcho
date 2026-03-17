package nuc.edu.lumecho.service;

import nuc.edu.lumecho.model.dto.request.follow.FollowStatsResquest;

public interface UserFollowService {
    boolean follow(Long followerId, Long targetId);
    boolean unfollow(Long followerId, Long targetId);
    boolean isFollowed(Long followerId, Long targetId);
    FollowStatsResquest getStats(Long userId);
}
