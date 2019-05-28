package com.example.routerapi;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.example.routerbase.RouterConfig;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class RouterRequest {
    private String mPath;
    private Bundle mParams;
    private int mRequestCode;
    private int mExtra;
    private WeakReference<Activity> mActivity;
    private Context mContext;
    private RouterCallback mCallback;
    private RouterConfig mConfig;

    private RouterRequest(String path, Bundle params, int requestCode, int extra,
                          Activity activity, Context context, RouterCallback callback) {
        this.mPath = path;
        this.mParams = params;
        this.mRequestCode = requestCode;
        this.mExtra = extra;
        this.mActivity = new WeakReference<>(activity);
        this.mContext = context == null ? null : context.getApplicationContext();
        this.mCallback = callback;
    }

    public String getPath() {
        return mPath;
    }

    public Bundle getParams() {
        return mParams;
    }

    public int getExtra() {
        return mExtra;
    }

    public int getRequestCode() {
        return mRequestCode;
    }

    public Activity getActivity() {
        return mActivity == null || mActivity.get() == null ? null : mActivity.get();
    }

    public Context getContext() {
        return mContext;
    }

    public RouterCallback getCallback() {
        return mCallback;
    }

    public RouterConfig getConfig() {
        return mConfig;
    }

    public void setConfig(RouterConfig mConfig) {
        this.mConfig = mConfig;
    }

    public static class Builder {
        private String path;
        private Bundle bundle = new Bundle();
        private int requestCode = -1;
        private int extra = 0;
        private Activity activity;
        private Context context;
        private RouterCallback callback;

        public Builder(String path) {
            this.path = path;
        }

        public Builder withString(String key, String value) {
            bundle.putString(key, value);
            return this;
        }

        public Builder withStringArrayList(String key, ArrayList<String> value) {
            bundle.putStringArrayList(key, value);
            return this;
        }

        public Builder withInt(String key, int value) {
            bundle.putInt(key, value);
            return this;
        }

        public Builder withIntArrayList(String key, ArrayList<Integer> value) {
            bundle.putIntegerArrayList(key, value);
            return this;
        }

        public Builder withDouble(String key, double value) {
            bundle.putDouble(key, value);
            return this;
        }

        public Builder requestCode(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        public Builder withActivity(Activity activity) {
            this.activity = activity;
            return this;
        }

        public Builder withContext(Context context) {
            this.context = context.getApplicationContext();
            return this;
        }

        public Builder withCallback(RouterCallback callback) {
            this.callback = callback;
            return this;
        }

        public Builder withExtra(int extra) {
            this.extra = extra;
            return this;
        }

        public RouterRequest build() {
            return new RouterRequest(path, bundle, requestCode, extra, activity, context, callback);
        }
    }
}
