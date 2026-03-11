package nuc.edu.lumecho.mapper;

import nuc.edu.lumecho.model.dto.request.photographer.PhotographerApplyRequest;
import nuc.edu.lumecho.model.entity.PhotographerApplication;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PhotographerApplicationMapper {

    @Insert("INSERT INTO photographer_application (user_id, description, apply_time) " +
            "VALUES (#{userId}, #{description}, #{applyTime})")
    void insert(PhotographerApplication photographerApplication);


    @Select("SELECT COUNT(1) FROM photographer_application WHERE user_id = #{userId}")
    int countByUserId(Long userId);
}
