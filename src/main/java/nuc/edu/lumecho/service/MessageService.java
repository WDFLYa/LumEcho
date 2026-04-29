package nuc.edu.lumecho.service;

import nuc.edu.lumecho.model.dto.request.SendCodeRequest;
import nuc.edu.lumecho.model.dto.request.SendEmailCodeRequest;

public interface MessageService {
    void sendRegisterMessage(SendCodeRequest sendCodeRequest);

    void sendEmailRegisterMessage(SendEmailCodeRequest sendEmailCodeRequest);

    void sendEmailLoginMessage(SendEmailCodeRequest sendEmailCodeRequest);

    void sendCompleteEmailMessage(SendEmailCodeRequest sendEmailCodeRequest);


    void sendLoginMessage(SendCodeRequest sendCodeRequest);

    void sendCompletePhoneMessage(SendCodeRequest sendCodeRequest);
}
