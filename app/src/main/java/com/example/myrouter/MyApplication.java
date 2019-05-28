package com.example.myrouter;

import android.app.Application;

import com.example.routerapi.RouterManager;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RouterManager.getInstance().init(this);
    }
}
