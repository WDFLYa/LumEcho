package nuc.edu.lumecho.service.impl;

import nuc.edu.lumecho.common.Enum.ChallengeApplicationStatusEnum;
import nuc.edu.lumecho.common.WdfUserContext;
import nuc.edu.lumecho.mapper.ChallengeApplicationMapper;
import nuc.edu.lumecho.mapper.ChallengeMapper;
import nuc.edu.lumecho.mapper.ChallengeSubmissionMapper;
import nuc.edu.lumecho.model.entity.ChallengeApplication;
import nuc.edu.lumecho.model.vo.ChallengeApplicationVO;
import nuc.edu.lumecho.service.ChallengeApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChallengeApplicationServiceImpl implements ChallengeApplicationService {

    @Autowired
    private ChallengeApplicationMapper challengeApplicationMapper;

    @Autowired
    private ChallengeMapper challengeMapper;

    @Autowired
    private ChallengeSubmissionMapper challengeSubmissionMapper;

    // ===================== 你原来的方法，我帮你修复 =====================
    @Override
    public void createChallengeApplication(Long challengeId) {
        Long userId = WdfUserContext.getCurrentUserId();

        // ========== 修复：判断是否已经报名 ==========
        ChallengeApplication exist = challengeApplicationMapper.selectByChallengeAndUser(challengeId, userId);
        if (exist != null) {
            Integer status = exist.getStatus();
            if (status == ChallengeApplicationStatusEnum.PENDING.getCode()) {
                throw new RuntimeException("已报名，待审核");
            }
            if (status == ChallengeApplicationStatusEnum.APPROVED.getCode()) {
                throw new RuntimeException("已报名成功");
            }
            // 如果是拒绝/取消 → 重新报名
            exist.setStatus(ChallengeApplicationStatusEnum.PENDING.getCode());
            challengeApplicationMapper.updateStatus(exist.getId(), exist.getStatus());
            return;
        }

        // 你原来的逻辑
        ChallengeApplication application = new ChallengeApplication();
        application.setChallengeId(challengeId);
        application.setUserId(userId);
        application.setStatus(ChallengeApplicationStatusEnum.PENDING.getCode());

        challengeApplicationMapper.insert(application);

    }

    // 你原来的
    @Override
    public void cancelApplication(Long applicationId) {
        challengeApplicationMapper.updateStatus(applicationId, ChallengeApplicationStatusEnum.CANCELLED.getCode());
    }

    // 你原来的
    @Override
    public void approveApplication(Long applicationId) {
        challengeApplicationMapper.updateStatus(applicationId, ChallengeApplicationStatusEnum.APPROVED.getCode());
    }

    // 你原来的
    @Override
    public void rejectApplication(Long applicationId) {
        challengeApplicationMapper.updateStatus(applicationId, ChallengeApplicationStatusEnum.REJECTED.getCode());
    }

    // 你原来的
    @Override
    public List<ChallengeApplication> listAll() {
        return challengeApplicationMapper.listAll();
    }

    // ===================== 我新增的：获取用户报名状态 =====================
    @Override
    public Integer getUserApplyStatus(Long challengeId) {
        Long userId = WdfUserContext.getCurrentUserId();
        if (userId == null) {
            return -1; // 未登录
        }

        ChallengeApplication app = challengeApplicationMapper.selectByChallengeAndUser(challengeId, userId);
        if (app == null) {
            return -2; // 未报名
        }

        return app.getStatus();
    }

    @Override
    public ChallengeApplication getByChallengeAndUser(Long challengeId, Long userId) {
        return challengeApplicationMapper.getByChallengeAndUser(challengeId, userId);
    }

    @Override
    public boolean checkHasSubmittedWork(Long challengeId) {
        Long userId = WdfUserContext.getCurrentUserId();
        return challengeSubmissionMapper.existsByUserIdAndChallengeId(userId, challengeId);
    }

    @Override
    public List<ChallengeApplicationVO> getByChallengeId(Long challengeId) {
        return challengeApplicationMapper.listByChallengeIdWithUser(challengeId);
    }

    @Override
    public void rejectWithRemark(Long id, String remark) {
        ChallengeApplication application = new ChallengeApplication();
        application.setId(id);
        application.setStatus(ChallengeApplicationStatusEnum.REJECTED.getCode());
        application.setRemark(remark);
        challengeApplicationMapper.updateById(application);
    }
}