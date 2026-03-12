package nuc.edu.lumecho.controller.photographyactivity;

import nuc.edu.lumecho.common.Result;
import nuc.edu.lumecho.model.dto.request.photographer.CreateActivityRequest;
import nuc.edu.lumecho.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
