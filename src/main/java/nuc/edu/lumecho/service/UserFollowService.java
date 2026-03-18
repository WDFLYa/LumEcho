package nuc.edu.lumecho.service;


public interface UserFollowService {
    boolean follow(Long targetId);
    boolean unfollow(Long targetId);
    boolean isFollowed(Long targetId);
}
