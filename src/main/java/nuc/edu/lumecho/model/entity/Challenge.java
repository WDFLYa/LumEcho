package nuc.edu.lumecho.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Challenge {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime reviewEndTime;
    private Integer status;
    private Integer maxParticipants;
    private Integer participantCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
