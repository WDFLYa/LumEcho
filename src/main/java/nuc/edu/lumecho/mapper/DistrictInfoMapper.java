package nuc.edu.lumecho.mapper;

import nuc.edu.lumecho.model.entity.DistrictInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DistrictInfoMapper {

    @Select("SELECT id, name,city_id FROM district_info WHERE city_id = #{cityId}")
    List<DistrictInfo> listAllByCityId(Long cityId);

}
