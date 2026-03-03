package nuc.edu.lumecho.common.util;

import cn.hutool.core.util.IdUtil;

public class WdfTokenUtil {
    public static String generateLoginToken() {
        return IdUtil.fastSimpleUUID();
    }
}
