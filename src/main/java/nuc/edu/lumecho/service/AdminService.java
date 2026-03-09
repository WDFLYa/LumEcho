package nuc.edu.lumecho.service;

import nuc.edu.lumecho.model.dto.request.user.RestoreUserRequest;
import nuc.edu.lumecho.model.dto.request.user.UpdateUserRoleRequest;
import nuc.edu.lumecho.model.dto.request.user.UpdateUserStatusRequest;

public interface AdminService {
    void restoreUser(RestoreUserRequest restoreUserRequest);
    void updateUserStatus(UpdateUserStatusRequest updateUserStatusRequest);
    void updateUserRole(UpdateUserRoleRequest updateUserRoleRequest);
}
