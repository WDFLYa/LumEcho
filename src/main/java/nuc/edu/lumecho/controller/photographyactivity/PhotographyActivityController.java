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

    // 👇 改成 分页 + 筛选 + 模糊查询
    @GetMapping("/list")
    public Result<List<PhotographyActivityListResponse>> getActivityList(
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        List<PhotographyActivityListResponse> list = activityService.getActivityList(status, keyword, pageNum, pageSize);
        return Result.ok(list);
    }

    @GetMapping("/detail/{id}")
    public Result<PhotographyActivityListResponse> getActivityDetail(@PathVariable Long id) {
        PhotographyActivityListResponse detail = activityService.getActivityDetail(id);
        return Result.ok(detail);
    }


    @PostMapping("/cancel/{id}")
    public Result<Boolean> cancelActivity(@PathVariable Long id) {
        activityService.cancelActivity(id);
        return Result.ok();
    }
}