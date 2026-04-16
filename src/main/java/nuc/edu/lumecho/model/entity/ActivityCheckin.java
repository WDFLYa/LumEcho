package nuc.edu.lumecho.model.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ActivityCheckin {
    private Long id;
    private Long activityId;
    private Long userId;
    private Double latitude;
    private Double longitude;
    private String address;
    private LocalDateTime checkinTime;
}