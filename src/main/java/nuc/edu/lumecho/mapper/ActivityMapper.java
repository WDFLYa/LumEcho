package nuc.edu.lumecho.mapper;

import nuc.edu.lumecho.model.dto.request.photographer.CreateActivityRequest;

import nuc.edu.lumecho.model.entity.PhotographyActivity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ActivityMapper {
    void insert(PhotographyActivity photographyActivity);
    List<PhotographyActivity> selectAllActivity();

    PhotographyActivity selectActivityById(Long id);
}
