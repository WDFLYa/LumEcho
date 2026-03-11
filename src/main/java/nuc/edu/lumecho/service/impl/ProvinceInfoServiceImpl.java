package nuc.edu.lumecho.service.impl;

import nuc.edu.lumecho.mapper.ProvinceInfoMapper;
import nuc.edu.lumecho.model.entity.ProvinceInfo;
import nuc.edu.lumecho.service.ProvinceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceInfoServiceImpl implements ProvinceInfoService {
    @Autowired
    private ProvinceInfoMapper provinceInfoMapper;
    @Override
    public List<ProvinceInfo> getAllProvinces() {
        List<ProvinceInfo> provinceInfos = provinceInfoMapper.listAll();
        return provinceInfos;
    }
}
