package nuc.edu.lumecho.service.impl;

import nuc.edu.lumecho.common.WdfUserContext;
import nuc.edu.lumecho.mapper.ChallengeScoreMapper;
import nuc.edu.lumecho.mapper.ChallengeSubmissionMapper;
import nuc.edu.lumecho.mapper.UserMapper;
import nuc.edu.lumecho.model.dto.request.challenge.ChallengeScoreRequest;
import nuc.edu.lumecho.model.entity.ChallengeScore;
import nuc.edu.lumecho.model.entity.ChallengeSubmission;
import nuc.edu.lumecho.service.ChallengeScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ChallengeScoreServiceImpl implements ChallengeScoreService {

    @Autowired
    private ChallengeScoreMapper challengeScoreMapper;

    @Autowired
    private ChallengeSubmissionMapper challengeSubmissionMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public void scoreSubmission(ChallengeScoreRequest request) {
        Long judgeId = WdfUserContext.getCurrentUserId();

        boolean hasScored = challengeScoreMapper.existsBySubmissionAndJudge(
                request.getSubmissionId(), judgeId);
        if (hasScored) {
            throw new RuntimeException("你已经给该作品打过分啦");
        }

        ChallengeScore score = new ChallengeScore();
        score.setSubmissionId(request.getSubmissionId());
        score.setJudgeId(judgeId);
        score.setJudgeName(userMapper.selectUserBaseInfoById(judgeId).getUsername());
        score.setScore(request.getScore());
        score.setComment(request.getComment());
        challengeScoreMapper.insert(score);

        // 计算平均分
        BigDecimal avg = challengeScoreMapper.selectAvgScore(request.getSubmissionId());

        // ✅ 这里改成 updateFinalScore
        ChallengeSubmission submission = new ChallengeSubmission();
        submission.setId(request.getSubmissionId());
        submission.setFinalScore(avg);
        challengeSubmissionMapper.updateFinalScore(submission); // ✅ 修复
    }

    @Override
    public List<ChallengeScore> getScoresBySubmission(Long submissionId) {
        return challengeScoreMapper.selectBySubmissionId(submissionId);
    }

    @Override
    public BigDecimal getAverageScore(Long submissionId) {
        return challengeScoreMapper.selectAvgScore(submissionId);
    }

    @Override
    public boolean hasScored(Long submissionId, Long judgeId) {
        return challengeScoreMapper.existsBySubmissionAndJudge(submissionId, judgeId);
    }
}