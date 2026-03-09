package nuc.edu.lumecho.service.impl;

import nuc.edu.lumecho.common.Enum.ResultCodeEnum;
import nuc.edu.lumecho.common.RedisKeyConstants;
import nuc.edu.lumecho.common.WdfUserContext;
import nuc.edu.lumecho.common.exception.BusinessException;
import nuc.edu.lumecho.common.util.WdfMd5Util;
import nuc.edu.lumecho.common.util.WdfStringUtil;
import nuc.edu.lumecho.common.util.WdfTokenUtil;
import nuc.edu.lumecho.mapper.UserMapper;
import nuc.edu.lumecho.model.dto.request.user.*;
import nuc.edu.lumecho.model.dto.response.LoginResponse;
import nuc.edu.lumecho.model.entity.User;
import nuc.edu.lumecho.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

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
}
