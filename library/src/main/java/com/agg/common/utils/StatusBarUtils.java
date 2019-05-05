package com.agg.common.utils;

import android.app.Activity;

public class StatusBarUtils {

    /**
     *  隐藏状态栏
     * @param activity 页面
     */
    public static void hideStatusBar(Activity activity) {
        activity.requestWindowFeature(1);
        activity.getWindow().setFlags(1024, 1024);
    }

}
