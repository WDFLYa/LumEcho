package nuc.edu.lumecho.mapper;

import java.util.List;
import nuc.edu.lumecho.model.entity.PhotographyActivity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PhotographyActivityMapper {

    List<PhotographyActivity> selectPendingActivity();
}