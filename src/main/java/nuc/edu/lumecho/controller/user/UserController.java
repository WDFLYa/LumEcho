package nuc.edu.lumecho.controller.user;


import nuc.edu.lumecho.common.Result;
import nuc.edu.lumecho.common.WdfUserContext;
import nuc.edu.lumecho.model.dto.request.user.*;
import nuc.edu.lumecho.model.dto.response.LoginResponse;
import nuc.edu.lumecho.model.dto.response.user.UserBaseInfoResponse;
import nuc.edu.lumecho.model.dto.response.user.UserDetailInfoResponse;
import nuc.edu.lumecho.model.entity.User;
import nuc.edu.lumecho.service.FileService;
import nuc.edu.lumecho.service.ResourceFileService;
import nuc.edu.lumecho.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Autowired
    private ResourceFileService resourceFileService;
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

    @PostMapping("update")
    public Result updateUserInfo(@RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        userService.updateUserInfo(userUpdateRequest);
        return Result.ok();
    }

    @PostMapping("delete")
    public Result deleteUser() {
        userService.softDeleteCurrentUser();
        return Result.ok();
    }

    @PostMapping("complete/account")
    public Result completeAccount(@RequestBody @Valid CompleteAccountRequest completeAccountRequest) {
        userService.completeAccount(completeAccountRequest);
        return Result.ok();
    }

    @PostMapping("complete/phone")
    public Result completePhone(@RequestBody @Valid CompletePhoneRequest completePhoneRequest) {
        userService.completePhone(completePhoneRequest);
        return Result.ok();
    }

    @GetMapping("getuserinfo")
    public Result<UserBaseInfoResponse> selectUserBaseInfoById() {
        return Result.ok(userService.selectUserBaseInfoById());
    }

    @GetMapping("/getuserinfo/{id}")
    public Result<UserBaseInfoResponse> getUserById(@PathVariable("id") Long id) {
        return Result.ok(userService.selectUserBaseInfo(id));
    }

    @GetMapping("/getuser/{id}")
    public Result<User> getUser(@PathVariable("id") Long id) {
        return Result.ok(userService.getUserById(id));
    }

    @GetMapping("/getuser")
    public Result<UserDetailInfoResponse> getUserDetailInfo() {
        return Result.ok(userService.GetUserDetailInfo());
    }

    @GetMapping("/list")
    public Result<List<UserDetailInfoResponse>> getUserList() {

        List<UserDetailInfoResponse> list = userService.getAllUsersForAdmin();
        return Result.ok(list);
    }

    @PutMapping("/status/{account}")
    public Result<Void> updateUserStatus(@PathVariable String account, @RequestBody Map<String, Integer> body) {
        Integer status = body.get("status");

        userService.updateUserStatusByAccount(account, status);

        return Result.ok();
    }


}