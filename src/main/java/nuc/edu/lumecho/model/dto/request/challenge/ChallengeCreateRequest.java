package nuc.edu.lumecho.model.dto.request.challenge;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChallengeCreateRequest {
    private String title;           // 标题
    private String description;     // 详情
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;// 开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;  // 截止时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reviewEndTime; // 评审结束时间
    private String rules;
    private String prizes;
    private String coverUrl;
    private Integer maxParticipants;     // 最大人数 (-1为不限)
}
