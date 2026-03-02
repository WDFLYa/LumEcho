package nuc.edu.lumecho.model.dto;

import lombok.Data;

@Data
public class UserAccountRegisterRequest {
    private String account;
    private String password;
}
