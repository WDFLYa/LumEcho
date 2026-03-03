package nuc.edu.lumecho.controller.user;


import nuc.edu.lumecho.common.Result;
import nuc.edu.lumecho.model.dto.request.UserAccountLoginRequest;
import nuc.edu.lumecho.model.dto.request.UserPhoneLoginRequest;
import nuc.edu.lumecho.model.dto.response.LoginResponse;
import nuc.edu.lumecho.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("login/account")
    public Result<LoginResponse> loginByAccount(@RequestBody @Valid UserAccountLoginRequest userAccountLoginRequest) {
        LoginResponse loginResponse = userService.loginByAccount(userAccountLoginRequest);
        return Result.ok(loginResponse);
    }
    @PostMapping("login/phone")
    public Result<LoginResponse> loginByPhone(@RequestBody @Valid UserPhoneLoginRequest userPhoneLoginRequest) {
        LoginResponse loginResponse = userService.loginByPhone(userPhoneLoginRequest);
        return Result.ok(loginResponse);
    }
}
