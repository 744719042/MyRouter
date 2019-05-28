package com.example.routerbase;

import java.util.Objects;

public class FilterConfig {
    private int mPriority;
    private String mClassName;

    public FilterConfig(int priority, String className) {
        this.mPriority = priority;
        this.mClassName = className;
    }

    public int getPriority() {
        return mPriority;
    }

    public String getClassName() {
        return mClassName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilterConfig that = (FilterConfig) o;
        return mPriority == that.mPriority &&
                Objects.equals(mClassName, that.mClassName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mPriority, mClassName);
    }
}
