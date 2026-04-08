package nuc.edu.lumecho.scheduler;

import nuc.edu.lumecho.common.Enum.ChallengeStatus;
import nuc.edu.lumecho.mapper.ChallengeMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Component
public class ChallengeStatusScheduler {

    @Resource
    private ChallengeMapper challengeMapper;

    /**
     * 每天凌晨 00:00 自动更新比赛状态
     * 0=未开始  1=进行中  2=评审中  3=已结束
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void autoUpdateChallengeStatus() {
        LocalDateTime now = LocalDateTime.now();

        // 1. 未开始 → 进行中
        challengeMapper.updateStatusByTime(
                ChallengeStatus.NOT_STARTED.getCode(),
                ChallengeStatus.IN_PROGRESS.getCode(),
                "start_time", now
        );

        // 2. 进行中 → 评审中
        challengeMapper.updateStatusByTime(
                ChallengeStatus.IN_PROGRESS.getCode(),
                ChallengeStatus.REVIEWING.getCode(),
                "end_time", now
        );

        // 3. 评审中 → 已结束
        challengeMapper.updateStatusByTime(
                ChallengeStatus.REVIEWING.getCode(),
                ChallengeStatus.ENDED.getCode(),
                "review_end_time", now
        );
    }
}