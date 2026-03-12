package nuc.edu.lumecho.service;

public interface ActivityApplicationService {
    void createActivityApplication(Long activityId);

    void cancelApplication(Long applicationId);

    void approveApplication(Long applicationId);

    void rejectApplication(Long applicationId);
}
