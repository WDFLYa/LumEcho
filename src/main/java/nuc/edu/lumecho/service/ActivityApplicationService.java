package nuc.edu.lumecho.service;

import nuc.edu.lumecho.model.entity.ActivityApplication;
import nuc.edu.lumecho.model.entity.PhotographyActivity;
import nuc.edu.lumecho.model.vo.ActivityApplicationVO;

import java.util.List;

public interface ActivityApplicationService {
    void createActivityApplication(Long activityId);

    void createActivityApplicationDirect(Long activityId);

    void cancelApplication(Long applicationId);

    void approveApplication(Long applicationId);

    void rejectApplication(Long applicationId);

    List<ActivityApplication> listAll();

    ActivityApplication getMyApplicationStatus(Long activityId);

    List<ActivityApplication> listByActivityId(Long activityId);

    List<PhotographyActivity> getPendingActivityList();

    List<ActivityApplicationVO> getApplyListWithUserInfo(Long activityId);

    void adminApprove(Long applicationId);

    void adminReject(Long applicationId);

}
