package com.example.oracle.demo.framework.context;

import java.util.HashMap;
import java.util.Map;

public abstract class ApplicationContext {

    protected Map<String, Object> ctx = new HashMap<>();

    public abstract void setAttribute(String key, Object obj);

    public abstract Object getAttribute(String key);

    public Map<String, Object> getCtx() {
        return ctx;
    }

    public void setCtx(Map<String, Object> ctx) {
        this.ctx = ctx;
    }
}
