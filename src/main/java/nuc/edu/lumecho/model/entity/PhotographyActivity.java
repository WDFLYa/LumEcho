package nuc.edu.lumecho.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PhotographyActivity {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private Integer maxParticipants;
    private Integer currentParticipants;
    private Long photographerId;
    private int status;
    private Boolean requireAudit;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}