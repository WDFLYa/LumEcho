package nuc.edu.lumecho.controller.message;

import nuc.edu.lumecho.common.Result;
import nuc.edu.lumecho.model.dto.request.SendCodeRequest;
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

    @PostMapping("/login/sendcode")
    public Result sendCodeLogin(@RequestBody @Valid SendCodeRequest sendCodeRequest) {
        messageService.sendLoginMessage(sendCodeRequest);
        return Result.ok("登录验证码已发送");
    }

}
