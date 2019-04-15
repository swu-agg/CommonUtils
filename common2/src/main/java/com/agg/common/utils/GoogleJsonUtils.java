package com.agg.common.utils;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class GoogleJsonUtils {
    private static volatile GoogleJsonUtils defaultInstance;
    private Gson gson = new Gson();

    public GoogleJsonUtils() {
    }

    public static GoogleJsonUtils getInstance() {
        if (defaultInstance == null) {
            synchronized (GoogleJsonUtils.class) {
                if (defaultInstance == null) {
                    defaultInstance = new GoogleJsonUtils();
                }
            }
        }
        return defaultInstance;
    }

    public <Bean> Bean getBean(@NonNull String json, @NonNull Class<Bean> beanClass) {
        try {
            return this.gson.fromJson(json, beanClass);
        } catch (Exception var4) {
            ExceptionCatchUtils.catchE(var4, "GoogleJsonUtils");
            return null;
        }
    }

    public <Bean> List<Bean> getListBean(@NonNull String json, @NonNull Class<Bean> beanClass) {
        try {
            return (List) this.gson.fromJson(json, (new TypeToken<List<Bean>>() {
            }).getType());
        } catch (Exception var4) {
            ExceptionCatchUtils.catchE(var4, "GoogleJsonUtils");
            return null;
        }
    }

    public <Bean> String toJson(@NonNull Bean bean) {
        return this.gson.toJson(bean);
    }

    public Gson getGson() {
        return this.gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }
}
