package nuc.edu.lumecho.service;

import nuc.edu.lumecho.model.dto.request.challenge.ChallengeSubmissionRequest;
import nuc.edu.lumecho.model.dto.response.challenge.ChallengeSubmissionItemResponse;

import java.util.List;

public interface ChallengeSubmissionService {
    void createChallengeSubmission(ChallengeSubmissionRequest challengeSubmissionRequest);

    List<ChallengeSubmissionItemResponse> getSubmissionList(Long challengeId);
}
