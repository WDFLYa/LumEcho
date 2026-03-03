package nuc.edu.lumecho.controller.message;

import lombok.Data;
import nuc.edu.lumecho.common.Enum.ResultCodeEnum;
import nuc.edu.lumecho.common.Result;
import nuc.edu.lumecho.common.exception.BusinessException;
import nuc.edu.lumecho.mapper.UserMapper;
import nuc.edu.lumecho.model.dto.SendCodeRequest;
import nuc.edu.lumecho.model.dto.UserAccountRegisterRequest;
import nuc.edu.lumecho.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/sendcode")
    public Result sendCodeByPhone(@RequestBody @Valid SendCodeRequest request) {
        messageService.sendRegisterMessage(request.getPhone());
        return Result.ok("验证码已发送");
    }


}
