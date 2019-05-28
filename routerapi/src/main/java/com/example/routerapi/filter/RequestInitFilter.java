package com.example.routerapi.filter;

import com.example.routerapi.ErrorHandler;
import com.example.routerapi.RouterCallback;
import com.example.routerbase.RouterConfig;
import com.example.routerapi.RouterManager;
import com.example.routerapi.RouterRequest;

public class RequestInitFilter implements Filter {

    @Override
    public Object doFilter(FilterChain chain) {
        RouterRequest request = chain.getRequest();
        RouterCallback callback = request.getCallback();

        RouterConfig routerConfig = RouterManager.getInstance().getRouterConfig(request.getPath());
        if (routerConfig == null) {
            if (callback != null) {
                callback.onLost(null);
            } else {
                RouterRequest errorRequest = new RouterRequest.Builder("/error/handler").build();
                ErrorHandler handler = (ErrorHandler) RouterManager.getInstance().navigate(errorRequest);
                if (handler != null) {
                    handler.onError(-1);
                }
            }
            return null;
        } else {
            request.setConfig(routerConfig);
            if (callback != null) {
                callback.onFound(request);
            }
            return chain.proceed();
        }
    }
}
