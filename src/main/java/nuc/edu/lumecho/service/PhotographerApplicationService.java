package nuc.edu.lumecho.service;

import nuc.edu.lumecho.model.dto.request.photographer.PhotographerApplyRequest;
import nuc.edu.lumecho.model.entity.PhotographerApplication;

import java.util.List;

public interface PhotographerApplicationService {
    void insertPhotographerApply(PhotographerApplyRequest request);
    PhotographerApplication getUserApply();

    List<PhotographerApplication> getAllApplicationsForAdmin();
    void reviewApplication(Long id, Integer status, String rejectReason, Long reviewerId);
}
