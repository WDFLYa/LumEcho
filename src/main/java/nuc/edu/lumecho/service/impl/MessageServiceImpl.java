package nuc.edu.lumecho.service.impl;

import nuc.edu.lumecho.common.Enum.ResultCodeEnum;
import nuc.edu.lumecho.common.RedisKeyConstants;
import nuc.edu.lumecho.common.exception.BusinessException;
import nuc.edu.lumecho.common.util.WdfRandomCodeUtil;
import nuc.edu.lumecho.mapper.UserMapper;
import nuc.edu.lumecho.model.dto.request.SendCodeRequest;
import nuc.edu.lumecho.model.dto.request.SendEmailCodeRequest;
import nuc.edu.lumecho.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.TimeUnit;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private JavaMailSender javaMailSender;
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
    public void sendEmailRegisterMessage(SendEmailCodeRequest sendEmailCodeRequest) {
        String email = sendEmailCodeRequest.getEmail();
        if(userMapper.existsByEmail(email)){
            throw new BusinessException(ResultCodeEnum.ADMIN_EMAIL_EXIST_ERROR);
        }
        String key = RedisKeyConstants.EMAIL_REGISTER_CODE_KEY + email;
        if(stringRedisTemplate.hasKey(key)){
            throw new BusinessException(ResultCodeEnum.APP_SENED_CODE);
        }
        String code = WdfRandomCodeUtil.generateSixDigitCode();
        stringRedisTemplate.opsForValue().set(key,code,5,TimeUnit.MINUTES);
        System.out.printf(code);

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // 你的邮箱
            helper.setFrom("2737278699@qq.com");
            // 接收人
            helper.setTo(email);
            // 标题
            helper.setSubject("Welcome to LumEcho!!");
            // 内容
            String content = "<h2>您的注册验证码：" + code + "</h2><p>5分钟内有效</p>";
            helper.setText(content, true);

            // 发送
            javaMailSender.send(message);

            System.out.println("邮件发送成功！验证码：" + code);

        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResultCodeEnum.FAIL);
        }
    }

    @Override
    public void sendEmailLoginMessage(SendEmailCodeRequest sendEmailCodeRequest) {
        String email = sendEmailCodeRequest.getEmail();
        if(!userMapper.existsByEmail(email)){
            throw new BusinessException(ResultCodeEnum.ADMIN_EMAIL_NOT_EXIST_ERROR);
        }
        String key = RedisKeyConstants.EMAIL_LOGIN_CODE_KEY + email;
        if(stringRedisTemplate.hasKey(key)){
            throw new BusinessException(ResultCodeEnum.APP_SENED_CODE);
        }
        String code = WdfRandomCodeUtil.generateSixDigitCode();
        stringRedisTemplate.opsForValue().set(key,code,5,TimeUnit.MINUTES);
        System.out.printf(code);

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // 你的邮箱
            helper.setFrom("2737278699@qq.com");
            // 接收人
            helper.setTo(email);
            // 标题
            helper.setSubject("Welcome to LumEcho!!");
            // 内容
            String content = "<h2>您的登录验证码：" + code + "</h2><p>5分钟内有效</p>";
            helper.setText(content, true);

            // 发送
            javaMailSender.send(message);

            System.out.println("邮件发送成功！验证码：" + code);

        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResultCodeEnum.FAIL);
        }
    }

    @Override
    public void sendCompleteEmailMessage(SendEmailCodeRequest sendEmailCodeRequest) {
        String email = sendEmailCodeRequest.getEmail();
        if(userMapper.existsByEmail(email)){
            throw new BusinessException(ResultCodeEnum.ADMIN_EMAIL_EXIST_ERROR);
        }
        String key = RedisKeyConstants.EMAIL_COMPLETE_CODE_KEY + email;
        if(stringRedisTemplate.hasKey(key)){
            throw new BusinessException(ResultCodeEnum.APP_SENED_CODE);
        }
        String code = WdfRandomCodeUtil.generateSixDigitCode();
        stringRedisTemplate.opsForValue().set(key,code,5,TimeUnit.MINUTES);

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // 你的邮箱
            helper.setFrom("2737278699@qq.com");
            // 接收人
            helper.setTo(email);
            // 标题
            helper.setSubject("Welcome to LumEcho!!");
            // 内容
            String content = "<h2>您的验证码：" + code + "</h2><p>5分钟内有效</p>";
            helper.setText(content, true);

            // 发送
            javaMailSender.send(message);

            System.out.println("邮件发送成功！验证码：" + code);

        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResultCodeEnum.FAIL);
        }

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

    @Override
    public void sendCompletePhoneMessage(SendCodeRequest sendCodeRequest) {
        String phone = sendCodeRequest.getPhone();
        if(userMapper.existsByPhone(phone)){
            throw new BusinessException(ResultCodeEnum.ADMIN_PHONE_EXIST_ERROR);
        }
        String key = RedisKeyConstants.PHONE_COMPLETE_CODE_KEY + phone;
        if(stringRedisTemplate.hasKey(key)){
            throw new BusinessException(ResultCodeEnum.APP_SENED_CODE);
        }
        String code = WdfRandomCodeUtil.generateSixDigitCode();
        stringRedisTemplate.opsForValue().set(key,code,5,TimeUnit.MINUTES);
        System.out.printf(code);
    }
}
