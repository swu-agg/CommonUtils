package com.agg.common.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * <pre>
 *     author    : Agg
 *     blog      : https://blog.csdn.net/Agg_bin
 *     time      : 2019/03/22
 *     desc      :
 *     reference :
 *     remark    :
 * </pre>
 */
public class StorageUtils {

    private static final int ERROR = -1;

    private StorageUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 内部存储（内置SDCard）是否可用
     */
    public static boolean externalMemoryAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取系统总内存(ram)大小
     *
     * @return 系统总内存单位为B。
     */
    public static long getTotalMemorySize() {
        String dir = "/proc/meminfo";
        try {
            FileReader fr = new FileReader(dir);
            BufferedReader br = new BufferedReader(fr, 2048);
            String memoryLine = br.readLine();
            String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
            br.close();
            return Integer.parseInt(subMemoryLine.replaceAll("\\D+", "")) * 1024L;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取系统当前可用内存(ram)大小
     *
     * @param context 可传入应用程序上下文。
     * @return 系统当前可用内存单位为B。
     */
    public static long getAvailableMemorySize(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        if (activityManager != null) {
            activityManager.getMemoryInfo(memoryInfo);
        }
        return memoryInfo.availMem;
    }

    /**
     * 获取内部存储（内置SDCard）总存储大小
     *
     * @return 内部存储总存储单位为B。
     */
    public static long getTotalExternalMemorySize() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            return stat.getTotalBytes();
        } else {
            return ERROR;
        }
    }

    /**
     * 获取内部存储（内置SDCard）可用存储大小
     *
     * @return 内部存储可用存储单位为B。
     */
    public static long getAvailableExternalMemorySize() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            return stat.getAvailableBytes();
        } else {
            return ERROR;
        }
    }

    /**
     * 获取外部存储（外置SDCard）总存储大小
     *
     * @return 外部存储总存储单位为B。
     */
    public static long getTotalExternalSDMemorySize() {
        File externalSDPath;
        try {
            externalSDPath = new File("/mnt/external_sd/");
            if (externalSDPath.exists()) {
                StatFs stat = new StatFs(externalSDPath.getPath());
                return stat.getTotalBytes();
            } else {
                return ERROR;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    /**
     * 获取外部存储（外置SDCard）可用存储大小
     *
     * @return 外部存储可用存储单位为B。
     */
    public static long getAvailableExternalSDMemorySize() {
        File externalSDPath;
        try {
            externalSDPath = new File("/mnt/external_sd/");
            if (externalSDPath.exists()) {
                StatFs stat = new StatFs(externalSDPath.getPath());
                return stat.getAvailableBytes();
            } else {
                return ERROR;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }


}
