package nuc.edu.lumecho.controller.address;
import nuc.edu.lumecho.model.entity.Address;
import nuc.edu.lumecho.model.entity.CityInfo;
import nuc.edu.lumecho.model.entity.DistrictInfo;
import nuc.edu.lumecho.model.entity.ProvinceInfo;
import nuc.edu.lumecho.service.AddressService;
import nuc.edu.lumecho.service.CityInfoService;
import nuc.edu.lumecho.service.DistrictInfoService;
import nuc.edu.lumecho.service.ProvinceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private AddressService addressService;

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

    @PostMapping("/add")
    public void add(@RequestBody Address address) {
        addressService.add(address);
    }

    @GetMapping("/list")
    public List<Address> listByUserId() {
        return addressService.getByUserId();
    }

}
