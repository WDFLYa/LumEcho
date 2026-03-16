package nuc.edu.lumecho.mapper;

import nuc.edu.lumecho.model.dto.request.photographer.PhotographerApplyRequest;
import nuc.edu.lumecho.model.entity.PhotographerApplication;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PhotographerApplicationMapper {

    @Insert("""
    INSERT INTO photographer_application (user_id, description, apply_time)
    VALUES (#{userId}, #{description}, #{applyTime})
    """)
    void insert(PhotographerApplication photographerApplication);

    @Select("SELECT COUNT(1) FROM photographer_application WHERE user_id = #{userId}")
    int countByUserId(Long userId);

    @Select("SELECT * FROM photographer_application WHERE user_id = #{userId}")
    PhotographerApplication selectByUserId(@Param("userId") Long userId);

    @Update("""
    UPDATE photographer_application
    SET description = #{description},
        status = 0,
        reject_reason = NULL,
        apply_time = #{applyTime},
        review_time = NULL,
        reviewer_id = NULL
    WHERE user_id = #{userId}
    """)
    void updateApply(PhotographerApplication application);

    int updateStatus(PhotographerApplication application);
}
