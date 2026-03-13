package nuc.edu.lumecho.service.impl;

import nuc.edu.lumecho.common.Enum.ChallengeStatus;
import nuc.edu.lumecho.common.Enum.ResultCodeEnum;
import nuc.edu.lumecho.common.WdfUserContext;
import nuc.edu.lumecho.common.exception.BusinessException;
import nuc.edu.lumecho.mapper.ChallengeMapper;
import nuc.edu.lumecho.mapper.UserMapper;
import nuc.edu.lumecho.model.dto.request.challenge.ChallengeCreateRequest;
import nuc.edu.lumecho.model.entity.Challenge;
import nuc.edu.lumecho.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChallengeServiceImpl implements ChallengeService {

    @Autowired
    private ChallengeMapper challengeMapper;

    @Autowired
    private UserMapper userMapper;
    @Override
    public void createChallenge(ChallengeCreateRequest challengeCreateRequest) {

        String role = userMapper.getUserRoleById(WdfUserContext.getCurrentUserId());

        if (!"ADMIN".equals(role) && !"PHOTOGRAPHER".equals(role)) {
            throw new BusinessException(ResultCodeEnum.CHALLENGE_PUBLISH_FORBIDDEN);
        }

        Challenge challenge = new Challenge();
        challenge.setTitle(challengeCreateRequest.getTitle());
        challenge.setDescription(challengeCreateRequest.getDescription());
        challenge.setStartTime(challengeCreateRequest.getStartTime());
        challenge.setEndTime(challengeCreateRequest.getEndTime());
        challenge.setReviewEndTime(challengeCreateRequest.getReviewEndTime());
        challenge.setMaxParticipants(challengeCreateRequest.getMaxParticipants());
        challenge.setParticipantCount(0);
        challenge.setStatus(ChallengeStatus.NOT_STARTED.getCode());
        challenge.setCreatedAt(LocalDateTime.now());
        challenge.setUpdatedAt(LocalDateTime.now());
        challengeMapper.createChallenge(challenge);
    }

    @Override
    public List<Challenge> getChallengeList(Integer status) {
        return challengeMapper.selectChallengeList(status);
    }
}
