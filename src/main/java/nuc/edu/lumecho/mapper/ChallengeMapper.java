package nuc.edu.lumecho.mapper;

import nuc.edu.lumecho.model.entity.Challenge;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ChallengeMapper {

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("""
        insert into challenge
        (title, description, rules, prizes,
         start_time, end_time, review_end_time,
         status, max_participants, participant_count,
         created_at, updated_at)
        values
        (#{title}, #{description}, #{rules}, #{prizes},
         #{startTime}, #{endTime}, #{reviewEndTime},
         #{status}, #{maxParticipants}, #{participantCount},
         #{createdAt}, #{updatedAt})
    """)
    void createChallenge(Challenge challenge);


    List<Challenge> selectChallengeList(
            @Param("status") Integer status,
            @Param("keyword") String keyword,
            @Param("offset") int offset,
            @Param("limit") int limit
    );

    String selectChallengeCoverByChallengeId(@Param("challengeId") Long challengeId);


    int countChallengeList(
            @Param("status") Integer status,
            @Param("keyword") String keyword
    );


    Challenge selectById(@Param("id") Long id);


    /**
     * 根据时间自动更新状态
     * @param oldStatus 原状态
     * @param newStatus 目标状态
     * @param timeField 时间字段：start_time / end_time / review_end_time
     * @param now 当前时间
     */
    @Update("UPDATE challenge " +
            "SET status = #{newStatus} " +
            "WHERE status = #{oldStatus} " +
            "AND status != 4 " +
            "AND ${timeField} <= #{now}")
    int updateStatusByTime(
            @Param("oldStatus") int oldStatus,
            @Param("newStatus") int newStatus,
            @Param("timeField") String timeField,
            @Param("now") LocalDateTime now
    );

    @Update("UPDATE challenge " +
            "SET status = 4 " +
            "WHERE id = #{id} " +
            "AND status IN (0,1,2)")
    int cancelChallenge(@Param("id") Long id);

    @Update("UPDATE challenge SET participant_count = participant_count + 1 WHERE id = #{id}")
    int increaseParticipantCount(@Param("id") Long id);

    List<Challenge> listByStatus(@Param("status") int status);
}