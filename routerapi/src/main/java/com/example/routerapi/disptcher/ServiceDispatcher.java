package com.example.routerapi.disptcher;

import com.example.routerapi.RouterRequest;

public class ServiceDispatcher implements RouterDispatcher {
    @Override
    public Object dispatch(RouterRequest request) {
        try {
            String className = request.getConfig().getClassName();
            Class<?> clazz = Class.forName(className);
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
