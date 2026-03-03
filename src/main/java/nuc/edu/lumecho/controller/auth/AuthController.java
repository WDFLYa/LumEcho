package nuc.edu.lumecho.controller.auth;


import nuc.edu.lumecho.common.Result;
import nuc.edu.lumecho.model.dto.UserAccountRegisterRequest;
import nuc.edu.lumecho.model.dto.UserPhoneRegisterRequest;
import nuc.edu.lumecho.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @PostMapping("/register/account")
    public Result registerByAccount(@RequestBody @Valid UserAccountRegisterRequest request) {
        authService.registerByAccount(request);
        return Result.ok();
    }

    @PostMapping("/register/phone")
    public Result registerByPhone(@RequestBody @Valid UserPhoneRegisterRequest request) {
        authService.registerByPhone(request);
        return Result.ok();
    }
}
