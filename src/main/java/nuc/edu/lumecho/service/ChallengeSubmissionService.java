package nuc.edu.lumecho.service;

import nuc.edu.lumecho.model.dto.request.challenge.ChallengeSubmissionRequest;

public interface ChallengeSubmissionService {
    void createChallengeSubmission(ChallengeSubmissionRequest challengeSubmissionRequest);
}
