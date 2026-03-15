package nuc.edu.lumecho.service.impl;

import nuc.edu.lumecho.common.Enum.ResourceTypeEnum;
import nuc.edu.lumecho.common.Enum.SubmissionStatusEnum;
import nuc.edu.lumecho.mapper.ChallengeSubmissionMapper;
import nuc.edu.lumecho.mapper.ResourceFileMapper;
import nuc.edu.lumecho.model.dto.request.challenge.ChallengeSubmissionRequest;
import nuc.edu.lumecho.model.dto.response.challenge.ChallengeSubmissionItemResponse;
import nuc.edu.lumecho.model.entity.ChallengeSubmission;
import nuc.edu.lumecho.model.entity.ResourceFile;
import nuc.edu.lumecho.service.ChallengeSubmissionService;
import nuc.edu.lumecho.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChallengeSubmissionServiceImpl implements ChallengeSubmissionService {

    @Autowired
    private ChallengeSubmissionMapper challengeSubmissionMapper;

    @Autowired
    private ResourceFileMapper resourceFileMapper;
    @Override
    public void createChallengeSubmission(ChallengeSubmissionRequest challengeSubmissionRequest) {
        ChallengeSubmission challengeSubmission = new ChallengeSubmission();
        challengeSubmission.setApplicationId(challengeSubmissionRequest.getApplicationId());
        challengeSubmission.setTitle(challengeSubmissionRequest.getTitle());
        challengeSubmission.setContent(challengeSubmissionRequest.getContent());
        challengeSubmission.setLocation(challengeSubmissionRequest.getLocation());
        challengeSubmission.setFinalScore(null);
        challengeSubmission.setStatus(SubmissionStatusEnum.SUBMITTED.getCode());
        challengeSubmission.setSubmitTime(LocalDateTime.now());
        challengeSubmission.setUpdateTime(LocalDateTime.now());
        challengeSubmissionMapper.insert(challengeSubmission);
        resourceFileMapper.bindBizIdByUrl(challengeSubmissionRequest.getFileUrl(),ResourceTypeEnum.CHALLENGE_SUBMISSION_IMAGE.getCode(), challengeSubmission.getId());
    }

    @Override
    public List<ChallengeSubmissionItemResponse> getSubmissionList(Long challengeId) {

        return challengeSubmissionMapper.selectSubmissionList(challengeId);

    }
}
