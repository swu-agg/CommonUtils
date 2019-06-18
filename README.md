# CommonUtils
工具代码库

initial code 


// 单例模式 public class Singleton { // 懒汉式双重检查锁方式: private static volatile Singleton singleton = null;

private Singleton() {
}

public static Singleton getSingleton() {
    if (singleton == null) {
        synchronized (Singleton.class) {
            if (singleton == null) {
                singleton = new Singleton();
            }
        }
    }
    return singleton;
}
}

/**

create by agg
2019/02/15 */
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
