package nuc.edu.lumecho.service;

import nuc.edu.lumecho.model.entity.CityInfo;

import java.util.List;

public interface CityInfoService {
    List<CityInfo> listAllByProvinceId(Long provinceId);
}
