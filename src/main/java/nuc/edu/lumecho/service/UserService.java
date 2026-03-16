package nuc.edu.lumecho.service;

import nuc.edu.lumecho.model.dto.request.user.*;
import nuc.edu.lumecho.model.dto.response.LoginResponse;
import nuc.edu.lumecho.model.dto.response.user.UserBaseInfoResponse;
import nuc.edu.lumecho.model.dto.response.user.UserDetailInfoResponse;
import nuc.edu.lumecho.model.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserService {
    LoginResponse loginByAccount(UserAccountLoginRequest userAccountLoginRequest);
    LoginResponse loginByPhone(UserPhoneLoginRequest userPhoneLoginRequest);
    void updateUserInfo(UserUpdateRequest userUpdateRequest);
    void softDeleteCurrentUser();
    void completeAccount(CompleteAccountRequest completeAccountRequest);
    void completePhone(CompletePhoneRequest completePhoneRequest);
    UserBaseInfoResponse selectUserBaseInfoById();
    UserBaseInfoResponse selectUserBaseInfo(Long id);

    User getUserById(Long id);

    UserDetailInfoResponse GetUserDetailInfo();
}
