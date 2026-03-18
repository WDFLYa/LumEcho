package nuc.edu.lumecho.model.dto.response.user;

import lombok.Data;

@Data
public class FollowListResponse {
    private Long userId;
    private String userName;
    private String bio;
    private String avatar;
    private boolean status;
}
