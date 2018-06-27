package com.demo.myamazonapi.model;

import com.demo.myamazonapi.api.ApiEngine;

import java.io.File;
import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * @author Administrator
 * @name MyAmazonAPI
 * @class name：com.demo.myamazonapi.model
 * @class describe
 * @time 2018/6/20 10:11
 * @change
 * @class describe
 */

public class S3Model {

    public static Observable<ResponseBody> createBucket(String bucketName, HashMap<String, String> parameters) {
        return ApiEngine.getInstance().getApiAmazonService().createBucket( bucketName, parameters);//bucketName 桶的名称  创建桶
    }

    public static Observable<ResponseBody> getBucket( String bucket_name ) {
        return ApiEngine.getInstance().getApiAmazonService().getBucket(bucket_name);//bucketName 桶的名称  创建桶
    }


    public static Observable<ResponseBody> putBucketObject(String fileType,  String bucket_name, String fileName, String fileAddress, HashMap<String, String> parameters) {
        File file=new File(fileAddress);
        RequestBody requestBody= RequestBody.create(MediaType.parse("binary/octet-stream"), file);

        MultipartBody.Part part = MultipartBody.Part.createFormData(fileType,file.getName(),requestBody);

        return ApiEngine.getInstance().getApiAmazonService().putBucketObject(bucket_name, fileName, part, parameters);//bucketName 桶的名称  创建桶
    }
}
