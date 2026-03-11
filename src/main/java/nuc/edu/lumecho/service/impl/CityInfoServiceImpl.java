package nuc.edu.lumecho.service.impl;

import nuc.edu.lumecho.mapper.CityInfoMapper;
import nuc.edu.lumecho.model.entity.CityInfo;
import nuc.edu.lumecho.service.CityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityInfoServiceImpl implements CityInfoService {

    @Autowired
    private CityInfoMapper cityInfoMapper;
    @Override
    public List<CityInfo> listAllByProvinceId(Long provinceId) {
        return cityInfoMapper.listAllByProvinceId(provinceId);
    }
}
