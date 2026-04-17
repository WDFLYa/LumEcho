package nuc.edu.lumecho.mapper;

import nuc.edu.lumecho.model.entity.PhotographyActivity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ActivityMapper {

    // 原有
    void insert(PhotographyActivity photographyActivity);
    List<PhotographyActivity> selectAllActivity();
    PhotographyActivity selectActivityById(Long id);
    void updateStatusToOngoing(LocalDateTime now);
    void updateStatusToEnded(LocalDateTime now);

    // 👇 新增：分页 + 模糊查询 + 状态筛选
    List<PhotographyActivity> selectActivityList(
            @Param("status") Integer status,
            @Param("keyword") String keyword,
            @Param("offset") int offset,
            @Param("limit") int limit
    );
}