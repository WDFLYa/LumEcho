package nuc.edu.lumecho.model.dto.response.post;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostHomeItemResponse {
    private Long id;
    private String username;
    private String avatar;
    private String title;
    private Integer status;
    private String cover;
    private Integer likes;
    private Integer comments;
    private Integer views;
    private String category;
    private LocalDateTime createTime;
    private String timeAgo;
}