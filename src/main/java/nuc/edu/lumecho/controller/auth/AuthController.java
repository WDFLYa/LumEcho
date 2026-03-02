package nuc.edu.lumecho.controller.auth;


import nuc.edu.lumecho.common.Result;
import nuc.edu.lumecho.model.dto.UserAccountRegisterRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @PostMapping("/register/account")
    public Result<?> registerByAccount(@RequestBody UserAccountRegisterRequest request) {

        return Result.ok("注册成功");
    }
}
