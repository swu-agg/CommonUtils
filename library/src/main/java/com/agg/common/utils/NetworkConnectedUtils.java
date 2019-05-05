package com.agg.common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * create by agg
 * 2019/02/15
 */
public class NetworkConnectedUtils {
    public static boolean notNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mConnectivityManager != null) {
                NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                if (mNetworkInfo != null) {
                    return !(mNetworkInfo.isAvailable() && mNetworkInfo.isConnected());
                }
            }
        }
        return true;
    }
}
