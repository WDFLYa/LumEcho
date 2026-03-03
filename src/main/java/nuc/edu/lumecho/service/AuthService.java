package nuc.edu.lumecho.service;

import nuc.edu.lumecho.model.dto.request.UserAccountRegisterRequest;
import nuc.edu.lumecho.model.dto.request.UserPhoneRegisterRequest;

public interface AuthService {
    void registerByAccount(UserAccountRegisterRequest userAccountRegisterRequest);
    void registerByPhone(UserPhoneRegisterRequest userPhoneRegisterRequest);
}
