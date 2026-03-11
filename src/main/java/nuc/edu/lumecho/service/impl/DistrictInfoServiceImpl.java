package nuc.edu.lumecho.service.impl;

import nuc.edu.lumecho.mapper.DistrictInfoMapper;
import nuc.edu.lumecho.model.entity.DistrictInfo;
import nuc.edu.lumecho.service.DistrictInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictInfoServiceImpl implements DistrictInfoService {

    @Autowired
    private DistrictInfoMapper districtInfoMapper;

    @Override
    public List<DistrictInfo> listAllByCityId(Long cityId) {
        return districtInfoMapper.listAllByCityId(cityId);
    }
}
