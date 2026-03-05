package nuc.edu.lumecho.service;

import nuc.edu.lumecho.model.dto.request.RestoreUserRequest;

public interface AdminService {
    void restoreUser(RestoreUserRequest restoreUserRequest);
}
