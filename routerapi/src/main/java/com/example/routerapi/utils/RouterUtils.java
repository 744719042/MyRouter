package com.example.routerapi.utils;

import com.example.routerapi.RouterCallback;
import com.example.routerapi.RouterRequest;

public class RouterUtils {
    private RouterUtils() {

    }

    public static String getClassPathGroup(String path) {
        int index = path.lastIndexOf(".");
        String pkgName = path.substring(0, index);
        index = pkgName.lastIndexOf(".");
        return pkgName.substring(index + 1);
    }

    public static String getPathGroup(String path) {
        path = path.substring(1);
        int index = path.indexOf("/");
        return path.substring(0, index);
    }

    public static  void notifyRouterSuccess(RouterCallback callback, RouterRequest request) {
        if (callback != null) {
            callback.onArrival(request);
        }
    }

    public static void notifyRouterFailure(RouterCallback callback, Throwable throwable) {
        if (callback != null) {
            callback.onLost(throwable);
        }
    }
}
