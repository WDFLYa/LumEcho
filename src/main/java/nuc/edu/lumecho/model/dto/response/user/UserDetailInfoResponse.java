package nuc.edu.lumecho.model.dto.response.user;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDetailInfoResponse {
    private String username;
    private String bio;
    private String email;
    private String account;
    private String phone;
    private Integer status;
    private String role;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
