package nuc.edu.lumecho.service;

import nuc.edu.lumecho.model.entity.ActivityApplication;

import java.util.List;

public interface ActivityApplicationService {
    void createActivityApplication(Long activityId);

    void cancelApplication(Long applicationId);

    void approveApplication(Long applicationId);

    void rejectApplication(Long applicationId);

    List<ActivityApplication> listAll();
}
