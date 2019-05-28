package com.example.routerapi.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import dalvik.system.DexFile;

public class ClassUtils {
    private static final String TAG = "ClassUtils";
    private ClassUtils() {

    }

    public static List<String> getSourceDirs(Context context) {
        final ApplicationInfo applicationInfo = context.getApplicationInfo();
        List<String> list = new ArrayList<>();
        list.add(applicationInfo.sourceDir);
        return list;
    }

    public static void getConfigClassNames(String dexPath, List<String> routerList, List<String> filter) {
        try {
            DexFile dexfile = new DexFile(dexPath);
            Enumeration<String> dexEntries = dexfile.entries();
            while (dexEntries.hasMoreElements()) {
                String className = dexEntries.nextElement();
                if (className.startsWith("com.example.router.config")) {
                    Log.e(TAG, className);
                    routerList.add(className);
                }

                if (className.startsWith("com.example.filter.config")) {
                    Log.e(TAG, className);
                    filter.add(className);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
