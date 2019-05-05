package com.agg.common.helper;

import android.os.Build;
import android.support.v4.app.FragmentActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observable;

public class PermissionHelper {

    public static Observable<Boolean> rxAsk(FragmentActivity activity, String permission) {
        if (Build.VERSION.SDK_INT >= 23) {
            RxPermissions rxPermissions = new RxPermissions(activity);
            return rxPermissions.request(permission);
        } else {
            return Observable.just(false).map(a -> {
                throw new RuntimeException("the sdk is lower than 23....");
            });
        }
    }

    public static Observable<Boolean> rxAsk(FragmentActivity activity, String... permissionName) {
        if (Build.VERSION.SDK_INT >= 23) {
            RxPermissions rxPermissions = new RxPermissions(activity);
            return rxPermissions.request(permissionName);
        } else
            return Observable.just(false).map(a -> {
                throw new RuntimeException("the sdk is lower than 23....");
            });
    }
}