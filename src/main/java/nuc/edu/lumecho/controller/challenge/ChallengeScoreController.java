package nuc.edu.lumecho.controller.challenge;

import nuc.edu.lumecho.common.Result;
import nuc.edu.lumecho.model.dto.request.challenge.ChallengeScoreRequest;
import nuc.edu.lumecho.model.entity.ChallengeScore;
import nuc.edu.lumecho.service.ChallengeScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/challenge/score")
public class ChallengeScoreController {

    @Autowired
    private ChallengeScoreService challengeScoreService;

    // 评委打分
    @PostMapping("/submit")
    public Result<Void> submitScore(@RequestBody ChallengeScoreRequest request) {
        challengeScoreService.scoreSubmission(request);
        return Result.ok();
    }

    // 获取作品所有评分
    @GetMapping("/list/{submissionId}")
    public Result<List<ChallengeScore>> getScoreList(
            @PathVariable Long submissionId) {
        return Result.ok(challengeScoreService.getScoresBySubmission(submissionId));
    }

    // 获取平均分
    @GetMapping("/avg/{submissionId}")
    public Result<BigDecimal> getAvgScore(@PathVariable Long submissionId) {
        return Result.ok(challengeScoreService.getAverageScore(submissionId));
    }

    // 判断当前评委是否已打分
    @GetMapping("/check/{submissionId}")
    public Result<Boolean> checkHasScored(@PathVariable Long submissionId) {
        Long judgeId = nuc.edu.lumecho.common.WdfUserContext.getCurrentUserId();
        return Result.ok(challengeScoreService.hasScored(submissionId, judgeId));
    }
}