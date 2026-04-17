package nuc.edu.lumecho.controller.activityapplication;

import nuc.edu.lumecho.common.Result;
import nuc.edu.lumecho.model.entity.PhotographyActivity;
import nuc.edu.lumecho.model.vo.ActivityApplicationVO;
import nuc.edu.lumecho.service.ActivityApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/activity")
public class ActivityApplicationAdminController {

    @Autowired
    private ActivityApplicationService activityApplicationService;

    /**
     * 管理员：获取所有待开始活动列表
     */
    @GetMapping("/pending")
    public Result<List<PhotographyActivity>> getPendingActivityList() {
        return Result.ok(activityApplicationService.getPendingActivityList());
    }

    /**
     * 管理员：获取某个活动的全部报名（含用户信息+活动标题）
     */
    @GetMapping("/apply/list/{activityId}")
    public Result<List<ActivityApplicationVO>> getApplyList(
            @PathVariable Long activityId
    ) {
        return Result.ok(activityApplicationService.getApplyListWithUserInfo(activityId));
    }

    /**
     * 管理员：通过报名
     */
    @PostMapping("/apply/approve/{applicationId}")
    public Result approve(@PathVariable Long applicationId) {
        activityApplicationService.adminApprove(applicationId);
        return Result.ok();
    }

    /**
     * 管理员：拒绝报名
     */
    @PostMapping("/apply/reject/{applicationId}")
    public Result reject(@PathVariable Long applicationId) {
        activityApplicationService.adminReject(applicationId);
        return Result.ok();
    }
}