package nuc.edu.lumecho.service.impl;

import lombok.val;
import nuc.edu.lumecho.common.Enum.ResultCodeEnum;
import nuc.edu.lumecho.common.RedisKeyConstants;
import nuc.edu.lumecho.common.WdfUserContext;
import nuc.edu.lumecho.common.exception.BusinessException;
import nuc.edu.lumecho.common.util.WdfMd5Util;
import nuc.edu.lumecho.common.util.WdfStringUtil;
import nuc.edu.lumecho.common.util.WdfTokenUtil;
import nuc.edu.lumecho.mapper.UserMapper;
import nuc.edu.lumecho.model.dto.request.UserAccountLoginRequest;
import nuc.edu.lumecho.model.dto.request.UserPhoneLoginRequest;
import nuc.edu.lumecho.model.dto.response.LoginResponse;
import nuc.edu.lumecho.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

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

        Integer userId = userMapper.selectUserIdByAccount(account);
        String token = WdfTokenUtil.generateLoginToken();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setUserId(userId);

        String key = RedisKeyConstants.USER_TOKEN_KEY + userId;
        stringRedisTemplate.opsForValue().set(key,token,30, TimeUnit.MINUTES);
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
        Integer userId = userMapper.selectUserIdByPhone(phone);
        String key = RedisKeyConstants.USER_TOKEN_KEY + userId;
        String token = WdfTokenUtil.generateLoginToken();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setUserId(userId);
        stringRedisTemplate.opsForValue().set(key,token,30, TimeUnit.MINUTES);
        WdfUserContext.setCurrentUserId(userId);
        System.out.printf("登录成功");
        System.out.printf(WdfUserContext.getCurrentUserId().toString());
        return loginResponse;
    }
}
