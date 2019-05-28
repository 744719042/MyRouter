package com.example.routerprocessor;

import com.example.routerbase.RouterType;
import com.example.routerbase.annotation.Router;

import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;

public class RouterProcessor extends AbstractProcessor {
    private Elements mElements;
    private Types mTypes;
    private Filer mFiler;
    private static final String KEY_MODULE_NAME = "moduleName";
    private Set<String> mSet;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mElements = processingEnvironment.getElementUtils();
        mTypes = processingEnvironment.getTypeUtils();
        mFiler = processingEnvironment.getFiler();
        mSet = new HashSet<>();
        mSet.add(Router.class.getCanonicalName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment env) {
        Map<String, String> options = processingEnv.getOptions();
        System.out.println("options = " + options);
        String moduleName = "";
        if (!CollectionUtils.isEmpty(options)) {
            moduleName = options.get(KEY_MODULE_NAME);
        }

        if (TextUtils.isEmpty(moduleName)) {
            throw new RuntimeException("No module name defined!!");
        }

        System.out.println("moduleName = " + moduleName);
        Set<? extends Element> routers = env.getElementsAnnotatedWith(Router.class);

        if (CollectionUtils.isEmpty(routers)) {
            return true;
        }
        System.out.println("routers = " + routers);

        String pkgName = "com.example.router.config." + moduleName;
        StringBuilder builder = new StringBuilder();
        builder.append("package ").append(pkgName).append(";").append(Constant.LINE_SEPARATOR);
        builder.append("public class Router$Module implements com.example.routerbase.RouterModule { ").append(Constant.LINE_SEPARATOR);
        builder.append("public void loadConfigs(java.util.Map<String, com.example.routerbase.RouterConfig> map) {").append(Constant.LINE_SEPARATOR);
        for (Element element : routers) {
            System.out.println(element);
            TypeElement typeElement = (TypeElement) element;
            Router router = typeElement.getAnnotation(Router.class);
            String path = router.path();
            String group = moduleName;
            String className = typeElement.getQualifiedName().toString();
            String type = getRouterType(router.type());
            System.out.println("router = " + router);
            System.out.println("path = " + path + ", group = " + moduleName + ", className = " + className + ", type = " + type);
            builder.append("map.put(\"").append(path).append("\",").append("new com.example.routerbase.RouterConfig(\"")
                    .append(path).append("\",\"").append(group).append("\",").append(type).append(",\"").append(className).append("\"));").append(Constant.LINE_SEPARATOR);
        }
        builder.append("}").append(Constant.LINE_SEPARATOR);
        builder.append("}").append(Constant.LINE_SEPARATOR);

        String fullName = pkgName + ".Router$Module";
        writeToFile(fullName, builder.toString());
        return true;
    }

    private void writeToFile(String fullName, String source) {
        try {
            JavaFileObject javaFileObject = mFiler.createSourceFile(fullName, (Element[]) null);
            Writer writer = javaFileObject.openWriter();
            writer.append(source);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getRouterType(RouterType type) {
        switch (type) {
            case Fragment:
                return "com.example.routerbase.RouterType.Fragment";
            case Service:
                return "com.example.routerbase.RouterType.Service";
            case Activity:
            default:
                return "com.example.routerbase.RouterType.Activity";
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return mSet;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }
}
