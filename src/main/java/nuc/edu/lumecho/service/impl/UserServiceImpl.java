package nuc.edu.lumecho.service.impl;

import nuc.edu.lumecho.common.Enum.ResourceTypeEnum;
import nuc.edu.lumecho.common.Enum.ResultCodeEnum;
import nuc.edu.lumecho.common.RedisKeyConstants;
import nuc.edu.lumecho.common.WdfUserContext;
import nuc.edu.lumecho.common.exception.BusinessException;
import nuc.edu.lumecho.common.util.WdfMd5Util;
import nuc.edu.lumecho.common.util.WdfStringUtil;
import nuc.edu.lumecho.common.util.WdfTokenUtil;
import nuc.edu.lumecho.mapper.ResourceFileMapper;
import nuc.edu.lumecho.mapper.UserFollowMapper;
import nuc.edu.lumecho.mapper.UserMapper;
import nuc.edu.lumecho.model.dto.request.user.*;
import nuc.edu.lumecho.model.dto.response.LoginResponse;
import nuc.edu.lumecho.model.dto.response.user.FollowListResponse;
import nuc.edu.lumecho.model.dto.response.user.UserBaseInfoResponse;
import nuc.edu.lumecho.model.dto.response.user.UserDetailInfoResponse;
import nuc.edu.lumecho.model.entity.User;
import nuc.edu.lumecho.model.entity.UserFollow;
import nuc.edu.lumecho.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ResourceFileMapper resourceFileMapper;

    @Autowired
    private UserFollowMapper userFollowMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public LoginResponse loginByAccount(UserAccountLoginRequest userAccountLoginRequest) {
        String account = userAccountLoginRequest.getAccount();
        String inputPassword = userAccountLoginRequest.getPassword();
        if(!userMapper.existsByAccount(account)){
            throw new BusinessException(ResultCodeEnum.ADMIN_ACCOUNT_NOT_EXIST_ERROR);
        }
        String inputPasswordMd5 = WdfMd5Util.md5Encrypt(inputPassword);
        String password = userMapper.selectPasswordByAccount(account);
        if (!inputPasswordMd5.equals(password)){
            throw new BusinessException(ResultCodeEnum.ADMIN_PASSWORD_ERROR);
        }

        Long userId = userMapper.selectUserIdByAccount(account);
        String token = WdfTokenUtil.generateLoginToken();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setUserId(userId);
        loginResponse.setRole(userMapper.getUserRoleById(userId));

        String key = RedisKeyConstants.USER_TOKEN_KEY + token;
        stringRedisTemplate.opsForValue().set(key, String.valueOf(userId),30, TimeUnit.MINUTES);
        WdfUserContext.setCurrentUserId(userId);
        System.out.printf("登录成功");
        System.out.printf(WdfUserContext.getCurrentUserId().toString());
        return loginResponse;
    }

    @Override
    public LoginResponse loginByPhone(UserPhoneLoginRequest userPhoneLoginRequest) {
        String phone = userPhoneLoginRequest.getPhone();
        if(!userMapper.existsByPhone(phone)){
            throw new BusinessException(ResultCodeEnum.ADMIN_PHONE_NOT_EXIST_ERROR);
        }
        String inputCode = userPhoneLoginRequest.getCode();
        String code = stringRedisTemplate.opsForValue().get(RedisKeyConstants.PHONE_LOGIN_CODE_KEY + phone);
        if(WdfStringUtil.isBlank(code)){
            throw new BusinessException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_NOT_FOUND);
        }
        if(!inputCode.equals(code)){
            throw new BusinessException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_ERROR);
        }
        Long userId = userMapper.selectUserIdByPhone(phone);
        String token = WdfTokenUtil.generateLoginToken();
        String key = RedisKeyConstants.USER_TOKEN_KEY + token;
        stringRedisTemplate.opsForValue().set(key, String.valueOf(userId),30, TimeUnit.MINUTES);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setUserId(userId);
        loginResponse.setRole(userMapper.getUserRoleById(userId));

        WdfUserContext.setCurrentUserId(userId);
        System.out.printf("登录成功");
        System.out.printf(WdfUserContext.getCurrentUserId().toString());
        return loginResponse;
    }

    @Override
    public void updateUserInfo(UserUpdateRequest userUpdateRequest) {
        User user = new User();
        user.setId(WdfUserContext.getCurrentUserId());
        user.setUsername(userUpdateRequest.getUsername());
        user.setBio(userUpdateRequest.getBio());
        user.setEmail(userUpdateRequest.getEmail());
        System.out.println(user.toString());
        userMapper.updateUser(user);
    }

    @Override
    public void softDeleteCurrentUser() {
        User user = new User();
        user.setId(WdfUserContext.getCurrentUserId());
        user.setDeletedAt(LocalDateTime.now());
        userMapper.updateUser(user);
    }

    @Override
    public void completeAccount(CompleteAccountRequest completeAccountRequest) {
        User user = new User();
        String account = completeAccountRequest.getAccount();
        if(userMapper.existsByAccount(account)){
            throw new BusinessException(ResultCodeEnum.ADMIN_ACCOUNT_EXIST_ERROR);
        }
        user.setId(WdfUserContext.getCurrentUserId());
        user.setAccount(account);
        user.setPassword(WdfMd5Util.md5Encrypt(completeAccountRequest.getPassword()));
        userMapper.updateUser(user);
    }

    @Override
    public void completePhone(CompletePhoneRequest completePhoneRequest) {
        String phone = completePhoneRequest.getPhone();
        if(userMapper.existsByPhone(phone)){
            throw new BusinessException(ResultCodeEnum.ADMIN_PHONE_EXIST_ERROR);
        }
        String inputCode = completePhoneRequest.getCode();
        String code = stringRedisTemplate.opsForValue().get(RedisKeyConstants.PHONE_COMPLETE_CODE_KEY + phone);
        if(WdfStringUtil.isBlank(code)){
            throw new BusinessException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_NOT_FOUND);
        }
        if(!inputCode.equals(code)){
            throw new BusinessException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_ERROR);
        }
        User user = new User();
        user.setId(WdfUserContext.getCurrentUserId());
        user.setPhone(phone);
        userMapper.updateUser(user);
    }

    @Override
    public UserBaseInfoResponse selectUserBaseInfoById() {
        return userMapper.selectUserBaseInfoById(WdfUserContext.getCurrentUserId());
    }

    @Override
    public UserBaseInfoResponse selectUserBaseInfo(Long id) {
        return userMapper.selectUserBaseInfoById(id);
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.getUserById(id);
    }

    @Override
    public UserDetailInfoResponse GetUserDetailInfo() {
        User user = userMapper.getUserById(WdfUserContext.getCurrentUserId());
        UserDetailInfoResponse userDetailInfoResponse = new UserDetailInfoResponse();
        userDetailInfoResponse.setUsername(user.getUsername());
        userDetailInfoResponse.setBio(user.getBio());
        userDetailInfoResponse.setEmail(user.getEmail());
        userDetailInfoResponse.setAccount(user.getAccount());
        userDetailInfoResponse.setPhone(user.getPhone());
        userDetailInfoResponse.setStatus(user.getStatus());
        userDetailInfoResponse.setRole(user.getRole());
        userDetailInfoResponse.setCreateTime(user.getCreateTime());
        userDetailInfoResponse.setUpdateTime(user.getUpdateTime());
        return userDetailInfoResponse;
    }

    @Override
    public int updateUserAvatar(String avatarUrl) {
       return resourceFileMapper.updateUserAvatar(avatarUrl, ResourceTypeEnum.AVATAR.getCode(),WdfUserContext.getCurrentUserId());
    }

    @Override
    public List<FollowListResponse> selectFollowList(Long userId) {
        List<UserFollow> userFollows = userFollowMapper.selectFollowList(userId);
        List<Long> followingIds = userFollows.stream()
                .map(UserFollow::getFollowingId).toList();

        if (followingIds.isEmpty()) {
            return List.of();
        }

        List<UserBaseInfoResponse> userBaseInfos = userMapper.selectUserBaseInfoByIds(followingIds);

        Long currentUserId = WdfUserContext.getCurrentUserId();

        return userBaseInfos.stream()
                .map(userBaseInfo -> {
                    FollowListResponse response = new FollowListResponse();
                    response.setUserId(userBaseInfo.getId());
                    response.setUserName(userBaseInfo.getUsername());
                    response.setBio(userBaseInfo.getBio());
                    response.setAvatar(userBaseInfo.getAvatar());

                    boolean isFollowing = userFollowMapper.checkFollowStatus(currentUserId, userBaseInfo.getId()) > 0;
                    response.setStatus(isFollowing);

                    return response;
                })
                .collect(Collectors.toList());
    }



    @Override
    public List<FollowListResponse> selectFollowerList(Long userId) {
        List<UserFollow> userFollows = userFollowMapper.selectFansList(userId);
        List<Long> followerIds = userFollows.stream()
                .map(UserFollow::getFollowerId).toList();

        if (followerIds.isEmpty()) {
            return List.of();
        }

        List<UserBaseInfoResponse> userBaseInfos = userMapper.selectUserBaseInfoByIds(followerIds);

        Long currentUserId = WdfUserContext.getCurrentUserId();

        return userBaseInfos.stream()
                .map(userBaseInfo -> {
                    FollowListResponse response = new FollowListResponse();
                    response.setUserId(userBaseInfo.getId());
                    response.setUserName(userBaseInfo.getUsername());
                    response.setBio(userBaseInfo.getBio());
                    response.setAvatar(userBaseInfo.getAvatar());

                    boolean isFollowing = userFollowMapper.checkFollowStatus(currentUserId, userBaseInfo.getId()) > 0;
                    response.setStatus(isFollowing);

                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDetailInfoResponse> getAllUsersForAdmin() {
        // 1. 从 Mapper 获取所有 User 实体
        List<User> users = userMapper.selectAllUsersForAdmin();

        // 2. 转换为 DTO (UserDetailInfoResponse)
        return users.stream().map(user -> {
            UserDetailInfoResponse resp = new UserDetailInfoResponse();
            resp.setAccount(user.getAccount());
            resp.setUsername(user.getUsername());
            resp.setEmail(user.getEmail());
            resp.setPhone(user.getPhone());
            resp.setStatus(user.getStatus());
            resp.setRole(user.getRole());
            resp.setCreateTime(user.getCreateTime());
            resp.setUpdateTime(user.getUpdateTime());
            // 如果有 bio 或 avatar，也记得 set
            // resp.setBio(user.getBio());
            // resp.setAvatar(user.getAvatar());
            return resp;
        }).collect(Collectors.toList());
    }

    @Override
    public void updateUserStatusByAccount(String account, Integer status) {
        // 调用 Mapper 更新
        int rows = userMapper.updateStatusByAccount(account, status);
        if (rows == 0) {
            throw new RuntimeException("用户不存在或更新失败");
        }
    }


}
