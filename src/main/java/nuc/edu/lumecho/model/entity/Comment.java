package nuc.edu.lumecho.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {
    private Long id;
    private Long postId;
    private Long userId;
    private String content;
    private Long parentId;
    private LocalDateTime createTime;
}
