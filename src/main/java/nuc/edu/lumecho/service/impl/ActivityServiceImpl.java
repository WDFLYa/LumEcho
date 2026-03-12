package nuc.edu.lumecho.service.impl;

import nuc.edu.lumecho.common.Enum.ActivityStatusEnum;
import nuc.edu.lumecho.common.Enum.ResultCodeEnum;
import nuc.edu.lumecho.common.Enum.UserRoleEnum;
import nuc.edu.lumecho.common.WdfUserContext;
import nuc.edu.lumecho.common.exception.BusinessException;
import nuc.edu.lumecho.mapper.ActivityMapper;
import nuc.edu.lumecho.mapper.UserMapper;
import nuc.edu.lumecho.model.dto.request.photographer.CreateActivityRequest;
import nuc.edu.lumecho.model.entity.PhotographyActivity;
import nuc.edu.lumecho.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private UserMapper userMapper;
    @Override
    public void createActivity(CreateActivityRequest createActivityRequest) {

        String role = userMapper.getUserRoleById(WdfUserContext.getCurrentUserId());
        if (!role.equals(UserRoleEnum.PHOTOGRAPHER.getCode())) {
            throw new BusinessException(ResultCodeEnum.NOT_PHOTOGRAPHER);
        }
        PhotographyActivity photographyActivity = new PhotographyActivity();
        photographyActivity.setTitle(createActivityRequest.getTitle());
        photographyActivity.setDescription(createActivityRequest.getDescription());
        photographyActivity.setStartTime(createActivityRequest.getStartTime());
        photographyActivity.setEndTime(createActivityRequest.getEndTime());
        photographyActivity.setLocation(createActivityRequest.getLocation());
        photographyActivity.setMaxParticipants(createActivityRequest.getMaxParticipants());
        photographyActivity.setCurrentParticipants(0);
        photographyActivity.setRequireAudit(createActivityRequest.getRequireAudit());
        photographyActivity.setPhotographerId(WdfUserContext.getCurrentUserId());
        photographyActivity.setStatus(ActivityStatusEnum.PENDING.getCode());
        activityMapper.insert(photographyActivity);
    }
}
