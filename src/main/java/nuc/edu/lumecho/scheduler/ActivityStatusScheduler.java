package nuc.edu.lumecho.scheduler;

import nuc.edu.lumecho.mapper.ActivityMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Component
public class ActivityStatusScheduler {

    @Resource
    private ActivityMapper activityMapper;

    @Scheduled(cron = "0 * * * * ?")
    public void autoUpdateActivityStatus() {
        try {
            LocalDateTime now = LocalDateTime.now();
            activityMapper.updateStatusToOngoing(now);
            activityMapper.updateStatusToEnded(now);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}