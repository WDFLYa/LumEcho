package nuc.edu.lumecho.mapper;

import nuc.edu.lumecho.model.entity.ActivityApplication;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ActivityApplicationMapper {
    @Insert("insert into activity_application(activity_id, user_id, status) values(#{activityId}, #{userId}, #{status})")
    void insert(ActivityApplication activityApplication);

    void updateStatus(Long id,int status);
}
