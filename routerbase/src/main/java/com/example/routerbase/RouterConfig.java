package com.example.routerbase;

public class RouterConfig {
    private String mPath;
    private String mGroup;
    private RouterType mType;
    private String mClassName;

    public RouterConfig(String path, String group, RouterType type, String className) {
        this.mPath = path;
        this.mGroup = group;
        this.mType = type;
        this.mClassName = className;
    }

    public String getPath() {
        return mPath;
    }

    public String getGroup() {
        return mGroup;
    }

    public RouterType getType() {
        return mType;
    }

    public String getClassName() {
        return mClassName;
    }
}
