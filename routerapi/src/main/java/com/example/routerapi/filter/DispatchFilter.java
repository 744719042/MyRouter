package com.example.routerapi.filter;

import com.example.routerapi.RouterCallback;
import com.example.routerbase.RouterConfig;
import com.example.routerapi.RouterRequest;
import com.example.routerapi.disptcher.RouterDispatcherHelper;
import com.example.routerapi.utils.RouterUtils;

public class DispatchFilter implements Filter {
    @Override
    public Object doFilter(FilterChain chain) {
        RouterRequest request = chain.getRequest();
        RouterConfig config = request.getConfig();
        RouterCallback callback = request.getCallback();
        if (config != null) {
            switch (config.getType()) {
                case Activity:
                    return RouterDispatcherHelper.dispatchActivity(request);
                case Service:
                    return RouterDispatcherHelper.dispatchService(request);
                case Fragment:
                    return RouterDispatcherHelper.dispatchFragment(request);
            }
        } else {
            RouterUtils.notifyRouterFailure(callback, new IllegalArgumentException("Error Router Type"));
        }

        return null;
    }
}
