package nuc.edu.lumecho.mapper;

import nuc.edu.lumecho.model.entity.ActivityCheckin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ActivityCheckinMapper {

    @Insert("INSERT INTO activity_checkin(activity_id, user_id, latitude, longitude, address) " +
            "VALUES(#{activityId}, #{userId}, #{latitude}, #{longitude}, #{address})")
    int insert(ActivityCheckin checkin);

    @Select("SELECT COUNT(*) FROM activity_checkin WHERE activity_id=#{aid} AND user_id=#{uid}")
    int exists(@Param("aid") Long activityId, @Param("uid") Long userId);
}