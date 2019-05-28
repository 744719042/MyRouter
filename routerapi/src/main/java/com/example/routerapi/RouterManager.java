package com.example.routerapi;

import android.content.Context;

import com.example.routerapi.disptcher.RouterDispatcherHelper;
import com.example.routerapi.filter.Filter;
import com.example.routerapi.utils.ClassUtils;
import com.example.routerapi.utils.CollectionUtils;
import com.example.routerapi.utils.RouterUtils;
import com.example.routerbase.FilterConfig;
import com.example.routerbase.FilterModule;
import com.example.routerbase.RouterConfig;
import com.example.routerbase.RouterModule;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class RouterManager {
    private static volatile RouterManager sInstance;
    private Context mContext;
    private Map<String, RouterModule> mModuleMap = new HashMap<>();
    private Map<String, Map<String, RouterConfig>> mRouterConfigMap = new HashMap<>();

    private List<Filter> mFilters = new ArrayList<>();

    private RouterManager() {

    }

    public static RouterManager getInstance() {
        if (sInstance == null) {
            synchronized (RouterManager.class) {
                if (sInstance == null) {
                    sInstance = new RouterManager();
                }
            }
        }

        return sInstance;
    }

    public Context getContext() {
        return mContext;
    }

    public void init(Context context) {
        this.mContext = context.getApplicationContext();
        List<String> routerList = new ArrayList<>();
        List<String> filterList = new ArrayList<>();
        List<String> sourceList = ClassUtils.getSourceDirs(context);
        for (String path : sourceList) {
            ClassUtils.getConfigClassNames(path, routerList, filterList);
        }

        initFilters(filterList);
        initRouterModule(routerList);
    }

    private void initRouterModule(List<String> routerList) {
        if (!CollectionUtils.isEmpty(routerList)) {
            try {
                for (String routerModule : routerList) {
                    String group = RouterUtils.getClassPathGroup(routerModule);
                    mModuleMap.put(group, (RouterModule) Class.forName(routerModule).newInstance());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initFilters(List<String> filterList) {
        if (!CollectionUtils.isEmpty(filterList)) {
            TreeSet<FilterConfig> filterConfigs = new TreeSet<>(new Comparator<FilterConfig>() {
                @Override
                public int compare(FilterConfig left, FilterConfig right) {
                    return left.getPriority() - right.getPriority();
                }
            });
            try {
                List<FilterConfig> list = new ArrayList<>();
                for (String filterModuleName : filterList) {
                    FilterModule filterModule = (FilterModule) Class.forName(filterModuleName).newInstance();
                    filterModule.loadFilters(list);
                }

                filterConfigs.addAll(list);
                while (!filterConfigs.isEmpty()) {
                    FilterConfig filterConfig = filterConfigs.pollLast();
                    mFilters.add((Filter) Class.forName(filterConfig.getClassName()).newInstance());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<Filter> getFilters() {
        return mFilters;
    }

    public Object navigate(RouterRequest request) {
        return RouterDispatcherHelper.dispatch(request);
    }

    public RouterConfig getRouterConfig(String path) {
        String group = RouterUtils.getPathGroup(path);

        Map<String, RouterConfig> map = mRouterConfigMap.get(group);
        if (map == null) {
            RouterModule routerModule = mModuleMap.get(group);
            if (routerModule == null) {
                return null;
            }
            map =  new HashMap<>();
            routerModule.loadConfigs(map);
            mRouterConfigMap.put(group, map);
        }

        return map.get(path);
    }
}
