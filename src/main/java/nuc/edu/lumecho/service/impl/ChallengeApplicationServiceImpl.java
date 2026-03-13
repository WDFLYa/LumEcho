package nuc.edu.lumecho.service.impl;

import nuc.edu.lumecho.common.Enum.ChallengeApplicationStatusEnum;
import nuc.edu.lumecho.common.WdfUserContext;
import nuc.edu.lumecho.mapper.ChallengeApplicationMapper;
import nuc.edu.lumecho.model.entity.ChallengeApplication;
import nuc.edu.lumecho.service.ChallengeApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChallengeApplicationServiceImpl implements ChallengeApplicationService {

    @Autowired
    private ChallengeApplicationMapper challengeApplicationMapper;

    @Override
    public void createChallengeApplication(Long challengeId) {
        ChallengeApplication application = new ChallengeApplication();
        application.setChallengeId(challengeId);
        // 从上下文获取当前登录用户ID
        application.setUserId(WdfUserContext.getCurrentUserId());
        // 默认状态：待审核 (0)
        application.setStatus(ChallengeApplicationStatusEnum.PENDING.getCode());

        challengeApplicationMapper.insert(application);
    }

    @Override
    public void cancelApplication(Long applicationId) {
        challengeApplicationMapper.updateStatus(applicationId, ChallengeApplicationStatusEnum.CANCELLED.getCode());
    }

    @Override
    public void approveApplication(Long applicationId) {
        challengeApplicationMapper.updateStatus(applicationId, ChallengeApplicationStatusEnum.APPROVED.getCode());
    }

    @Override
    public void rejectApplication(Long applicationId) {
        challengeApplicationMapper.updateStatus(applicationId, ChallengeApplicationStatusEnum.REJECTED.getCode());
    }

    @Override
    public List<ChallengeApplication> listAll() {
        return challengeApplicationMapper.listAll();
    }
}