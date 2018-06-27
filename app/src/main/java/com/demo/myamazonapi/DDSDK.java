package com.demo.myamazonapi;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import com.demo.myamazonapi.model.S3Model;
import com.demo.myamazonapi.rx.RxSchedulers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

/**
 * @author Administrator
 * @name MyAmazonAPI
 * @class name：com.demo.myamazonapi
 * @class describe
 * @time 2018/6/20 10:07
 * @change
 * @class describe
 */

public class DDSDK {
    public static String accessKey, secretKey, endpoint, bucket_name;
    private static Context mContext;
    private static DDSDK mDDSDK = new DDSDK();
    private static HashMap<String, String> parameters = new HashMap<>();
    private Handler mHandler = null;

    public DDSDK() {
        Looper looper = Looper.getMainLooper(); //主线程的Looper对象
        mHandler = new Handler(looper);
    }


    public static void init(Context context) {
        mContext = context;
        accessKey = "GXDYC1SINE72M7IMOEG3";
        secretKey = "z2w2T9wLNpdwaUNLJgG8vGRWO1i9stkxH5bMMLRA";
        endpoint = "http://172.21.20.102:7480";
        bucket_name = "bucket_namexiezhenggen";
        parameters.put("x-amz-acl", "public-read-write");
        //S3Model.createBucket( bucket_name, parameters);//bucketName 桶的名称  创建桶


        /** *///获取桶
        /** //提交文件*/
    }

    public static void createBucket() {
        S3Model.createBucket(bucket_name, parameters).compose(RxSchedulers.<ResponseBody>switchObservableThread())
               .subscribe(new DisposableObserver<ResponseBody>() {
                   @Override
                   public void onNext(ResponseBody body) {
                       if (body != null) {
                           String base64 = null;
                           try {
                               base64 = Base64.getEncoder().encodeToString(body.bytes());
                           } catch (IOException e) {
                               e.printStackTrace();
                           }
                           LogUtils.i("createBucket   o=" + base64);
                       }

                   }

                   @Override
                   public void onError(Throwable e) {
                       LogUtils.i("createBucket   e=" + e);
                   }

                   @Override
                   public void onComplete() {
                       LogUtils.i("createBucket   onComplete");
                   }
               });
    }

    public static void getBucket() {
        S3Model.getBucket(bucket_name).compose(RxSchedulers.<ResponseBody>switchObservableThread())
               .subscribe(new DisposableObserver<ResponseBody>() {
                   @Override
                   public void onNext(ResponseBody body) {
                       String base64 = null;
                       try {
                           base64 = Base64.getEncoder().encodeToString(body.bytes());
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                       LogUtils.i("getBucket   obase64=" + base64);
                   }

                   @Override
                   public void onError(Throwable e) {
                       LogUtils.i("getBucket   e=" + e);
                   }

                   @Override
                   public void onComplete() {
                       LogUtils.i("getBucket   onComplete");
                   }
               });
    }

    public void putBucketObject() {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "IMG.jpg");
        LogUtils.i("createBucket   file=" + file.getAbsolutePath() + "  file=" + file.exists() + "  getName=" + file.getName());
        S3Model.putBucketObject("image", bucket_name, file.getName(), file.getAbsolutePath(), parameters).compose(RxSchedulers.<ResponseBody>switchObservableThread())
               .subscribe(new DisposableObserver<ResponseBody>() {
                   @Override
                   public void onNext(ResponseBody o) {
                       LogUtils.i("createBucket   o=" + o);
                   }

                   @Override
                   public void onError(Throwable e) {
                       LogUtils.i("createBucket   e=" + e);
                   }

                   @Override
                   public void onComplete() {
                       LogUtils.i("createBucket   onComplete");
                   }
               });
    }
}
