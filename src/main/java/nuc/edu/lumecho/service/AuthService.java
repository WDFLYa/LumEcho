package nuc.edu.lumecho.service;

import nuc.edu.lumecho.model.dto.UserAccountRegisterRequest;
import nuc.edu.lumecho.model.dto.UserPhoneRegisterRequest;

public interface AuthService {
    void registerByAccount(UserAccountRegisterRequest request);
    void registerByPhone(UserPhoneRegisterRequest request);
}
