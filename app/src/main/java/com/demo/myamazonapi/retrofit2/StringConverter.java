package com.demo.myamazonapi.retrofit2;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @author Administrator
 * @name MyAmazonAPI
 * @class name：com.demo.myamazonapi.retrofit2
 * @class describe
 * @time 2018/6/22 11:01
 * @change
 * @class describe
 */

public class StringConverter implements Converter<ResponseBody, String> {
    public static final StringConverter INSTANCE = new StringConverter();

    @Override
    public String convert(ResponseBody value) throws IOException {
        return value.string();
    }

}
