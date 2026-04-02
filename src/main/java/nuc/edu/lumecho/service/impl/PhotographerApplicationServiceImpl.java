package nuc.edu.lumecho.service.impl;

import nuc.edu.lumecho.common.Enum.PhotographerApplyStatusEnum;
import nuc.edu.lumecho.common.Enum.ResultCodeEnum;
import nuc.edu.lumecho.common.WdfUserContext;
import nuc.edu.lumecho.common.exception.BusinessException;
import nuc.edu.lumecho.mapper.PhotographerApplicationMapper;
import nuc.edu.lumecho.mapper.UserMapper;
import nuc.edu.lumecho.model.dto.request.photographer.PhotographerApplyRequest;
import nuc.edu.lumecho.model.entity.PhotographerApplication;
import nuc.edu.lumecho.service.PhotographerApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PhotographerApplicationServiceImpl implements PhotographerApplicationService {

    @Autowired
    private PhotographerApplicationMapper photographerApplicationMapper;

    @Autowired
    private UserMapper userMapper;

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

    // ✅ 实现：获取所有申请
    @Override
    public List<PhotographerApplication> getAllApplicationsForAdmin() {
        return photographerApplicationMapper.selectAllApplications();
    }

    // ✅ 实现：审核申请
    @Override
    public void reviewApplication(Long id, Integer status, String rejectReason, Long reviewerId) {
        PhotographerApplication app = new PhotographerApplication();
        app.setId(id);
        app.setStatus(status);
        app.setRejectReason(rejectReason); // 如果通过，这里传 null
        app.setReviewTime(LocalDateTime.now());
        app.setReviewerId(reviewerId);

        photographerApplicationMapper.updateStatusById(app);

        if (status == 1) {
            userMapper.updateRoleByUserId(app.getUserId(), "PHOTOGRAPHER");
        }
    }

}
