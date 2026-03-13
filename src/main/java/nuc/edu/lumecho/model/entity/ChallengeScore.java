package nuc.edu.lumecho.model.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ChallengeScore {
    private Long id;

    // 关联作品ID
    private Long submissionId;

    // 评委信息
    private Long judgeId;
    private String judgeName; // 冗余字段，防止用户表删除后找不到名字

    // 打分详情
    private BigDecimal score;
    private String comment;

    private LocalDateTime createTime;
}
