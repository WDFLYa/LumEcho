package nuc.edu.lumecho.controller.photographyactivity;

import nuc.edu.lumecho.common.Result;
import nuc.edu.lumecho.model.dto.request.checkin.CheckInRequest;
import nuc.edu.lumecho.service.ActivityCheckinService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/activity")
public class ActivityCheckinController {

    @Resource
    private ActivityCheckinService activityCheckinService;

    @PostMapping("/checkin")
    public Result checkIn(@RequestBody CheckInRequest request) {
        String address = activityCheckinService.checkIn(request);
        return Result.ok("签到成功！位置：" + address);
    }
}