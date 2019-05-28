package com.example.routerapi.disptcher;

import com.example.routerapi.RouterManager;
import com.example.routerapi.RouterRequest;
import com.example.routerapi.filter.DispatchFilter;
import com.example.routerapi.filter.Filter;
import com.example.routerapi.filter.FilterChain;
import com.example.routerapi.filter.RequestInitFilter;

import java.util.ArrayList;
import java.util.List;

public class RouterDispatcherHelper {
    private static final ActivityDispatcher ACTIVITY_DISPATCHER = new ActivityDispatcher();
    private static final FragmentDispatcher FRAGMENT_DISPATCHER = new FragmentDispatcher();
    private static final ServiceDispatcher SERVICE_DISPATCHER = new ServiceDispatcher();

    public static Object dispatch(RouterRequest request) {
        List<Filter> filters = new ArrayList<>();
        filters.add(new RequestInitFilter());
        filters.addAll(RouterManager.getInstance().getFilters());
        filters.add(new DispatchFilter());
        FilterChain filterChain = new FilterChain(request, filters);
        return filterChain.proceed();
    }

    public static Object dispatchActivity(RouterRequest request) {
        return ACTIVITY_DISPATCHER.dispatch(request);
    }

    public static Object dispatchFragment(RouterRequest request) {
        return FRAGMENT_DISPATCHER.dispatch(request);
    }

    public static Object dispatchService(RouterRequest request) {
        return SERVICE_DISPATCHER.dispatch(request);
    }
}
