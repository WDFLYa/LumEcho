package nuc.edu.lumecho.mapper;

import nuc.edu.lumecho.model.dto.response.challenge.ChallengeSubmissionItemResponse;
import nuc.edu.lumecho.model.entity.ChallengeSubmission;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChallengeSubmissionMapper {

    @Insert("INSERT INTO challenge_submission(application_id, title, content, location, final_score, status, submit_time, update_time) " +
            "VALUES(#{applicationId}, #{title}, #{content}, #{location}, #{finalScore}, #{status}, #{submitTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(ChallengeSubmission challengeSubmission);

    List<ChallengeSubmissionItemResponse> selectSubmissionList(
            @Param("challengeId") Long challengeId
    );

    @Select("SELECT COUNT(*) > 0 " +
            "FROM challenge_submission s " +
            "JOIN challenge_application a ON s.application_id = a.id " +
            "WHERE a.user_id = #{userId} " +
            "AND a.challenge_id = #{challengeId}")
    boolean existsByUserIdAndChallengeId(
            @Param("userId") Long userId,
            @Param("challengeId") Long challengeId
    );

    @Select("SELECT s.* FROM challenge_submission s " +
            "JOIN challenge_application a ON s.application_id = a.id " +
            "WHERE a.challenge_id = #{challengeId}")
    List<ChallengeSubmission> selectByChallengeId(@Param("challengeId") Long challengeId);

    @Update("UPDATE challenge_submission " +
            "SET final_score = #{finalScore}, update_time = NOW() " +
            "WHERE id = #{id}")
    void updateFinalScore(ChallengeSubmission submission);
}
