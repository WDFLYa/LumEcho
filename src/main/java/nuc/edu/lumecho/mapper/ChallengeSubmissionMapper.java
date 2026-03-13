package nuc.edu.lumecho.mapper;

import nuc.edu.lumecho.model.entity.ChallengeSubmission;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface ChallengeSubmissionMapper {

    @Insert("INSERT INTO challenge_submission(application_id, title, content, location, final_score, status, submit_time, update_time) " +
            "VALUES(#{applicationId}, #{title}, #{content}, #{location}, #{finalScore}, #{status}, #{submitTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(ChallengeSubmission challengeSubmission);
}
