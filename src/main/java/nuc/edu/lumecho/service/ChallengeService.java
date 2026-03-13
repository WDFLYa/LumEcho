package nuc.edu.lumecho.service;

import nuc.edu.lumecho.model.dto.request.challenge.ChallengeCreateRequest;
import nuc.edu.lumecho.model.entity.Challenge;

import java.util.List;

public interface ChallengeService {
    void createChallenge(ChallengeCreateRequest challengeCreateRequest);
    List<Challenge> getChallengeList(Integer status);
}
