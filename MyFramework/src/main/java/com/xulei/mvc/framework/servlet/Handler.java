package com.xulei.mvc.framework.servlet;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * Handler 里面保存了一个url匹配器，以及对应的bean实例和该bean下面的方法
 */
public class Handler {

    private  Pattern pattern;
    private  Object controller;
    private Method method;


    public Handler() {
    }

    public Handler(Pattern pattern, Object value, Method method) {

        this.pattern=pattern;
        this.controller=value;
        this.method=method;

    }


    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
