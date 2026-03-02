package nuc.edu.lumecho.common.util;

import cn.hutool.core.util.StrUtil;

public final class WdfStringUtil {
    private WdfStringUtil() {
    }
    public static boolean isEmpty(CharSequence str) {
        return StrUtil.isEmpty(str);
    }
    public static boolean isNotEmpty(CharSequence str) {
        return StrUtil.isNotEmpty(str);
    }
    public static boolean isBlank(CharSequence str) {
        return StrUtil.isBlank(str);
    }
    public static boolean isNotBlank(CharSequence str) {
        return StrUtil.isNotBlank(str);
    }

}
