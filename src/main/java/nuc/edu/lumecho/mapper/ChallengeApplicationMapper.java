package nuc.edu.lumecho.mapper;

import nuc.edu.lumecho.model.entity.ChallengeApplication;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChallengeApplicationMapper {
    @Insert("INSERT INTO challenge_application(challenge_id, user_id, status) VALUES(#{challengeId}, #{userId}, #{status})")
    void insert(ChallengeApplication challengeApplication);

    void updateStatus(Long id, int status);

    @Select("SELECT * FROM challenge_application")
    List<ChallengeApplication> listAll();
}
