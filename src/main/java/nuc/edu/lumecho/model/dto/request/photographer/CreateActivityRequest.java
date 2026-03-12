package nuc.edu.lumecho.model.dto.request.photographer;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateActivityRequest {
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private Integer maxParticipants;
    private Boolean requireAudit;
}
