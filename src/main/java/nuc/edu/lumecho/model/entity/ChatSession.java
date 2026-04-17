package nuc.edu.lumecho.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatSession {
    private Long id;
    private Long userId;
    private Long photographerId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}