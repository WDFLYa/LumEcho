package nuc.edu.lumecho.service;

import nuc.edu.lumecho.model.entity.DistrictInfo;

import java.util.List;

public interface DistrictInfoService {
    List<DistrictInfo> listAllByCityId(Long cityId);
}
