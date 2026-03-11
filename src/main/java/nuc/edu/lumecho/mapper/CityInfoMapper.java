package nuc.edu.lumecho.mapper;

import nuc.edu.lumecho.model.entity.CityInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CityInfoMapper {

    @Select("SELECT id, name,province_id FROM city_info WHERE province_id = #{provinceId}")
    List<CityInfo> listAllByProvinceId(Long provinceId);

}
