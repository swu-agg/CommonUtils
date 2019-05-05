package com.agg.common.utils;

import android.support.annotation.NonNull;
import android.util.Log;

public class ExceptionCatchUtils {

    public ExceptionCatchUtils() {
    }

    public static void catchE(@NonNull Exception e, String TAG, boolean isLogToFile) {
        if (TAG == null) {
            TAG = "ExceptionCatchUtils";
        }

        e.printStackTrace();
        String msg = e.getMessage() == null ? "null" : e.getMessage();
        Log.e( TAG, msg);
    }

    public static void catchE(Exception e) {
        catchE(e, "ExceptionCatchUtils", false);
    }

    public static void catchE(Exception e, String TAG) {
        catchE(e, TAG, false);
    }

}
