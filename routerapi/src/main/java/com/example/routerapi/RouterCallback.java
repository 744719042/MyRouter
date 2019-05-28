package com.example.routerapi;

import com.example.routerapi.filter.FilterChain;

public interface RouterCallback {
    void onFound(RouterRequest request);
    void onLost(Throwable throwable);
    void onIntercept(RouterRequest request, FilterChain chain);
    void onArrival(RouterRequest request);
}
