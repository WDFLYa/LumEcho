package nuc.edu.lumecho.model.dto.response.activity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PhotographyActivityListResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private Integer maxParticipants;
    private Integer currentParticipants;
    private Long photographerId;
    private Integer status;
    private Integer requireAudit;
    private String coverUrl;
}