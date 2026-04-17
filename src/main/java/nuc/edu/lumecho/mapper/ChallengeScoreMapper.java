package nuc.edu.lumecho.mapper;

import nuc.edu.lumecho.model.entity.ChallengeScore;
import nuc.edu.lumecho.model.entity.ChallengeSubmission;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface ChallengeScoreMapper {

    // ====================== 插入评分（你要的 SQL）======================
    @Insert("INSERT INTO challenge_score(submission_id, judge_id, judge_name, score, comment) " +
            "VALUES(#{submissionId}, #{judgeId}, #{judgeName}, #{score}, #{comment})")
    void insert(ChallengeScore score);

    // 查询某个作品的所有评分
    @Select("SELECT * FROM challenge_score WHERE submission_id = #{submissionId}")
    List<ChallengeScore> selectBySubmissionId(@Param("submissionId") Long submissionId);

    // 查询某个评委是否已经给该作品打过分
    @Select("SELECT COUNT(*) > 0 FROM challenge_score " +
            "WHERE submission_id = #{submissionId} AND judge_id = #{judgeId}")
    boolean existsBySubmissionAndJudge(
            @Param("submissionId") Long submissionId,
            @Param("judgeId") Long judgeId
    );

    // 计算某个作品的平均分
    @Select("SELECT AVG(score) FROM challenge_score WHERE submission_id = #{submissionId}")
    BigDecimal selectAvgScore(@Param("submissionId") Long submissionId);

    @Update("UPDATE challenge_submission " +
            "SET final_score = #{finalScore}, update_time = NOW() " +
            "WHERE id = #{id}")
    void updateFinalScore(ChallengeSubmission submission);
}