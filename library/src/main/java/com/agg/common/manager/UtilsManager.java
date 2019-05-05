package com.agg.common.manager;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.StringRes;

import com.agg.common.utils.AppUtils;
import com.agg.common.utils.LogUtils;
import com.agg.common.utils.SPUtils;

import java.io.File;

/**
 * 工具类的管理者，请在Application中初始化
 */
public class UtilsManager {

    private static String CACHE_PATH = null;
    private static Context applicationContext;

    public static void init(Context context, String SP_NAME) {
        applicationContext = context.getApplicationContext();
        //初始化缓存地址
        if (context.getExternalCacheDir() != null) {
            CACHE_PATH = context.getExternalCacheDir().getAbsolutePath();
        } else if (Environment.getExternalStorageDirectory() != null) {
            CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + AppUtils.getAppPackageName(context);
            File file = new File(CACHE_PATH);
            if (!file.exists()) {
                if (!file.mkdir()) {
                    throw new RuntimeException("UtilsManager CACHE_PATH create fail...");
                }
            }
        } else {
            CACHE_PATH = context.getCacheDir().getAbsolutePath();
        }
        LogUtils.init();
        LogUtils.i(true, "UtilsManager", "cache_path:" + CACHE_PATH);
        SPUtils.getInstance().init(context, SP_NAME);
    }

    public static void init(Context context) {
        init(context, AppUtils.getAppPackageName(context));
    }

    public static String getCachePath() {
        return CACHE_PATH;
    }

    public static Context getApplicationContext() {
        return applicationContext;
    }

    public static String getStringRes(@StringRes int stringRes) {
        return getApplicationContext().getString(stringRes);
    }

}
