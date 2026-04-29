package nuc.edu.lumecho.service.impl;

import nuc.edu.lumecho.common.Enum.ResultCodeEnum;
import nuc.edu.lumecho.common.Enum.UserRoleEnum;
import nuc.edu.lumecho.common.Enum.UserStatusEnum;
import nuc.edu.lumecho.common.RedisKeyConstants;
import nuc.edu.lumecho.common.exception.BusinessException;
import nuc.edu.lumecho.common.util.WdfMd5Util;
import nuc.edu.lumecho.mapper.UserMapper;
import nuc.edu.lumecho.model.dto.request.user.UserAccountRegisterRequest;
import nuc.edu.lumecho.model.dto.request.user.UserEmailRegisterRequest;
import nuc.edu.lumecho.model.dto.request.user.UserPhoneRegisterRequest;
import nuc.edu.lumecho.model.entity.User;
import nuc.edu.lumecho.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public void registerByAccount(UserAccountRegisterRequest request) {

        if(userMapper.existsByAccount(request.getAccount())){
            throw new BusinessException(ResultCodeEnum.ADMIN_ACCOUNT_EXIST_ERROR);
        }
        User user = new User();
        user.setAccount(request.getAccount());
        user.setPassword(WdfMd5Util.md5Encrypt(request.getPassword()));
        user.setStatus(UserStatusEnum.ENABLED.getCode());
        user.setRole(UserRoleEnum.USER.getCode());
        user.setCreateTime(LocalDateTime.now());

        userMapper.insert(user);
    }

    @Override
    public void registerByPhone(UserPhoneRegisterRequest request) {
        if(userMapper.existsByPhone(request.getPhone())){
            throw new BusinessException(ResultCodeEnum.ADMIN_PHONE_EXIST_ERROR);
        }
        String phone = request.getPhone();
        String inputCode = request.getCode();
        String key = RedisKeyConstants.PHONE_REGISTER_CODE_KEY + phone;
        String code = stringRedisTemplate.opsForValue().get(key);
        if (!inputCode.equals(code)){
            throw new BusinessException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_ERROR);
        }
        User user = new User();
        user.setPhone(phone);
        user.setStatus(UserStatusEnum.ENABLED.getCode());
        user.setRole(UserRoleEnum.USER.getCode());
        user.setCreateTime(LocalDateTime.now());
        userMapper.insert(user);
        System.out.printf(user.toString());
    }

    @Override
    public void registerByEmail(UserEmailRegisterRequest userEmailRegisterRequest) {

        if (userMapper.existsByEmail(userEmailRegisterRequest.getEmail())){
            throw new BusinessException(ResultCodeEnum.ADMIN_EMAIL_EXIST_ERROR);
        }

        String email = userEmailRegisterRequest.getEmail();
        String inputCode = userEmailRegisterRequest.getCode();
        String key = RedisKeyConstants.EMAIL_REGISTER_CODE_KEY + email;
        String code = stringRedisTemplate.opsForValue().get(key);
        if (!inputCode.equals(code)){
            throw new BusinessException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_ERROR);
        }
        User user = new User();
        user.setEmail(email);
        user.setStatus(UserStatusEnum.ENABLED.getCode());
        user.setRole(UserRoleEnum.USER.getCode());
        user.setCreateTime(LocalDateTime.now());
        userMapper.insertByEmail(user);
        System.out.printf(user.toString());
    }

    @Override
    public void logout(String token) {
        String key = RedisKeyConstants.USER_TOKEN_KEY + token;
        Boolean deleted = stringRedisTemplate.delete(key);

        if (Boolean.TRUE.equals(deleted)) {
            System.out.println("退出登录成功，Token已删除: " + token);
        } else {
            System.out.println("Token不存在或已过期: " + token);
        }
    }
}
