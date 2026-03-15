package nuc.edu.lumecho.service;

import nuc.edu.lumecho.model.dto.request.challenge.ChallengeCreateRequest;
import nuc.edu.lumecho.model.dto.response.challenge.ChallengeListResponse;
import nuc.edu.lumecho.model.entity.Challenge;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChallengeService {
    void createChallenge(ChallengeCreateRequest challengeCreateRequest);
    ChallengeListResponse getChallengeList(Integer status, String keyword, int pageNum, int pageSize);

    Challenge getChallengeDetail(Long id);
}
