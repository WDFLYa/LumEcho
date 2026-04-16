package nuc.edu.lumecho.service.impl;

import nuc.edu.lumecho.common.Enum.ActivityStatusEnum;
import nuc.edu.lumecho.common.Enum.ResourceTypeEnum;
import nuc.edu.lumecho.common.Enum.ResultCodeEnum;
import nuc.edu.lumecho.common.Enum.UserRoleEnum;
import nuc.edu.lumecho.common.WdfUserContext;
import nuc.edu.lumecho.common.exception.BusinessException;
import nuc.edu.lumecho.common.util.GaodeUtil;
import nuc.edu.lumecho.mapper.ActivityMapper;
import nuc.edu.lumecho.mapper.ResourceFileMapper;
import nuc.edu.lumecho.mapper.UserMapper;
import nuc.edu.lumecho.model.dto.request.photographer.CreateActivityRequest;
import nuc.edu.lumecho.model.dto.response.activity.PhotographyActivityListResponse;
import nuc.edu.lumecho.model.entity.PhotographyActivity;
import nuc.edu.lumecho.model.entity.ResourceFile;
import nuc.edu.lumecho.service.ActivityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ResourceFileMapper resourceFileMapper;
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
        photographyActivity.setRequireAudit(createActivityRequest.getRequireAudit() ? 1 : 0);
        photographyActivity.setPhotographerId(WdfUserContext.getCurrentUserId());
        photographyActivity.setStatus(ActivityStatusEnum.PENDING.getCode());

        double[] ll = GaodeUtil.getLocationByAddress(createActivityRequest.getLocation());
        photographyActivity.setLatitude(ll[0]);
        photographyActivity.setLongitude(ll[1]);

        activityMapper.insert(photographyActivity);

        resourceFileMapper.bindBizIdByUrl(
                createActivityRequest.getCoverUrl(),
                ResourceTypeEnum.ACTIVITY_COVER.getCode(),
                photographyActivity.getId()
        );
    }

    @Override
    public List<PhotographyActivityListResponse> getActivityList() {
        List<PhotographyActivity> activityList = activityMapper.selectAllActivity();

        return activityList.stream().map(activity -> {
            PhotographyActivityListResponse resp = new PhotographyActivityListResponse();

            resp.setId(activity.getId());
            resp.setTitle(activity.getTitle());
            resp.setDescription(activity.getDescription());
            resp.setStartTime(activity.getStartTime());
            resp.setEndTime(activity.getEndTime());
            resp.setLocation(activity.getLocation());
            resp.setMaxParticipants(activity.getMaxParticipants());
            resp.setCurrentParticipants(activity.getCurrentParticipants());
            resp.setPhotographerId(activity.getPhotographerId());
            resp.setStatus(activity.getStatus());
            resp.setRequireAudit(activity.getRequireAudit());

            // 封面
            List<ResourceFile> covers = resourceFileMapper.selectByBizIdAndTypes(
                    activity.getId(),
                    Collections.singletonList(ResourceTypeEnum.ACTIVITY_COVER.getCode())
            );
            if (!CollectionUtils.isEmpty(covers)) {
                resp.setCoverUrl(covers.get(0).getFileUrl());
            }

            return resp;
        }).collect(Collectors.toList());
    }

    @Override
    public PhotographyActivityListResponse getActivityDetail(Long id) {
        PhotographyActivity activity = activityMapper.selectActivityById(id);

        return convertResponse(activity);
    }

    /**
     * 抽取公共转换方法（列表+详情共用）
     */
    private PhotographyActivityListResponse convertResponse(PhotographyActivity activity) {
        PhotographyActivityListResponse resp = new PhotographyActivityListResponse();
        BeanUtils.copyProperties(activity, resp);

        // 封面图
        List<ResourceFile> covers = resourceFileMapper.selectByBizIdAndTypes(
                activity.getId(),
                Collections.singletonList(ResourceTypeEnum.ACTIVITY_COVER.getCode())
        );
        if (!CollectionUtils.isEmpty(covers)) {
            resp.setCoverUrl(covers.get(0).getFileUrl());
        }

        return resp;
    }


}
