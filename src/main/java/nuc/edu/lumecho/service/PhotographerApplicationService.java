package nuc.edu.lumecho.service;

import nuc.edu.lumecho.model.dto.request.photographer.PhotographerApplyRequest;
import nuc.edu.lumecho.model.entity.PhotographerApplication;

public interface PhotographerApplicationService {
    void insertPhotographerApply(PhotographerApplyRequest request);
    PhotographerApplication getUserApply();
}
