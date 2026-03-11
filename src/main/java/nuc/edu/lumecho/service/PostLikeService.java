package nuc.edu.lumecho.service;

import java.util.List;
import java.util.Map;

public interface PostLikeService {
    void toggleLike(Long postId);
    boolean isLiked(Long postId);
    Map<Long, Boolean> getLikeStatuses(List<Long> postIds);
}
