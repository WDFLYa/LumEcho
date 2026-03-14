package nuc.edu.lumecho.model.dto.response.user;

import lombok.Data;

@Data
public class UserBaseInfoResponse {
    private Long id;
    private String username;
    private String bio;
    private String avatar;
}
