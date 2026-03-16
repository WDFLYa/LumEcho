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

        PhotographerApplication application = new PhotographerApplication();
        application.setUserId(WdfUserContext.getCurrentUserId());
        application.setDescription(request.getDescription());
        application.setApplyTime(LocalDateTime.now());

        int count = photographerApplicationMapper.countByUserId(WdfUserContext.getCurrentUserId());

        if (count > 0) {
            // 已申请过 → 更新
            photographerApplicationMapper.updateApply(application);
        } else {
            // 第一次申请 → 插入
            photographerApplicationMapper.insert(application);
        }
    }

    @Override
    public PhotographerApplication getUserApply() {
        return photographerApplicationMapper.selectByUserId(WdfUserContext.getCurrentUserId());
    }

}
