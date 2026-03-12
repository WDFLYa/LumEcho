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
    public void cancelApplication(Long applicationId) {
        activityApplicationMapper.updateStatus(applicationId,ActivityApplicationStatusEnum.CANCELLED.getCode());
    }

    @Override
    public void approveApplication(Long applicationId) {
        activityApplicationMapper.updateStatus(applicationId,ActivityApplicationStatusEnum.APPROVED.getCode());
    }

    @Override
    public void rejectApplication(Long applicationId) {
        activityApplicationMapper.updateStatus(applicationId,ActivityApplicationStatusEnum.REJECTED.getCode());
    }

    @Override
    public List<ActivityApplication> listAll() {
        return activityApplicationMapper.listAll();
    }
}
