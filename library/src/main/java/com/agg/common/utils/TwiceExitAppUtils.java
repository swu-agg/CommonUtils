package com.agg.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * <pre>
 *     author    : Agg
 *     blog      : https://blog.csdn.net/Agg_bin
 *     time      : 2019/05/10
 *     desc      :
 *     reference :
 *     remark    :
 * </pre>
 */
public class TwiceExitAppUtils {

    private static long exitTime = 0L;

    private TwiceExitAppUtils(){
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    public interface ExitAppCallBack {
        void firstAsk();

        void beginExit();
    }

    public static void exitApp(Context context, boolean isBackToLaunch) {
        if (isBackToLaunch) {
            Intent startMain = new Intent("android.intent.action.MAIN");
            startMain.addCategory("android.intent.category.HOME");
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(startMain);
        }
        System.exit(0);
    }

    public static void exitApp(Context context) {
        exitApp(context, true);
    }

    public static void pressTwiceExitApp(Activity activity, boolean isBackToLaunch, long exitTimeMsSpace, ExitAppCallBack exitAppCallBack) {
        if (System.currentTimeMillis() - exitTime > exitTimeMsSpace) {
            exitTime = System.currentTimeMillis();
            if (exitAppCallBack != null) {
                exitAppCallBack.firstAsk();
            }
        } else {
            if (exitAppCallBack != null) {
                exitAppCallBack.beginExit();
            }

            activity.finish();
            exitApp(activity, isBackToLaunch);
        }

    }

    public static void pressTwiceExitApp(Activity activity, String msg, long exitTimeMsSpace, ExitAppCallBack exitAppCallBack) {
        pressTwiceExitApp(activity, true, exitTimeMsSpace, exitAppCallBack);
    }



}
