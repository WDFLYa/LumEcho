package nuc.edu.lumecho.service;

import nuc.edu.lumecho.model.entity.ChallengeApplication;
import nuc.edu.lumecho.model.vo.ChallengeApplicationVO;

import java.util.List;

public interface ChallengeApplicationService {

    /**
     * 创建挑战赛报名申请
     * @param challengeId 挑战赛ID
     */
    void createChallengeApplication(Long challengeId);

    /**
     * 取消报名
     * @param applicationId 申请记录ID
     */
    void cancelApplication(Long applicationId);

    /**
     * 审核通过
     * @param applicationId 申请记录ID
     */
    void approveApplication(Long applicationId);

    /**
     * 审核拒绝
     * @param applicationId 申请记录ID
     */
    void rejectApplication(Long applicationId);

    /**
     * 获取所有报名记录
     */
    List<ChallengeApplication> listAll();

    Integer getUserApplyStatus(Long challengeId);

    ChallengeApplication getByChallengeAndUser(Long challengeId, Long userId);

    boolean checkHasSubmittedWork(Long challengeId);

    List<ChallengeApplicationVO> getByChallengeId(Long challengeId);

    void rejectWithRemark(Long id, String remark);
}