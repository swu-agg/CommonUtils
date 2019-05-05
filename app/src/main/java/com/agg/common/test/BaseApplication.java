package com.agg.common.test;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.Stack;

/**
 * <pre>
 *     author    : Agg
 *     blog      : https://blog.csdn.net/Agg_bin
 *     time      : 2019/05/05
 *     desc      :
 *     reference :
 *     remark    :
 * </pre>
 */
public class BaseApplication extends Application {

    private Stack<Activity> stack = new Stack<>();

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new CommonActivityLifecycleCallbacks());
    }

    private class CommonActivityLifecycleCallbacks implements ActivityLifecycleCallbacks{

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            stack.add(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            stack.remove(activity);
        }
    }

}
