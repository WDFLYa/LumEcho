package nuc.edu.lumecho.service.impl;

import nuc.edu.lumecho.common.Enum.ActivityApplicationStatusEnum;
import nuc.edu.lumecho.common.WdfUserContext;
import nuc.edu.lumecho.mapper.ActivityApplicationMapper;
import nuc.edu.lumecho.model.entity.ActivityApplication;
import nuc.edu.lumecho.service.ActivityApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityApplicationImpl implements ActivityApplicationService {

    @Autowired
    private ActivityApplicationMapper activityApplicationMapper;

    @Override
    public void createActivityApplication(Long activityId) {
        ActivityApplication activityApplication = new ActivityApplication();
        activityApplication.setActivityId(activityId);
        activityApplication.setUserId(WdfUserContext.getCurrentUserId());
        activityApplication.setStatus(ActivityApplicationStatusEnum.PENDING.getCode());
        activityApplicationMapper.insert(activityApplication);
    }

    @Override
    public void createActivityApplicationDirect(Long activityId) {
        ActivityApplication activityApplication = new ActivityApplication();
        activityApplication.setActivityId(activityId);
        activityApplication.setUserId(WdfUserContext.getCurrentUserId());
        activityApplication.setStatus(ActivityApplicationStatusEnum.APPROVED.getCode());
        activityApplicationMapper.insert(activityApplication);

        // 免审核直接通过 → 人数+1
        activityApplicationMapper.incrementCurrentParticipants(activityId);
    }

    @Override
    public void cancelApplication(Long applicationId) {
        // 先查询报名信息
        ActivityApplication app = activityApplicationMapper.selectById(applicationId);

        // 如果是已通过状态，取消时人数-1
        if (app != null && app.getStatus() == 1) {
            activityApplicationMapper.decrementCurrentParticipants(app.getActivityId());
        }

        activityApplicationMapper.updateStatus(applicationId, ActivityApplicationStatusEnum.CANCELLED.getCode());
    }

    @Override
    public void approveApplication(Long applicationId) {
        ActivityApplication app = activityApplicationMapper.selectById(applicationId);
        if (app != null) {
            activityApplicationMapper.incrementCurrentParticipants(app.getActivityId());
        }

        activityApplicationMapper.updateStatus(applicationId, ActivityApplicationStatusEnum.APPROVED.getCode());
    }

    @Override
    public void rejectApplication(Long applicationId) {
        activityApplicationMapper.updateStatus(applicationId, ActivityApplicationStatusEnum.REJECTED.getCode());
    }

    @Override
    public List<ActivityApplication> listAll() {
        return activityApplicationMapper.listAll();
    }

    @Override
    public ActivityApplication getMyApplicationStatus(Long activityId) {
        Long userId = WdfUserContext.getCurrentUserId();
        return activityApplicationMapper.getByActivityAndUser(activityId, userId);
    }

    @Override
    public List<ActivityApplication> listByActivityId(Long activityId) {
        return activityApplicationMapper.listByActivityId(activityId);
    }
}