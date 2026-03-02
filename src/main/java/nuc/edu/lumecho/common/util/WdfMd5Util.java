package nuc.edu.lumecho.common.util;

import cn.hutool.crypto.digest.DigestUtil;

public class WdfMd5Util {
    public static String md5Encrypt(String input) {
        return input == null ? null : DigestUtil.md5Hex(input);
    }
}
