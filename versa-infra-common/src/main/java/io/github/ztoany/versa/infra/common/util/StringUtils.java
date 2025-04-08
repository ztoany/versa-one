package io.github.ztoany.versa.infra.common.util;

public class StringUtils {
    public static String ifNullOrBlankToEmptyAndTrim(String str) {
        boolean hasText = str != null && !str.isBlank();
        var ret = hasText ? str : "";
        return ret.trim();
    }
}
