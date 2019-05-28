package com.example.routerprocessor;

import java.util.Collection;
import java.util.Map;

public class CollectionUtils {
    private CollectionUtils() {

    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }
}
