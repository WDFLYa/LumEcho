package nuc.edu.lumecho.controller.message;

import nuc.edu.lumecho.common.Result;
import nuc.edu.lumecho.model.dto.request.SendCodeRequest;
import nuc.edu.lumecho.model.dto.request.SendEmailCodeRequest;
import nuc.edu.lumecho.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/register/sendcode")
    public Result sendCodeByPhone(@RequestBody @Valid SendCodeRequest sendCodeRequest) {
        messageService.sendRegisterMessage(sendCodeRequest);
        return Result.ok("注册验证码已发送");
    }

    @PostMapping("/register/sendemailcode")
    public Result sendCodeByEmail(@RequestBody @Valid SendEmailCodeRequest sendEmailCodeRequest) {
        messageService.sendEmailRegisterMessage(sendEmailCodeRequest);
        return Result.ok("注册验证码已发送");
    }

    @PostMapping("/login/sendemailcode")
    public Result sendLoginCodeByEmail(@RequestBody @Valid SendEmailCodeRequest sendEmailCodeRequest) {
        messageService.sendEmailLoginMessage(sendEmailCodeRequest);
        return Result.ok("登录验证码已发送");
    }
    @PostMapping("/login/sendcode")
    public Result sendCodeLogin(@RequestBody @Valid SendCodeRequest sendCodeRequest) {
        messageService.sendLoginMessage(sendCodeRequest);
        return Result.ok("登录验证码已发送");
    }

    @PostMapping("/complete/sendcode")
    public Result sendCodeComplete(@RequestBody @Valid SendCodeRequest sendCodeRequest) {
        messageService.sendCompletePhoneMessage(sendCodeRequest);
        return Result.ok("完善手机验证码已发送");
    }

    @PostMapping("/complete/sendemailcode")
    public Result sendEmailCodeComplete(@RequestBody @Valid SendEmailCodeRequest sendEmailCodeRequest) {
        messageService.sendCompleteEmailMessage(sendEmailCodeRequest);
        return Result.ok("完善邮箱验证码已发送");
    }
}
