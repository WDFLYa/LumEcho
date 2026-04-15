package nuc.edu.lumecho.controller.photographyactivity;

import nuc.edu.lumecho.common.Result;
import nuc.edu.lumecho.model.dto.request.photographer.CreateActivityRequest;
import nuc.edu.lumecho.model.dto.response.activity.PhotographyActivityListResponse;
import nuc.edu.lumecho.model.entity.PhotographyActivity;
import nuc.edu.lumecho.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity")
public class PhotographyActivityController {

    @Autowired
    private ActivityService activityService;

    @PostMapping("/create")
    public Result createActivity(@RequestBody CreateActivityRequest createActivityRequest) {
        activityService.createActivity(createActivityRequest);
        return Result.ok();
    }

    @GetMapping("/list")
    public Result<List<PhotographyActivityListResponse>> getActivityList() {
        List<PhotographyActivityListResponse> list = activityService.getActivityList();
        return Result.ok(list);
    }

    @GetMapping("/detail/{id}")
    public Result<PhotographyActivityListResponse> getActivityDetail(@PathVariable Long id) {
        PhotographyActivityListResponse detail = activityService.getActivityDetail(id);
        return Result.ok(detail);
    }
}
