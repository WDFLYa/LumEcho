package nuc.edu.lumecho.service;

import nuc.edu.lumecho.model.dto.request.user.UserAccountRegisterRequest;
import nuc.edu.lumecho.model.dto.request.user.UserPhoneRegisterRequest;

public interface AuthService {
    void registerByAccount(UserAccountRegisterRequest userAccountRegisterRequest);
    void registerByPhone(UserPhoneRegisterRequest userPhoneRegisterRequest);
}
