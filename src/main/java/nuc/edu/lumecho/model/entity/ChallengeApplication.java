package nuc.edu.lumecho.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChallengeApplication {
    private Long id;

    private Long challengeId;

    private Long userId;

    private LocalDateTime applyTime;

    private Integer status;

    private String remark;
}
