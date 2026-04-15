package nuc.edu.lumecho.service;

import nuc.edu.lumecho.model.dto.request.photographer.CreateActivityRequest;
import nuc.edu.lumecho.model.dto.response.activity.PhotographyActivityListResponse;
import nuc.edu.lumecho.model.entity.PhotographyActivity;

import java.util.List;

public interface ActivityService {
    void createActivity(CreateActivityRequest request);
    List<PhotographyActivityListResponse> getActivityList();

    PhotographyActivityListResponse getActivityDetail(Long id);
}
