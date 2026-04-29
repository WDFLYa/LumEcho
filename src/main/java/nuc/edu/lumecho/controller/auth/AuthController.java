package nuc.edu.lumecho.controller.auth;


import nuc.edu.lumecho.common.Result;
import nuc.edu.lumecho.model.dto.request.user.UserAccountRegisterRequest;
import nuc.edu.lumecho.model.dto.request.user.UserEmailRegisterRequest;
import nuc.edu.lumecho.model.dto.request.user.UserPhoneRegisterRequest;
import nuc.edu.lumecho.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @PostMapping("/register/account")
    public Result registerByAccount(@RequestBody @Valid UserAccountRegisterRequest userAccountRegisterRequest) {
        authService.registerByAccount(userAccountRegisterRequest);
        return Result.ok();
    }

    @PostMapping("/register/phone")
    public Result registerByPhone(@RequestBody @Valid UserPhoneRegisterRequest userPhoneRegisterRequest) {
        authService.registerByPhone(userPhoneRegisterRequest);
        return Result.ok();
    }

    @PostMapping("/register/email")
    public Result registerByEmail(@RequestBody @Valid UserEmailRegisterRequest userEmailRegisterRequest) {
        authService.registerByEmail(userEmailRegisterRequest);
        return Result.ok();
    }

    @PostMapping("/logout")
    public Result logout(@RequestHeader("Authorization") String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return Result.fail(400, "无效的Token格式");
        }

        String token = authorization.substring(7);
        authService.logout(token);

        return Result.ok("退出登录成功");
    }
}
