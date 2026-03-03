package nuc.edu.lumecho.service;

import nuc.edu.lumecho.model.dto.request.SendCodeRequest;

public interface MessageService {
    void sendRegisterMessage(SendCodeRequest sendCodeRequest);
}
