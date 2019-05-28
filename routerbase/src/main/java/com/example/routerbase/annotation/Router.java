package com.example.routerbase.annotation;

import com.example.routerbase.RouterType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Router {
    String path();
    RouterType type() default RouterType.Activity;
}
