package nuc.edu.lumecho.service;

import nuc.edu.lumecho.model.dto.request.user.*;
import nuc.edu.lumecho.model.dto.response.LoginResponse;

public interface UserService {
    LoginResponse loginByAccount(UserAccountLoginRequest userAccountLoginRequest);
    LoginResponse loginByPhone(UserPhoneLoginRequest userPhoneLoginRequest);
    void updateUserInfo(UserUpdateRequest userUpdateRequest);
    void softDeleteCurrentUser();
    void completeAccount(CompleteAccountRequest completeAccountRequest);
    void completePhone(CompletePhoneRequest completePhoneRequest);
}
