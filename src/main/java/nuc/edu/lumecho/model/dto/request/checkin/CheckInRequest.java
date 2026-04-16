package nuc.edu.lumecho.model.dto.request.checkin;

import lombok.Data;

@Data
public class CheckInRequest {
    private Long activityId;
    private Double latitude;
    private Double longitude;
}