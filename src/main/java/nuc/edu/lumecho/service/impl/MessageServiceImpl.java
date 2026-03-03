package nuc.edu.lumecho.service.impl;

import nuc.edu.lumecho.common.Enum.ResultCodeEnum;
import nuc.edu.lumecho.common.RedisKeyConstants;
import nuc.edu.lumecho.common.exception.BusinessException;
import nuc.edu.lumecho.common.util.WdfRandomCodeUtil;
import nuc.edu.lumecho.mapper.UserMapper;
import nuc.edu.lumecho.model.dto.request.SendCodeRequest;
import nuc.edu.lumecho.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public void sendRegisterMessage(SendCodeRequest sendCodeRequest) {
        String phone = sendCodeRequest.getPhone();
        if(userMapper.existsByPhone(phone)){
            throw new BusinessException(ResultCodeEnum.ADMIN_PHONE_EXIST_ERROR);
        }
        String key = RedisKeyConstants.PHONE_REGISTER_CODE_KEY + phone;
        if(stringRedisTemplate.hasKey(key)){
            throw new BusinessException(ResultCodeEnum.APP_SENED_CODE);
        }
        String code = WdfRandomCodeUtil.generateSixDigitCode();
        stringRedisTemplate.opsForValue().set(key,code,5,TimeUnit.MINUTES);
        System.out.printf(code);
    }

    @Override
    public void sendLoginMessage(SendCodeRequest sendCodeRequest) {
        String phone = sendCodeRequest.getPhone();
        if(!userMapper.existsByPhone(phone)){
            throw new BusinessException(ResultCodeEnum.ADMIN_PHONE_NOT_EXIST_ERROR);
        }
        String key = RedisKeyConstants.PHONE_LOGIN_CODE_KEY + phone;
        if(stringRedisTemplate.hasKey(key)){
            throw new BusinessException(ResultCodeEnum.APP_SENED_CODE);
        }
        String code = WdfRandomCodeUtil.generateSixDigitCode();
        stringRedisTemplate.opsForValue().set(key,code,5,TimeUnit.MINUTES);
        System.out.printf(code);
    }
}
