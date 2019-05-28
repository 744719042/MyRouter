package com.example.routerapi.disptcher;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.routerapi.RouterRequest;

public class FragmentDispatcher implements RouterDispatcher {
    @Override
    public Object dispatch(RouterRequest request) {
        Bundle args = request.getParams();
        try {
            String className = request.getConfig().getClassName();
            Class<Fragment> clazz = (Class<Fragment>) Class.forName(className);
            Fragment fragment = clazz.newInstance();
            fragment.setArguments(args);
            return fragment;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
