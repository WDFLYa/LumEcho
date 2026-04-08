package nuc.edu.lumecho.controller.challengeapplication;

import nuc.edu.lumecho.common.Result;
import nuc.edu.lumecho.model.entity.ChallengeApplication;
import nuc.edu.lumecho.service.ChallengeApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/challengeapplication")
public class ChallengeApplicationController {

    @Autowired
    private ChallengeApplicationService challengeApplicationService;


    /**
     * 用户报名挑战赛
     * POST /api/challengeapplication/{challengeId}/apply
     */
    @PostMapping("/{challengeId}/apply")
    public Result apply(@PathVariable Long challengeId) {
        challengeApplicationService.createChallengeApplication(challengeId);
        return Result.ok();
    }

    /**
     * 用户取消报名
     * POST /api/challengeapplication/applications/{applicationId}/cancel
     */
    @PostMapping("/applications/{applicationId}/cancel")
    public Result cancel(@PathVariable Long applicationId) {
        challengeApplicationService.cancelApplication(applicationId);
        return Result.ok();
    }

    /**
     * 管理员审核通过
     */
    @PostMapping("/applications/{applicationId}/approve")
    public Result approve(@PathVariable Long applicationId) {
        challengeApplicationService.approveApplication(applicationId);
        return Result.ok();
    }

    /**
     * 管理员审核拒绝
     */
    @PostMapping("/applications/{applicationId}/reject")
    public Result reject(@PathVariable Long applicationId) {
        challengeApplicationService.rejectApplication(applicationId);
        return Result.ok();
    }

    /**
     * 获取所有报名记录
     */
    @GetMapping("/applications/listall")
    public Result<List<ChallengeApplication>> list() {
        return Result.ok(challengeApplicationService.listAll());
    }

    // ====================== ✅ 已改造：返回状态 + 是否提交作品 ======================
    /**
     * 获取当前用户对某个比赛的报名状态 + 是否已提交作品
     */
    @GetMapping("/status/{challengeId}")
    public Result getUserApplyStatus(@PathVariable Long challengeId) {
        Integer applyStatus = challengeApplicationService.getUserApplyStatus(challengeId);
        boolean hasSubmitted = challengeApplicationService.checkHasSubmittedWork(challengeId);

        Map<String, Object> map = new HashMap<>();
        map.put("applyStatus", applyStatus);
        map.put("hasSubmitted", hasSubmitted);

        return Result.ok(map);
    }

    @GetMapping("/getByUserAndChallenge")
    public Result getByUserAndChallenge(
            @RequestParam Long challengeId,
            @RequestParam Long userId
    ) {
        ChallengeApplication application = challengeApplicationService.getByChallengeAndUser(challengeId, userId);
        return Result.ok(application);
    }
}