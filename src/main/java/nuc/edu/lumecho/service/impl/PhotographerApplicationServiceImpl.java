package nuc.edu.lumecho.service.impl;

import nuc.edu.lumecho.common.Enum.PhotographerApplyStatusEnum;
import nuc.edu.lumecho.common.Enum.ResultCodeEnum;
import nuc.edu.lumecho.common.WdfUserContext;
import nuc.edu.lumecho.common.exception.BusinessException;
import nuc.edu.lumecho.mapper.PhotographerApplicationMapper;
import nuc.edu.lumecho.model.dto.request.photographer.PhotographerApplyRequest;
import nuc.edu.lumecho.model.entity.PhotographerApplication;
import nuc.edu.lumecho.service.PhotographerApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PhotographerApplicationServiceImpl implements PhotographerApplicationService {

    @Autowired
    private PhotographerApplicationMapper photographerApplicationMapper;

    @Override
    public void insertPhotographerApply(PhotographerApplyRequest request) {
        if (photographerApplicationMapper.countByUserId(WdfUserContext.getCurrentUserId()) > 0) {
            throw new BusinessException(ResultCodeEnum.PHOTOGRAPHER_APPLY_EXISTS);
        }
        PhotographerApplication photographerApplication = new PhotographerApplication();
        photographerApplication.setUserId(WdfUserContext.getCurrentUserId());
        photographerApplication.setDescription(request.getDescription());
        photographerApplication.setStatus(PhotographerApplyStatusEnum.PENDING.getCode());
        photographerApplication.setApplyTime(LocalDateTime.now());
        photographerApplicationMapper.insert(photographerApplication);
    }
}
