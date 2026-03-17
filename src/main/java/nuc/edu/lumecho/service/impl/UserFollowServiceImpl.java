package nuc.edu.lumecho.service.impl;

import nuc.edu.lumecho.common.Enum.ResultCodeEnum;
import nuc.edu.lumecho.common.exception.BusinessException;
import nuc.edu.lumecho.mapper.UserFollowMapper;
import nuc.edu.lumecho.model.dto.request.follow.FollowStatsResquest;
import nuc.edu.lumecho.service.UserFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Dfly
 * @Date 2026/3/17 22:00
 */
@Service
public class UserFollowServiceImpl implements UserFollowService {
    @Autowired
    private UserFollowMapper userFollowMapper;
    @Override
    public boolean follow(Long followerId, Long targetId) {
        if (followerId == null || targetId == null) {
            throw new BusinessException(ResultCodeEnum.FOLLOW_TARGET_NOT_FOUND);
        }
        if (followerId.equals(targetId)) {
            throw new BusinessException(ResultCodeEnum.FOLLOW_SELF_FORBIDDEN);
        }

        int insertResult = userFollowMapper.insertFollow(followerId, targetId);
        
        return false;
    }

    @Override
    public boolean unfollow(Long followerId, Long targetId) {
        return false;
    }

    @Override
    public boolean isFollowed(Long followerId, Long targetId) {
        return false;
    }

    @Override
    public FollowStatsResquest getStats(Long userId) {
        return null;
    }
}