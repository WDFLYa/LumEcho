package nuc.edu.lumecho.service.impl;

import nuc.edu.lumecho.common.Enum.ChallengeStatus;
import nuc.edu.lumecho.common.Enum.ResourceTypeEnum;
import nuc.edu.lumecho.common.Enum.ResultCodeEnum;
import nuc.edu.lumecho.common.WdfUserContext;
import nuc.edu.lumecho.common.exception.BusinessException;
import nuc.edu.lumecho.mapper.ChallengeMapper;
import nuc.edu.lumecho.mapper.ResourceFileMapper;
import nuc.edu.lumecho.mapper.UserMapper;
import nuc.edu.lumecho.model.dto.request.challenge.ChallengeCreateRequest;
import nuc.edu.lumecho.model.dto.response.challenge.ChallengeListItemResponse;
import nuc.edu.lumecho.model.dto.response.challenge.ChallengeListResponse;
import nuc.edu.lumecho.model.entity.Challenge;
import nuc.edu.lumecho.service.ChallengeService;
import org.checkerframework.checker.units.qual.Acceleration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChallengeServiceImpl implements ChallengeService {

    @Autowired
    private ChallengeMapper challengeMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ResourceFileMapper resourceFileMapper;
    @Override
    public void createChallenge(ChallengeCreateRequest challengeCreateRequest) {

        String role = userMapper.getUserRoleById(WdfUserContext.getCurrentUserId());

        if (!"ADMIN".equals(role) && !"PHOTOGRAPHER".equals(role)) {
            throw new BusinessException(ResultCodeEnum.CHALLENGE_PUBLISH_FORBIDDEN);
        }

        Challenge challenge = new Challenge();
        challenge.setTitle(challengeCreateRequest.getTitle());
        challenge.setDescription(challengeCreateRequest.getDescription());
        challenge.setStartTime(challengeCreateRequest.getStartTime());
        challenge.setEndTime(challengeCreateRequest.getEndTime());
        challenge.setReviewEndTime(challengeCreateRequest.getReviewEndTime());
        challenge.setMaxParticipants(challengeCreateRequest.getMaxParticipants());
        challenge.setParticipantCount(0);
        challenge.setStatus(ChallengeStatus.NOT_STARTED.getCode());
        challenge.setRules(challengeCreateRequest.getRules());
        challenge.setPrizes(challengeCreateRequest.getPrizes());
        challenge.setCreatedAt(LocalDateTime.now());
        challenge.setUpdatedAt(LocalDateTime.now());
        challengeMapper.createChallenge(challenge);
        resourceFileMapper.bindBizIdByUrl(challengeCreateRequest.getCoverUrl(), ResourceTypeEnum.CHALLENGE_COVER.getCode(), challenge.getId());
    }

    @Override
    public ChallengeListResponse getChallengeList(Integer status, String keyword, int pageNum, int pageSize) {

        int offset = (pageNum - 1) * pageSize;

        List<Challenge> list = challengeMapper.selectChallengeList(status, keyword, offset, pageSize);

        int total = challengeMapper.countChallengeList(status, keyword);

        List<ChallengeListItemResponse> responseList = new ArrayList<>();

        for (Challenge challenge : list) {

            ChallengeListItemResponse item = new ChallengeListItemResponse();

            item.setId(challenge.getId());
            item.setTitle(challenge.getTitle());
            item.setDescription(challenge.getDescription());

            item.setStartTime(challenge.getStartTime());
            item.setEndTime(challenge.getEndTime());
            item.setReviewEndTime(challenge.getReviewEndTime());

            Integer max = challenge.getMaxParticipants();

            if (max == null || max <= 0) {
                item.setMaxParticipants(null);
            } else {
                item.setMaxParticipants(max);
            }

            item.setParticipantCount(challenge.getParticipantCount());

            // 状态
            item.setStatusCode(challenge.getStatus());
            item.setStatusText(getStatusText(challenge.getStatus()));

            // 进度
            item.setProgressPercent(calcProgress(
                    challenge.getParticipantCount(),
                    challenge.getMaxParticipants()
            ));

            // 剩余时间
            item.setRemainingTimeDesc(calcRemainingTime(challenge.getEndTime()));

            // 是否紧急
            item.setIsUrgent(isUrgent(challenge.getEndTime()));

            // ==========================================
            // ✨ 新增：计算友好提示语和状态标记
            // ==========================================
            setFriendlyFields(item, challenge.getParticipantCount(), challenge.getMaxParticipants());
            // ==========================================

            responseList.add(item);
        }

        ChallengeListResponse response = new ChallengeListResponse();

        response.setData(responseList);
        response.setTotal((long) total);
        response.setHasMore(offset + pageSize < total);

        return response;
    }

    @Override
    public Challenge getChallengeDetail(Long id) {
        return challengeMapper.selectById(id);
    }

    /**
     * 状态文本
     */
    private String getStatusText(Integer status) {

        if (status == null) return "未知";

        switch (status) {
            case 0:
                return "未开始";
            case 1:
                return "报名中";
            case 2:
                return "评审中";
            case 3:
                return "已结束";
            default:
                return "未知";
        }
    }

    /**
     * 计算进度
     */
    private Integer calcProgress(Integer current, Integer max) {

        // 无限人数 或 不合法人数
        if (max == null || max <= 0) {
            return 0;
        }

        if (current == null || current <= 0) {
            return 0;
        }

        int percent = (int) ((current * 100.0) / max);

        // 防止超过100
        return Math.min(percent, 100);
    }

    /**
     * 剩余时间描述
     */
    private String calcRemainingTime(LocalDateTime endTime) {

        if (endTime == null) {
            return "未知";
        }

        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(endTime)) {
            return "已结束";
        }

        Duration duration = Duration.between(now, endTime);

        long days = duration.toDays();
        long hours = duration.toHours() % 24;

        if (days > 0) {
            return "剩 " + days + "天" + hours + "小时";
        }

        return "剩 " + hours + "小时";
    }

    /**
     * 是否紧急 (<3天)
     */
    private Boolean isUrgent(LocalDateTime endTime) {

        if (endTime == null) {
            return false;
        }

        Duration duration = Duration.between(LocalDateTime.now(), endTime);

        return duration.toDays() <= 3;
    }

    // ==========================================
    // ✨ 新增辅助方法：生成友好提示语
    // ==========================================

    /**
     * 设置友好提示语、是否满员、是否无限制字段
     */
    private void setFriendlyFields(ChallengeListItemResponse item, Integer current, Integer max) {
        boolean isUnlimited = (max == null || max <= 0);
        boolean isFull = !isUnlimited && current != null && max != null && current >= max;

        // 设置布尔标记供前端判断样式
        item.setIsUnlimited(isUnlimited);
        item.setIsFull(isFull);

        String hint;

        if (isUnlimited) {
            hint = "🌟 无人数限制，随时加入！";
        } else if (isFull) {
            hint = "✅ 名额已满，感谢关注";
        } else {
            // 确保不为空再计算
            int safeCurrent = (current == null) ? 0 : current;
            int safeMax = (max == null) ? 0 : max;
            int remaining = safeMax - safeCurrent;
            double ratio = (double) safeCurrent / safeMax;

            if (remaining <= 3) {
                hint = "🔥 仅剩 " + remaining + " 席，手慢无！";
            } else if (ratio >= 0.8) {
                hint = "⚡ 热度飙升，还差 " + remaining + " 人满员！";
            } else if (ratio >= 0.5) {
                hint = "👥 已有 " + safeCurrent + " 人加入，快来组队！";
            } else {
                hint = "🚀 招募进行中，还差 " + remaining + " 人满员";
            }
        }

        item.setFriendlyHint(hint);
    }
}