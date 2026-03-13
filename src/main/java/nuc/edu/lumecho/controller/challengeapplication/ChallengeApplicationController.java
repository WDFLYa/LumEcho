package nuc.edu.lumecho.controller.challengeapplication;

import nuc.edu.lumecho.common.Result;
import nuc.edu.lumecho.model.entity.ChallengeApplication;
import nuc.edu.lumecho.service.ChallengeApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * POST /api/challengeapplication/applications/{applicationId}/approve
     */
    @PostMapping("/applications/{applicationId}/approve")
    public Result approve(@PathVariable Long applicationId) {
        challengeApplicationService.approveApplication(applicationId);
        return Result.ok();
    }

    /**
     * 管理员审核拒绝
     * POST /api/challengeapplication/applications/{applicationId}/reject
     */
    @PostMapping("/applications/{applicationId}/reject")
    public Result reject(@PathVariable Long applicationId) {
        challengeApplicationService.rejectApplication(applicationId);
        return Result.ok();
    }

    /**
     * 获取所有报名记录
     * GET /api/challengeapplication/applications/listall
     */
    @GetMapping("/applications/listall")
    public Result<List<ChallengeApplication>> list() {
        return Result.ok(challengeApplicationService.listAll());
    }
}