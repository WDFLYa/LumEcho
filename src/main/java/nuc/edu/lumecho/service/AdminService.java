package nuc.edu.lumecho.service;

import nuc.edu.lumecho.model.dto.request.RestoreUserRequest;
import nuc.edu.lumecho.model.dto.request.UpdateUserRoleRequest;
import nuc.edu.lumecho.model.dto.request.UpdateUserStatusRequest;

public interface AdminService {
    void restoreUser(RestoreUserRequest restoreUserRequest);
    void updateUserStatus(UpdateUserStatusRequest updateUserStatusRequest);
    void updateUserRole(UpdateUserRoleRequest updateUserRoleRequest);
}
