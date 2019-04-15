package com.agg.common.helper;

import android.content.Context;
import android.text.TextUtils;

import com.agg.common.utils.NetworkConnectedUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * create by agg
 * 2019/01/24
 */
public class OkHttp3Helper {

    /**
     * 参考：https://www.jianshu.com/p/ea2055db3dd3
     * https://www.jianshu.com/p/cf59500990c7
     * 云端响应头拦截器，用来配置缓存策略
     *
     * @param context 上下文
     * @return OkHttpClient
     */
    public static OkHttpClient getOkHttpClient(Context context) {
        final Context applicationContext = context.getApplicationContext();
        Interceptor interceptor = new Interceptor() {
            // 读取缓存时，就不会走tintercep回调
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (NetworkConnectedUtils.notNetworkConnected(applicationContext)) {
                    request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                }
                okhttp3.Response response = chain.proceed(request);
                if (NetworkConnectedUtils.notNetworkConnected(applicationContext)) {
                    return response.newBuilder() // 长缓存,有效期为7天,,,,,设置也没有效果，因为在上面request已经设置了
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    String cacheControl = request.cacheControl().toString();
                    if (TextUtils.isEmpty(cacheControl))
                        cacheControl = "public,max-age=600"; // 短缓存,有效期10分钟
                    return response.newBuilder()
                            .header("Cache-Control", cacheControl)
                            .removeHeader("Pragma") // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                }
            }
        };
        File cacheFile = new File(applicationContext.getCacheDir(), "responses");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 20); // 缓存大小20MB
        return new OkHttpClient.Builder()
                // addNetworkInterceptor添加的是网络拦截器，他会在在request和resposne是分别被调用一次，
                // addInterceptor添加的是application拦截器，他只会在response被调用一次.
                .addInterceptor(interceptor) // 设置拦截器。（不添加的话没网络就没有缓存）
                .addNetworkInterceptor(interceptor) // 设置拦截器。
                .cache(cache) // 设置缓存。
                .retryOnConnectionFailure(true)// 设置重试。连接失败后是否重新连接,默认重试一次，若需要重试N次，则要实现拦截器。
                // 设置超时。square官方建议timeout
                .connectTimeout(10, TimeUnit.SECONDS) // 尽量设置得小一些(比如10s),这样可以减小弱网环境下手机的负载，同时对于用户体验也有好处
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    public static OkHttpClient getOkHttpClientByTime(Context context, int time) {
        final Context applicationContext = context.getApplicationContext();
        final int finalTime = time;
        Interceptor interceptor = new Interceptor() {
            // 读取缓存时，就不会走intercept回调
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (NetworkConnectedUtils.notNetworkConnected(applicationContext)) { // 没有网络
                    request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                }
                okhttp3.Response response = chain.proceed(request);
                if (NetworkConnectedUtils.notNetworkConnected(applicationContext)) { // 没有网络
                    return response.newBuilder() // 长缓存,有效期为7天
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    return response.newBuilder()
                            .header("Cache-Control", "public,max-age=" + finalTime)
                            .removeHeader("Pragma") // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                }
            }
        };

        File cacheFile = new File(applicationContext.getCacheDir(), "responses");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 20); // 缓存大小20MB
        return new OkHttpClient.Builder()
                // addNetworkInterceptor添加的是网络拦截器，他会在在request和resposne是分别被调用一次，
                // addInterceptor添加的是application拦截器，他只会在response被调用一次.
                .addInterceptor(interceptor)
                .addNetworkInterceptor(interceptor) // 设置拦截器。
                .cache(cache) // 设置缓存。
                .retryOnConnectionFailure(true)// 设置重试。连接失败后是否重新连接,默认重试一次，若需要重试N次，则要实现拦截器。
                // 设置超时。square官方建议timeout
                .connectTimeout(10, TimeUnit.SECONDS) // 尽量设置得小一些(比如10s),这样可以减小弱网环境下手机的负载，同时对于用户体验也有好处
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

}

