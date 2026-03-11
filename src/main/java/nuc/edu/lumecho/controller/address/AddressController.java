package nuc.edu.lumecho.controller.address;
import nuc.edu.lumecho.model.entity.CityInfo;
import nuc.edu.lumecho.model.entity.DistrictInfo;
import nuc.edu.lumecho.model.entity.ProvinceInfo;
import nuc.edu.lumecho.service.CityInfoService;
import nuc.edu.lumecho.service.DistrictInfoService;
import nuc.edu.lumecho.service.ProvinceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private ProvinceInfoService provinceInfoService;

    @Autowired
    private CityInfoService cityInfoService;

    @Autowired
    private DistrictInfoService districtInfoService;

    @GetMapping("/getallprovince")
    public List<ProvinceInfo> listAll() {
        return provinceInfoService.getAllProvinces();
    }

    @GetMapping("/getallcity")
    public List<CityInfo> listAllCity(@RequestParam Long provinceId) {
        return cityInfoService.listAllByProvinceId(provinceId);
    }


    @GetMapping("/getalldistrict")
    public List<DistrictInfo> listAllDistrict(@RequestParam Long cityId) {
        return districtInfoService.listAllByCityId(cityId);
    }
}
