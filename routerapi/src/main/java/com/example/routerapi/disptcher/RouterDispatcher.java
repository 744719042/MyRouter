package com.example.routerapi.disptcher;

import com.example.routerapi.RouterRequest;

public interface RouterDispatcher {
    Object dispatch(RouterRequest request);
}
