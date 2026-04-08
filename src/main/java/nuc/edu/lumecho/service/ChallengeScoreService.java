package nuc.edu.lumecho.service;

import nuc.edu.lumecho.model.dto.request.challenge.ChallengeScoreRequest;
import nuc.edu.lumecho.model.entity.ChallengeScore;

import java.math.BigDecimal;
import java.util.List;

public interface ChallengeScoreService {

    // 评委打分
    void scoreSubmission(ChallengeScoreRequest request);

    // 获取作品所有评分
    List<ChallengeScore> getScoresBySubmission(Long submissionId);

    // 获取平均分
    BigDecimal getAverageScore(Long submissionId);

    // 判断是否已打分
    boolean hasScored(Long submissionId, Long judgeId);
}