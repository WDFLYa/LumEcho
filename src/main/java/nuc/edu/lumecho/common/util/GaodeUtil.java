package nuc.edu.lumecho.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class GaodeUtil {

    private static final String KEY = "8025c78282841c40f91d8835533d3202";

    public static double[] getLocationByAddress(String address) {
        try {
            String encodedAddress = URLEncoder.encode(address, "UTF-8");
            String urlStr = "https://restapi.amap.com/v3/geocode/geo"
                    + "?address=" + encodedAddress
                    + "&key=" + KEY
                    + "&output=json";

            System.out.println("【真实请求】：" + urlStr);

            // 👇👇👇 用原生 Java 发送，绝对发得出去！绝对扣次数！
            URL url = new URL(urlStr);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            String result = sb.toString();

            System.out.println("【高德返回】：" + result);

            // 解析 JSON（极简解析，不依赖任何库）
            if (result.contains("\"status\":\"1\"") && result.contains("\"location\":")) {
                int locStart = result.indexOf("\"location\":\"") + 12;
                int locEnd = result.indexOf("\"", locStart);
                String location = result.substring(locStart, locEnd);
                String[] ll = location.split(",");
                double lng = Double.parseDouble(ll[0]);
                double lat = Double.parseDouble(ll[1]);
                return new double[]{lat, lng};
            }

        } catch (Exception e) {
            // 这里一定会打印出真正的错误！！！
            e.printStackTrace();
        }

        return new double[]{0.0, 0.0};
    }

    public static String getAddressByLocation(double lat, double lng) {
        return "未知位置";
    }

    public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        return 0;
    }
}