package nuc.edu.lumecho.service;

import nuc.edu.lumecho.model.dto.UserAccountRegisterRequest;

public interface AuthService {
    void registerByAccount(UserAccountRegisterRequest request);
}
