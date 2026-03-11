package nuc.edu.lumecho.mapper;

import nuc.edu.lumecho.model.entity.ProvinceInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProvinceInfoMapper {

    @Select("SELECT id,name FROM province_info")
    List<ProvinceInfo> listAll();
}
