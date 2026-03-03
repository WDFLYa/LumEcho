package nuc.edu.lumecho.common.util;

import cn.hutool.core.util.RandomUtil;

public class WdfRandomCodeUtil {

    public static String generateSixDigitCode() {
        return RandomUtil.randomNumbers(6);
    }

}
