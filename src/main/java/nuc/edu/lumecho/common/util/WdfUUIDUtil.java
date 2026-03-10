package nuc.edu.lumecho.common.util;

public class WdfUUIDUtil {
    public static String generateUUID() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }
}
