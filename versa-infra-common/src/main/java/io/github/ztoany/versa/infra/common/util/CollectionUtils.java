package io.github.ztoany.versa.infra.common.util;

import io.github.ztoany.versa.infra.common.db.json.JsonTypeSingleStringValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectionUtils {
    public static List<String> copyStringList(List<String> src) {
        if(src == null) return new ArrayList<>();
        return new ArrayList<>(src);
    }

    public static List<UUID> copyUuidList(List<UUID> src) {
        if(src == null) return new ArrayList<>();
        return new ArrayList<>(src);
    }

    public static List<? extends JsonTypeSingleStringValue> copyJsonTypeSingleValueList(List<? extends JsonTypeSingleStringValue> src) {
        if(src == null) return new ArrayList<>();
        return new ArrayList<>(src);
    }

    public static <S, D> List<D> mapList(List<S> src, Function<? super S, ? extends D> mapper) {
        if(src == null) return new ArrayList<>();
        return src.stream().map(mapper).collect(Collectors.toList());
    }

    public static List<String> stringListRemoveBlankAndTrim(List<String> src) {
        var ret = CollectionUtils.copyStringList(src);
        ret.removeIf(e -> Objects.isNull(e) || e.isBlank());
        return ret.stream().map(String::trim).collect(Collectors.toList());
    }

    public static List<String> stringListDistinctRemoveBlankAndTrim(List<String> src) {
        var ret = CollectionUtils.copyStringList(src);
        ret.removeIf(e -> Objects.isNull(e) || e.isBlank());
        return ret.stream().map(String::trim).distinct().collect(Collectors.toList());
    }

    public static List<UUID> uuidListDistinctRemoveNull(List<UUID> src) {
        var ret = CollectionUtils.copyUuidList(src);
        ret.removeIf(Objects::isNull);
        return ret.stream().distinct().collect(Collectors.toList());
    }

    public static List<? extends JsonTypeSingleStringValue> jsonTypeSingleStringValueListDistinctRemoveBlankAndTrim(List<? extends JsonTypeSingleStringValue> src) {
        var ret = CollectionUtils.copyJsonTypeSingleValueList(src);
        ret.removeIf((e) -> Objects.isNull(e) || Objects.isNull(e.getV()) || e.getV().isBlank());
        ret.forEach(JsonTypeSingleStringValue::trim);
        return ret.stream().distinct().collect(Collectors.toList());
    }
}
