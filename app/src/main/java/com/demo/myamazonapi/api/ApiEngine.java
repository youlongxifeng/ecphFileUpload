package com.demo.myamazonapi.api;

import com.demo.myamazonapi.DDSDK;
import com.demo.myamazonapi.api.interceptor.NullOnEmptyConverterFactory;
import com.demo.myamazonapi.api.interceptor.S3Auth;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Administrator
 * @name MyAmazonAPI
 * @class name：com.demo.myamazonapi.api
 * @class describe
 * @time 2018/6/20 10:06
 * @change
 * @class describe
 */

public class ApiEngine {
    private volatile static ApiEngine apiEngine;
    private Retrofit retrofit, retrofitAmazon;
    private OkHttpClient clientAmazon;

    private ApiEngine() {
        HttpLoggingInterceptor.Logger mLog = new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //Log.i("TAG", "message==" + message);
            }
        };

        //日志拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //缓存
        int size = 1024 * 1024 * 100;
       /* File cacheFile = new File(DDSDK.getInstance().getContext().getCacheDir(), "OkHttpCache");
        Cache cache = new Cache(cacheFile, size);*/


        clientAmazon = new OkHttpClient.Builder()
                .connectTimeout(12, TimeUnit.SECONDS)
                .writeTimeout(12, TimeUnit.SECONDS)
                .writeTimeout(12, TimeUnit.SECONDS)
                //.cookieJar(new CookiesManager())//在OkHttpClient创建时，传入这个CookieJar的实现，就能完成对Cookie的自动管理了
                .addNetworkInterceptor(new S3Auth())// 将有网络拦截器当做网络拦截器添加
                .retryOnConnectionFailure(true)
                .addInterceptor(loggingInterceptor)
                //.cache(cache)
                .build();


        retrofitAmazon = new Retrofit.Builder()
                .baseUrl(DDSDK.endpoint)
                .client(clientAmazon)
                .addConverterFactory(NullOnEmptyConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();



    }

    public static ApiEngine getInstance() {
        if (apiEngine == null) {
            synchronized (ApiEngine.class) {
                if (apiEngine == null) {
                    apiEngine = new ApiEngine();
                }
            }
        }
        return apiEngine;
    }


    public ApiAmazonService getApiAmazonService() {
        return retrofitAmazon.create(ApiAmazonService.class);
    }
}
