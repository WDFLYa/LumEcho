package nuc.edu.lumecho.mapper;

import nuc.edu.lumecho.model.entity.ActivityApplication;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ActivityApplicationMapper {
    @Insert("insert into activity_application(activity_id, user_id, status) values(#{activityId}, #{userId}, #{status})")
    void insert(ActivityApplication activityApplication);

    void updateStatus(Long id,int status);

    @Select("select * from activity_application")
    List<ActivityApplication> listAll();
}
