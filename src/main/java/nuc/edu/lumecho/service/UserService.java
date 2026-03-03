package nuc.edu.lumecho.service;

import nuc.edu.lumecho.model.dto.request.UserAccountLoginRequest;
import nuc.edu.lumecho.model.dto.request.UserPhoneLoginRequest;
import nuc.edu.lumecho.model.dto.response.LoginResponse;

public interface UserService {
    LoginResponse loginByAccount(UserAccountLoginRequest userAccountLoginRequest);
    LoginResponse loginByPhone(UserPhoneLoginRequest userPhoneLoginRequest);
}
