package nuc.edu.lumecho.mapper;

import nuc.edu.lumecho.model.dto.request.photographer.CreateActivityRequest;

import nuc.edu.lumecho.model.entity.PhotographyActivity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ActivityMapper {
    void insert(PhotographyActivity photographyActivity);
}
