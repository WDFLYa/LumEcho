package nuc.edu.lumecho.model.dto.request.challenge;

import lombok.Data;

@Data
public class ChallengeSubmissionRequest {
    private Long applicationId;
    private String title;
    private String content;
    private String location;
    private String fileUrl;
}
