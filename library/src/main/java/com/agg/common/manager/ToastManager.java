package com.agg.common.manager;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.StringRes;

import com.agg.common.utils.StringUtils;
import com.agg.common.utils.ToastUtils;

/**
 * <pre>
 *     author    : Agg
 *     blog      : https://blog.csdn.net/Agg_bin
 *     time      : 2019/04/08
 *     desc      :
 *     reference :
 *     remark    :
 * </pre>
 */
public class ToastManager {

    private ToastUtils toastUtils;
    private static volatile ToastManager toastManager;
    private Context applicationContext;

    public static ToastManager getInstance() {
        if (toastManager == null) {
            synchronized (ToastManager.class) {
                if (toastManager == null) {
                    toastManager = new ToastManager();
                }
            }
        }
        return toastManager;
    }

    private ToastManager() {
    }

    public void init(Context context, int showTimeDuration, int tvSize, Typeface typeface) {
        this.applicationContext = context.getApplicationContext();
        this.toastUtils = new ToastUtils(this.applicationContext, showTimeDuration);
        if (typeface != null) {
            this.toastUtils.setTextTypeface(typeface);
        }

        if (tvSize > 0) {
            this.toastUtils.setTextSize(tvSize);
        }

    }

    public void release() {
        this.applicationContext = null;
        this.toastUtils = null;
    }

    public void toast(String str, String tag) {
        if (!StringUtils.isEmpty(str)) {
            if (!StringUtils.isEmpty(tag)) {
                this.toastUtils.setText(str + ":(" + tag + ")").show();
            } else {
                this.toastUtils.setText(str).show();
            }
        }

    }

    public void toast(@StringRes int strRes) {
        this.toast(this.applicationContext.getString(strRes), (String) null);
    }

    public void toast(@StringRes int strRes, String tag) {
        this.toast(this.applicationContext.getString(strRes), tag);
    }

    public void toast(String str) {
        this.toast(str, (String) null);
    }


}
