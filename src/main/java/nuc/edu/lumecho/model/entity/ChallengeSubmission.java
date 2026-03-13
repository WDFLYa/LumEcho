package nuc.edu.lumecho.model.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ChallengeSubmission {

    private Long id;
    private Long applicationId;
    private String title;
    private String content;
    private String location;
    private BigDecimal finalScore;
    private Integer status;
    private LocalDateTime submitTime;
    private LocalDateTime updateTime;


}
