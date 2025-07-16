package io.github.ztoany.versa.infra.common.util;

import java.util.UUID;

public class RsqlQueryFilterUtils {
    private static final String USER_ID_FIELD = "userId";

    public static <T> String filterWith(String originalFilter, String fieldName, T fieldValue) {
        if(originalFilter == null || originalFilter.isBlank()) {
            return String.format("%s==%s", fieldName, fieldValue);
        }

        return String.format("(%s) and %s==%s", originalFilter, fieldName, fieldValue.toString());
    }

    public static String filterWithUserId(String originalFilter, UUID userId) {
        return filterWith(originalFilter, USER_ID_FIELD, userId);
    }
}
