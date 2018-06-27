package com.demo.myamazonapi;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.demo.myamazonapi.api.interceptor.S3Auth;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public static String accessKey, secretKey, endpoint, bucket_name;
    OkHttpClient clientAmazon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
   /*   new Thread(new Runnable() {
          @Override
          public void run() {
              com.dd.sdk.DDSDK.getInstance().init(MainActivity.this, "f2a9d153188d87e18adc233ca8ee30da", "564f939a8f8a5befa67d62bdf79e6fa5", "test20160822001", "test.sdk.door.doordu.com", 8018, MainActivity.this);
              com.dd.sdk.DDSDK.getInstance().amazonCloudinit(endpoint,  accessKey,secretKey);
          }
      }).start();*/

        //DDSDK.getinstance().init(MainActivity.this);
        findViewById(R.id.textid).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                 // DDSDK.init(MainActivity.this);
                //  DDSDK.createBucket();
               // DDSDK.getBucket();
              //  clientAmazon();
              //  createBucket("bucketName3");



                clientAmazon();
            }
        });
        accessKey = "GXDYC1SINE72M7IMOEG3";
        secretKey = "z2w2T9wLNpdwaUNLJgG8vGRWO1i9stkxH5bMMLRA";
        endpoint = "http://172.21.20.102:7480";
        this.clientAmazon = new OkHttpClient().newBuilder().addInterceptor(new S3Auth(accessKey, secretKey)).build();


    }


    private void clientAmazon() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("x-amz-acl", "public-read-write");
                LogUtils.i("response======clientAmazon=");
                File file = new File(Environment.getExternalStorageDirectory() + File.separator + "IMG.jpg");
                putBucketObject("bucket_name", "IMG.jpg", file.getAbsolutePath(), parameters);
            }
        }).start();

    }

    private void createBucket(String bucketName) {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("x-amz-acl", "public-read-write");
        HttpUrl.Builder urlBuilder = HttpUrl.parse(endpoint).newBuilder().addPathSegment(bucketName);

        for (Map.Entry<String, String> entity : parameters.entrySet()) {
            urlBuilder.addQueryParameter(entity.getKey(), entity.getValue());
        }

        Request request = new Request.Builder().url(urlBuilder.build()).build();
        try {
            Response response = clientAmazon.newCall(request).execute();
            LogUtils.i("response=======" + response);

        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.i("response======e=" + e);
        }
    }


    public void putBucketObject(String bucketName, String objectName, String filePath, Map<String, String> parameters) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(endpoint).newBuilder().addPathSegment(bucketName)
                                            .addPathSegment(objectName);

        for (Map.Entry<String, String> entity : parameters.entrySet()) {
            urlBuilder.addQueryParameter(entity.getKey(), entity.getValue());
        }
        File file = new File(filePath);
        RequestBody fileBody = RequestBody.create(null, file);
        Request request = new Request.Builder().put(fileBody).url(urlBuilder.build())
                                               .build();
        try {
            Response response = clientAmazon.newCall(request).execute();
            LogUtils.i("response=======" + response);
            LogUtils.i("response=======" + response.message());
            LogUtils.i("response=======" + response.request().url());
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.i("response======e=" + e);
        }
    }


}
