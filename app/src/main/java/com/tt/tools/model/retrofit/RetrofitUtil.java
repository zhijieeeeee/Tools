package com.tt.tools.model.retrofit;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <p> FileName： RetrofitUtil</p>
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2016-03-09 14:42
 */
public class RetrofitUtil {

    private volatile static Retrofit mRetrofit;

    /**
     * 超时时间（单位s）
     */
    private static final int DEFAULT_TIMEOUT = 15;

    public static <T> T createApi(Class<T> service) {
        if (mRetrofit == null) {
            synchronized (RetrofitUtil.class) {
                if (mRetrofit == null) {
                    //创建一个OkHttpClient并设置超时时间
                    OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
                    httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

                    Retrofit.Builder builder = new Retrofit.Builder();
                    builder.baseUrl("http://japi.juhe.cn/")
                            .client(httpClientBuilder.build())
                            .addConverterFactory(GsonConverterFactory.create())//gson解析
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//与rxjava的结合
                            .build();
                    mRetrofit = builder.build();
                }
            }
        }
        return mRetrofit.create(service);
    }
}
