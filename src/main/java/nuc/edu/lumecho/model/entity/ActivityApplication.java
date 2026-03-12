package nuc.edu.lumecho.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivityApplication {
    private Long id;
    private Long activityId;
    private Long userId;
    private LocalDateTime applyTime;
    private int status;
}