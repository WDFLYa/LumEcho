package nuc.edu.lumecho.mapper;

import nuc.edu.lumecho.model.entity.ActivityApplication;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ActivityApplicationMapper {

    @Insert("insert into activity_application(activity_id, user_id, status) values(#{activityId}, #{userId}, #{status})")
    void insert(ActivityApplication activityApplication);

    void updateStatus(@Param("id") Long id, @Param("status") int status);

    @Select("select * from activity_application")
    List<ActivityApplication> listAll();

    @Select("SELECT * FROM activity_application WHERE activity_id = #{activityId} AND user_id = #{userId}")
    ActivityApplication getByActivityAndUser(@Param("activityId") Long activityId, @Param("userId") Long userId);

    @Select("SELECT * FROM activity_application WHERE activity_id = #{activityId}")
    List<ActivityApplication> listByActivityId(@Param("activityId") Long activityId);

    @Update("UPDATE photography_activity SET current_participants = current_participants + 1 WHERE id = #{activityId}")
    void incrementCurrentParticipants(@Param("activityId") Long activityId);

    @Update("UPDATE photography_activity SET current_participants = current_participants - 1 WHERE id = #{activityId}")
    void decrementCurrentParticipants(@Param("activityId") Long activityId);

    @Select("SELECT * FROM activity_application WHERE id = #{id}")
    ActivityApplication selectById(@Param("id") Long id);
}