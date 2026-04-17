package nuc.edu.lumecho.model.dto.request.challenge;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ChallengeScoreRequest {
    private Long submissionId;
    private BigDecimal score;
    private String comment;
}