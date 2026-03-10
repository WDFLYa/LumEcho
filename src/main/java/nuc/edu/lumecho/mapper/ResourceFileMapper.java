package nuc.edu.lumecho.mapper;

import nuc.edu.lumecho.model.entity.ResourceFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ResourceFileMapper {

    List<ResourceFile> selectByBizIdAndTypes(
            @Param("bizId") Long bizId,
            @Param("bizTypes") List<String> bizTypes
    );

}
