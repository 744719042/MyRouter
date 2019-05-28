package com.example.routerprocessor;

import com.example.routerbase.annotation.Filter;
import com.example.routerbase.annotation.Router;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;

public class FilterProcessor extends AbstractProcessor {
    private Elements mElements;
    private Types mTypes;
    private Filer mFiler;
    private static final String KEY_MODULE_NAME = "moduleName";

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mElements = processingEnvironment.getElementUtils();
        mTypes = processingEnvironment.getTypeUtils();
        mFiler = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment env) {
        Map<String, String> options = processingEnv.getOptions();
        String moduleName = "";
        if (!CollectionUtils.isEmpty(options)) {
            moduleName = options.get(KEY_MODULE_NAME);
        }

        if (TextUtils.isEmpty(moduleName)) {
            throw new RuntimeException("No module name defined!!");
        }


        Set<? extends Element> filters = env.getElementsAnnotatedWith(Filter.class);

        if (CollectionUtils.isEmpty(filters)) {
            return true;
        }
        System.out.println("filters = " + filters);

        String pkgName = "com.example.filter.config." + moduleName;
        StringBuilder builder = new StringBuilder();
        builder.append("package ").append(pkgName).append(";").append(Constant.LINE_SEPARATOR);
        builder.append("public class Router$Filter implements com.example.routerbase.FilterModule { ").append(Constant.LINE_SEPARATOR);
        builder.append("public void loadFilters(java.util.List<com.example.routerbase.FilterConfig> list) {").append(Constant.LINE_SEPARATOR);
        System.out.println(builder.toString());
        for (Element element : filters) {
            System.out.println(element);
            TypeElement typeElement = (TypeElement) element;
            Filter filter = typeElement.getAnnotation(Filter.class);
            String priority = String.valueOf(filter.priority());
            String group = moduleName;
            String className = typeElement.getQualifiedName().toString();
            System.out.println("filter = " + filter);
            System.out.println("priority = " + priority + ", group = " + moduleName + ", className = " + className);
            builder.append("list.add(new com.example.routerbase.FilterConfig(")
                    .append(priority).append(",\"").append(className).append("\"));").append(Constant.LINE_SEPARATOR);
        }
        builder.append("}").append(Constant.LINE_SEPARATOR);
        builder.append("}").append(Constant.LINE_SEPARATOR);

        String fullName = pkgName + ".Router$Filter";
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
}
