package nuc.edu.lumecho.service.impl;

import nuc.edu.lumecho.common.Enum.ResultCodeEnum;
import nuc.edu.lumecho.common.WdfUserContext;
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
    public boolean follow(Long targetId) {
        Long followerId = WdfUserContext.getCurrentUserId();

        if (followerId.equals(targetId)) {
            throw new BusinessException(ResultCodeEnum.FOLLOW_SELF_FORBIDDEN);
        }

        // 1. 先检查是否已关注
        if (userFollowMapper.checkFollowStatus(followerId, targetId) > 0) {
            throw new BusinessException(ResultCodeEnum.FOLLOW_ALREADY_EXISTS);
        }

        // 2. 插入关系
        if (userFollowMapper.insertFollow(followerId, targetId) > 0) {
            // 3. 更新计数 (粉丝数+1, 关注数+1)
            userFollowMapper.updateFansCount(targetId, 1);
            userFollowMapper.updateFollowCount(followerId, 1);
            return true;
        }
        return false;
    }

    @Override
    public boolean unfollow(Long targetId) {
        Long followerId = WdfUserContext.getCurrentUserId();


        // 1. 检查是否存在关注关系
        if (userFollowMapper.checkFollowStatus(followerId, targetId) == 0) {
            throw new BusinessException(ResultCodeEnum.FOLLOW_NOT_EXISTS);
        }

        // 2. 删除关系
        if (userFollowMapper.deleteFollow(followerId, targetId) > 0) {
            // 3. 更新计数 (粉丝数-1, 关注数-1)
            userFollowMapper.updateFansCount(targetId, -1);
            userFollowMapper.updateFollowCount(followerId, -1);
            return true;
        }
        return false;
    }

    @Override
    public boolean isFollowed(Long targetId) {
        Long followerId = WdfUserContext.getCurrentUserId();
        return userFollowMapper.checkFollowStatus(followerId, targetId) > 0;
    }

}