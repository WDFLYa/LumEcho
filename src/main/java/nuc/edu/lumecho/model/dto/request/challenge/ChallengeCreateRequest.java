package nuc.edu.lumecho.model.dto.request.challenge;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChallengeCreateRequest {
    private String title;           // 标题
    private String description;     // 详情
    private LocalDateTime startTime;// 开始时间
    private LocalDateTime endTime;  // 截止时间
    private LocalDateTime reviewEndTime; // 评审结束时间
    private Integer maxParticipants;     // 最大人数 (-1为不限)
}
