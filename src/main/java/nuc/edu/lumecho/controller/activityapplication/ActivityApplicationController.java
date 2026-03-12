package nuc.edu.lumecho.controller.activityapplication;

import nuc.edu.lumecho.common.Result;
import nuc.edu.lumecho.model.entity.ActivityApplication;
import nuc.edu.lumecho.service.ActivityApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activityapplication")
public class ActivityApplicationController {

    @Autowired
    private ActivityApplicationService activityApplicationService;

    @PostMapping("/{activityId}/apply")
    public Result apply(@PathVariable Long activityId) {
        activityApplicationService.createActivityApplication(activityId);
        return Result.ok();
    }

    @PostMapping("/applications/{applicationId}/cancel")
    public Result cancel(@PathVariable Long applicationId) {
        activityApplicationService.cancelApplication(applicationId);
        return Result.ok();
    }

    @PostMapping("/applications/{applicationId}/approve")
    public Result approve(@PathVariable Long applicationId) {
        activityApplicationService.approveApplication(applicationId);
        return Result.ok();
    }

    @PostMapping("/applications/{applicationId}/reject")
    public Result reject(@PathVariable Long applicationId) {
        activityApplicationService.rejectApplication(applicationId);
        return Result.ok();
    }

    @GetMapping("/applications/listall")
    public Result<List<ActivityApplication>> list() {
        return Result.ok(activityApplicationService.listAll());
    }

}
