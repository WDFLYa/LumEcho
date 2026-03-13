package nuc.edu.lumecho.mapper;

import nuc.edu.lumecho.model.entity.ResourceFile;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ResourceFileMapper {

    List<ResourceFile> selectByBizIdAndTypes(
            @Param("bizId") Long bizId,
            @Param("bizTypes") List<String> bizTypes
    );

    @Insert("INSERT INTO resource_file " +
            "(biz_type, biz_id, file_url, file_name, created_by, created_time) " +
            "VALUES " +
            "(#{bizType}, #{bizId}, #{fileUrl}, #{fileName}, #{createdBy}, #{createdTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ResourceFile resource);


    @Update("UPDATE resource_file " +
            "SET biz_id = #{bizId} " +
            "WHERE file_url = #{fileUrl} AND biz_type = #{bizType} AND biz_id IS NULL")
    int bindBizIdByUrl(@Param("fileUrl") String fileUrl,
                       @Param("bizType") String bizType,
                       @Param("bizId") Long bizId);

}
