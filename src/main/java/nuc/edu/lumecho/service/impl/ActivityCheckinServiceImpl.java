package nuc.edu.lumecho.service.impl;


import nuc.edu.lumecho.common.WdfUserContext;
import nuc.edu.lumecho.common.util.GaodeUtil;
import nuc.edu.lumecho.mapper.ActivityCheckinMapper;
import nuc.edu.lumecho.mapper.ActivityMapper;
import nuc.edu.lumecho.model.dto.request.checkin.CheckInRequest;
import nuc.edu.lumecho.model.entity.ActivityCheckin;
import nuc.edu.lumecho.model.entity.PhotographyActivity;
import nuc.edu.lumecho.service.ActivityCheckinService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import javax.annotation.Resource;
import java.util.Map;

@Service
public class ActivityCheckinServiceImpl implements ActivityCheckinService {

    @Resource
    private ActivityCheckinMapper checkinMapper;

    @Resource
    private ActivityMapper activityMapper;

    private final String GAODE_KEY = "8025c78282841c40f91d8835533d3202";

    @Override
    public String checkIn(CheckInRequest request) {
        Long userId = WdfUserContext.getCurrentUserId();
        Long activityId = request.getActivityId();

        // 1. 重复签到判断
        int count = checkinMapper.exists(activityId, userId);
        if (count > 0) {
            throw new RuntimeException("您已签到，请勿重复操作");
        }

        // 2. 获取活动（含经纬度）
        PhotographyActivity activity = activityMapper.selectActivityById(activityId);
        if (activity == null) {
            throw new RuntimeException("活动不存在");
        }

        Double actLat = activity.getLatitude();
        Double actLng = activity.getLongitude();
        Double userLat = request.getLatitude();
        Double userLng = request.getLongitude();

        // 3. 距离校验（现场 200 米）
        if (actLat != null && actLng != null && actLat != 0 && actLng != 0) {
            double dist = GaodeUtil.getDistance(userLat, userLng, actLat, actLng);
            if (dist > 200) {
                throw new RuntimeException("您不在活动现场，无法签到");
            }
        }

        // 4. 获取地址并保存
        String address = GaodeUtil.getAddressByLocation(userLat, userLng);

        ActivityCheckin checkin = new ActivityCheckin();
        checkin.setActivityId(activityId);
        checkin.setUserId(userId);
        checkin.setLatitude(userLat);
        checkin.setLongitude(userLng);
        checkin.setAddress(address);

        checkinMapper.insert(checkin);
        return address;
    }

    private String getAddress(double lat, double lng) {
        try {
            String url = "https://restapi.amap.com/v3/geocode/regeo"
                    + "?location=" + lng + "," + lat
                    + "&key=" + GAODE_KEY
                    + "&output=json";

            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> res = restTemplate.getForObject(url, Map.class);

            if ("1".equals(res.get("status"))) {
                Map<String, Object> regeo = (Map<String, Object>) res.get("regeocode");
                return regeo.get("formatted_address").toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "未知位置";
    }
}