package com.example.routerapi.filter;

import com.example.routerapi.RouterRequest;

import java.util.ArrayList;
import java.util.List;

public class FilterChain {
    private List<Filter> mFilters = new ArrayList<>();
    private int mIndex = -1;
    private RouterRequest mRequest;

    public FilterChain(RouterRequest request, List<Filter> filters) {
        mFilters.clear();
        this.mFilters.addAll(filters);
        this.mIndex = -1;
        this.mRequest = request;
    }

    public Object proceed() {
        if (mIndex >= mFilters.size()) {
            return null;
        }

        Filter filter = mFilters.get(++mIndex);
        return filter.doFilter(this);
    }

    public RouterRequest getRequest() {
        return mRequest;
    }
}
