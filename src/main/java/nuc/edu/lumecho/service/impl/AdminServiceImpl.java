package nuc.edu.lumecho.service.impl;

import nuc.edu.lumecho.mapper.UserMapper;
import nuc.edu.lumecho.model.dto.request.user.RestoreUserRequest;
import nuc.edu.lumecho.model.dto.request.user.UpdateUserRoleRequest;
import nuc.edu.lumecho.model.dto.request.user.UpdateUserStatusRequest;
import nuc.edu.lumecho.model.entity.User;
import nuc.edu.lumecho.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void restoreUser(RestoreUserRequest restoreUserRequest) {
        User user = new User();
        user.setId(restoreUserRequest.getId());
        user.setDeletedAt(null);
        userMapper.updateUser(user);
    }

    @Override
    public void updateUserStatus(UpdateUserStatusRequest updateUserStatusRequest) {
        User user = new User();
        user.setId(updateUserStatusRequest.getId());
        user.setStatus(updateUserStatusRequest.getStatus().getCode());
        userMapper.updateUser(user);
    }

    @Override
    public void updateUserRole(UpdateUserRoleRequest updateUserRoleRequest) {
        User user = new User();
        user.setId(updateUserRoleRequest.getId());
        user.setRole(updateUserRoleRequest.getRole().getCode());
        userMapper.updateUser(user);
    }
}
