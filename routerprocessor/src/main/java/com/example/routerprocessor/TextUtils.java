package com.example.routerprocessor;

public class TextUtils {
    private TextUtils() {

    }

    public static boolean isEmpty(String text) {
        return text == null || "".equals(text);
    }
}
