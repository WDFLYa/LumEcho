package nuc.edu.lumecho.mapper;

import nuc.edu.lumecho.model.entity.Challenge;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChallengeMapper {

    @Insert("insert into challenge(title, description, start_time, end_time, review_end_time, status, max_participants, participant_count, created_at, updated_at) values(#{title}, #{description}, #{startTime}, #{endTime}, #{reviewEndTime}, #{status}, #{maxParticipants}, #{participantCount}, #{createdAt}, #{updatedAt})")
    void createChallenge(Challenge challenge);

    List<Challenge> selectChallengeList(@Param("status") Integer status);

}
