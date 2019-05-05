package com.agg.common.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AppUtils {

    private static long exitTime = 0L;

    private AppUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean isInstallApp(Context context, String packageName) {
        return !TextUtils.isEmpty(packageName) && IntentUtils.getLaunchAppIntent(context, packageName) != null;
    }

    public static void installApp(Context context, String filePath) {
        installApp(context, FileUtils.getFileByPath(filePath));
    }

    public static void installApp(Context context, File file) {
        if (file != null) {
            context.startActivity(IntentUtils.getInstallAppIntent(file));
        }
    }

    public static void installApp(Activity activity, String filePath, int requestCode) {
        installApp(activity, FileUtils.getFileByPath(filePath), requestCode);
    }

    public static void installApp(Activity activity, File file, int requestCode) {
        if (file != null) {
            activity.startActivityForResult(IntentUtils.getInstallAppIntent(file), requestCode);
        }
    }

    public static void installAppOver7_0(Context context, String fileProviderStr, File file) {
        if (Build.VERSION.SDK_INT >= 24 && file != null && file.exists() && file.isFile()) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri apkUri = FileProvider.getUriForFile(context, fileProviderStr, file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            context.startActivity(intent);
        }

    }

    public static void uninstallApp(Context context, String packageName) {
        if (!TextUtils.isEmpty(packageName)) {
            context.startActivity(IntentUtils.getUninstallAppIntent(packageName));
        }
    }

    public static void uninstallApp(Activity activity, String packageName, int requestCode) {
        if (!TextUtils.isEmpty(packageName)) {
            activity.startActivityForResult(IntentUtils.getUninstallAppIntent(packageName), requestCode);
        }
    }

    public static void launchApp(Context context, String packageName) {
        if (!TextUtils.isEmpty(packageName)) {
            context.startActivity(IntentUtils.getLaunchAppIntent(context, packageName));
        }
    }

    public static void launchApp(Activity activity, String packageName, int requestCode) {
        if (!TextUtils.isEmpty(packageName)) {
            activity.startActivityForResult(IntentUtils.getLaunchAppIntent(activity, packageName), requestCode);
        }
    }

    public static void launchApp2(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            PackageManager pm = context.getPackageManager();
            try {
                // 原来的时候我们在得到PakageInfo的时候第二个参数都是设置为0.这个PackageInfo代表的就是某个程序的清单文件，
                // 默认情况下在解析这个清单文件的时候得到的只是清单文件中的一些版本信息的等这些常用的内容，因为要获取更多的内容需要解析更多的内容，
                // 就会消耗时间消耗资源，所以默认的时候都是只解析一些常用的，当我们要获取Activity等这些的时候就要给它一个标记，让它知道多解析这些你想要得到的内容，
                // 如果我们想得到里面的activity或者service等这些啊就必须将第二个参数设置为相应的PackageManager.GET_ACTIVITYS等
                PackageInfo info = pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
                ActivityInfo[] activityInfos = info.activities;//获取清单中所有Activity信息的数据
                if (activityInfos != null && activityInfos.length > 0) {//由于一些服务或者接收者等没有Activity所以这里必须进行判断
                    ActivityInfo activitInfo = activityInfos[0];//清单文件中配置的第一个Activity就是程序的启动Activity
                    Intent intent = new Intent();
                    intent.setClassName(packageName, activitInfo.name);//这个activityInfo就是清单中activity节点的name，这样就能得到Activity的全类名
                    context.startActivity(intent);
                } else {
                    // 打开出错
                }
            } catch (Exception e) {
                //
            }
        }
    }

    public static String getAppPackageName(Context context) {
        return context.getPackageName();
    }

    public static void getAppDetailsSettings(Context context) {
        getAppDetailsSettings(context, context.getPackageName());
    }

    public static void getAppDetailsSettings(Context context, String packageName) {
        if (!TextUtils.isEmpty(packageName)) {
            context.startActivity(IntentUtils.getAppDetailsSettingsIntent(packageName));
        }
    }

    public static String getAppName(Context context) {
        return getAppName(context, context.getPackageName());
    }

    public static String getAppName(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return null;
        } else {
            try {
                PackageManager pm = context.getPackageManager();
                PackageInfo pi = pm.getPackageInfo(packageName, 0);
                return pi == null ? null : pi.applicationInfo.loadLabel(pm).toString();
            } catch (PackageManager.NameNotFoundException var4) {
                var4.printStackTrace();
                return null;
            }
        }
    }

    public static Drawable getAppIcon(Context context) {
        return getAppIcon(context, context.getPackageName());
    }

    public static Drawable getAppIcon(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return null;
        } else {
            try {
                PackageManager pm = context.getPackageManager();
                PackageInfo pi = pm.getPackageInfo(packageName, 0);
                return pi == null ? null : pi.applicationInfo.loadIcon(pm);
            } catch (PackageManager.NameNotFoundException var4) {
                var4.printStackTrace();
                return null;
            }
        }
    }

    public static String getAppPath(Context context) {
        return getAppPath(context, context.getPackageName());
    }

    public static String getAppPath(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return null;
        } else {
            try {
                PackageManager pm = context.getPackageManager();
                PackageInfo pi = pm.getPackageInfo(packageName, 0);
                return pi == null ? null : pi.applicationInfo.sourceDir;
            } catch (PackageManager.NameNotFoundException var4) {
                var4.printStackTrace();
                return null;
            }
        }
    }

    public static String getAppVersionName(Context context) {
        return getAppVersionName(context, context.getPackageName());
    }

    public static String getAppVersionName(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return null;
        } else {
            try {
                PackageManager pm = context.getPackageManager();
                PackageInfo pi = pm.getPackageInfo(packageName, 0);
                return pi == null ? null : pi.versionName;
            } catch (PackageManager.NameNotFoundException var4) {
                var4.printStackTrace();
                return null;
            }
        }
    }

    public static int getAppVersionCode(Context context) {
        return getAppVersionCode(context, context.getPackageName());
    }

    public static int getAppVersionCode(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return -1;
        } else {
            try {
                PackageManager pm = context.getPackageManager();
                PackageInfo pi = pm.getPackageInfo(packageName, 0);
                return pi == null ? -1 : pi.versionCode;
            } catch (PackageManager.NameNotFoundException var4) {
                var4.printStackTrace();
                return -1;
            }
        }
    }

    public static Signature[] getAppSignature(Context context) {
        return getAppSignature(context, context.getPackageName());
    }

    public static Signature[] getAppSignature(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return null;
        } else {
            try {
                PackageManager pm = context.getPackageManager();
                PackageInfo pi = pm.getPackageInfo(packageName, 0);
                return pi == null ? null : pi.signatures;
            } catch (PackageManager.NameNotFoundException var4) {
                var4.printStackTrace();
                return null;
            }
        }
    }

    public static boolean isSystemApp(Context context) {
        return isSystemApp(context, context.getPackageName());
    }

    public static boolean isSystemApp(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        } else {
            try {
                PackageManager pm = context.getPackageManager();
                ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
                return ai != null && (ai.flags & 1) != 0;
            } catch (PackageManager.NameNotFoundException var4) {
                var4.printStackTrace();
                return false;
            }
        }
    }

    public static boolean isAppForeground(Context context) {
        return isAppForeground(context, context.getPackageName());
    }

    public static boolean isAppForeground(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context.getSystemService("activity");
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        return !tasks.isEmpty() && tasks.get(0).topActivity.getPackageName().equals(packageName);
    }

    public static AppUtils.AppInfo getAppInfo(Context context) {
        return getAppInfo(context, context.getPackageName());
    }

    public static AppUtils.AppInfo getAppInfo(Context context, String packageName) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return getBean(pm, pi);
        } catch (PackageManager.NameNotFoundException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    private static AppUtils.AppInfo getBean(PackageManager pm, PackageInfo pi) {
        if (pm != null && pi != null) {
            ApplicationInfo ai = pi.applicationInfo;
            String name = ai.loadLabel(pm).toString();
            Drawable icon = ai.loadIcon(pm);
            String packageName = pi.packageName;
            String packagePath = ai.sourceDir;
            String versionName = pi.versionName;
            int versionCode = pi.versionCode;
            boolean isSystem = (1 & ai.flags) != 0;
            return new AppUtils.AppInfo(name, icon, packageName, packagePath, versionName, versionCode, isSystem);
        } else {
            return null;
        }
    }

    public static List<AppInfo> getAppsInfo(Context context) {
        List<AppUtils.AppInfo> list = new ArrayList();
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
        Iterator var4 = installedPackages.iterator();

        while (var4.hasNext()) {
            PackageInfo pi = (PackageInfo) var4.next();
            AppUtils.AppInfo ai = getBean(pm, pi);
            if (ai != null) {
                list.add(ai);
            }
        }

        return list;
    }

    public static boolean cleanAppData(Context context, String... dirPaths) {
        File[] dirs = new File[dirPaths.length];
        int i = 0;
        String[] var4 = dirPaths;
        int var5 = dirPaths.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            String dirPath = var4[var6];
            dirs[i++] = new File(dirPath);
        }

        return cleanAppData(context, dirs);
    }

    public static boolean cleanAppData(Context context, File... dirs) {
        boolean isSuccess = CleanUtils.cleanInternalCache(context);
        isSuccess &= CleanUtils.cleanInternalDbs(context);
        isSuccess &= CleanUtils.cleanInternalSP(context);
        isSuccess &= CleanUtils.cleanInternalFiles(context);
        isSuccess &= CleanUtils.cleanExternalCache(context);
        File[] var3 = dirs;
        int var4 = dirs.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            File dir = var3[var5];
            isSuccess &= CleanUtils.cleanCustomCache(dir);
        }

        return isSuccess;
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

    public static void pressTwiceExitApp(Activity activity, boolean isBackToLaunch, long exitTimeMsSpace, AppUtils.ExitAppCallBack exitAppCallBack) {
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

    public static void pressTwiceExitApp(Activity activity, String msg, long exitTimeMsSpace, AppUtils.ExitAppCallBack exitAppCallBack) {
        pressTwiceExitApp(activity, true, exitTimeMsSpace, exitAppCallBack);
    }

    public static String getThisAppMd5(Context context, boolean isUpper) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature signature = pi.signatures[0];
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(signature.toByteArray());
            byte[] digest = md.digest();
            return isUpper ? ConvertUtils.bytes2HexString(digest) : StringUtils.changeTOLowerCase(ConvertUtils.bytes2HexString(digest));
        } catch (PackageManager.NameNotFoundException var6) {
            var6.printStackTrace();
            return "null";
        } catch (NoSuchAlgorithmException var7) {
            var7.printStackTrace();
            return "null";
        }
    }

    /**
     * 判断当前应用是否是debug状态
     */
    public static boolean isInDebug(Context context) {
        boolean result = false;
        try {
            ApplicationInfo info = context.getApplicationInfo();
            result = (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public interface ExitAppCallBack {
        void firstAsk();

        void beginExit();
    }

    public static class AppInfo {
        private String name;
        private Drawable icon;
        private String packageName;
        private String packagePath;
        private String versionName;
        private int versionCode;
        private boolean isSystem;

        public Drawable getIcon() {
            return this.icon;
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }

        public boolean isSystem() {
            return this.isSystem;
        }

        public void setSystem(boolean isSystem) {
            this.isSystem = isSystem;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPackageName() {
            return this.packageName;
        }

        public void setPackageName(String packagName) {
            this.packageName = packagName;
        }

        public String getPackagePath() {
            return this.packagePath;
        }

        public void setPackagePath(String packagePath) {
            this.packagePath = packagePath;
        }

        public int getVersionCode() {
            return this.versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return this.versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public AppInfo(String name, Drawable icon, String packageName, String packagePath, String versionName, int versionCode, boolean isSystem) {
            this.setName(name);
            this.setIcon(icon);
            this.setPackageName(packageName);
            this.setPackagePath(packagePath);
            this.setVersionName(versionName);
            this.setVersionCode(versionCode);
            this.setSystem(isSystem);
        }
    }


}
