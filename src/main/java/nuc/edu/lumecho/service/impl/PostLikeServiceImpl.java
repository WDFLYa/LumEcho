package nuc.edu.lumecho.service.impl;

import nuc.edu.lumecho.common.WdfUserContext;
import nuc.edu.lumecho.mapper.PostLikeMapper;
import nuc.edu.lumecho.mapper.PostMapper;
import nuc.edu.lumecho.service.PostLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostLikeServiceImpl implements PostLikeService {

    @Autowired
    private PostLikeMapper postLikeMapper;

    @Autowired
    private PostMapper postMapper;
    @Override
    public void toggleLike(Long postId) {
        boolean exists = postLikeMapper.existsByUserAndPost(WdfUserContext.getCurrentUserId(), postId);
        if (exists) {
            postLikeMapper.deleteByUserAndPost(WdfUserContext.getCurrentUserId(), postId);
            postMapper.decrementLikeCount(postId);
        } else {
            postLikeMapper.insert(WdfUserContext.getCurrentUserId(), postId);
            postMapper.incrementLikeCount(postId);
        }
    }

    @Override
    public boolean isLiked(Long postId) {
        return postLikeMapper.existsByUserAndPost(WdfUserContext.getCurrentUserId(), postId);
    }

    @Override
    public Map<Long, Boolean> getLikeStatuses(List<Long> postIds) {
        if (postIds == null || postIds.isEmpty()) {
            return new HashMap<>();
        }

        // 1. 查出用户已点赞的帖子 ID 列表
        List<Long> likedPostIds = postLikeMapper.selectLikedPostIdsByUser(WdfUserContext.getCurrentUserId(), postIds);

        // 2. 转成 Set 快速判断
        Set<Long> likedSet = new HashSet<>(likedPostIds);

        // 3. 构建结果：每个 postId -> 是否在 likedSet 中
        Map<Long, Boolean> result = new HashMap<>();
        for (Long postId : postIds) {
            result.put(postId, likedSet.contains(postId));
        }
        return result;
    }
}
