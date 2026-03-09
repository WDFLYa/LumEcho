package nuc.edu.lumecho.controller.admin;

import nuc.edu.lumecho.common.Result;
import nuc.edu.lumecho.model.dto.request.post.PostIdRequest;
import nuc.edu.lumecho.model.dto.request.post.UpdatePostStatusRequest;
import nuc.edu.lumecho.model.dto.request.user.RestoreUserRequest;
import nuc.edu.lumecho.model.dto.request.user.UpdateUserRoleRequest;
import nuc.edu.lumecho.model.dto.request.user.UpdateUserStatusRequest;
import nuc.edu.lumecho.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("restore")
    public Result deleteUser(@RequestBody @Valid RestoreUserRequest restoreUserRequest) {
        adminService.restoreUser(restoreUserRequest);
        return Result.ok();
    }

    @PostMapping("update/user/status")
    public Result updateUserStatus(@RequestBody @Valid UpdateUserStatusRequest updateUserStatusRequest) {
        adminService.updateUserStatus(updateUserStatusRequest);
        return Result.ok();
    }

    @PostMapping("update/role")
    public Result updateUserRole(@RequestBody @Valid UpdateUserRoleRequest updateUserRoleRequest) {
        adminService.updateUserRole(updateUserRoleRequest);
        return Result.ok();
    }

    @PostMapping("update/post/status")
    public Result updatePostStatus(@RequestBody @Valid UpdatePostStatusRequest updatePostStatusRequest) {
        adminService.updateStatus(updatePostStatusRequest);
        return Result.ok();
    }
}
