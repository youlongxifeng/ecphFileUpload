package com.demo.myamazonapi;

import com.demo.myamazonapi.api.interceptor.S3Auth;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Administrator
 * @name MyAmazonAPI
 * @class name：com.demo.myamazonapi
 * @class describe
 * @time 2018/6/20 15:50
 * @change
 * @class describe
 */

public class S3AuthTest {

    @Test
    public void testAtestuth(){
        //String date1 = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneId.of("GMT")));
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.UK);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
        String date = sdf.format(System.currentTimeMillis());
        System.out.println(date);
      //  System.out.println(date1);
        S3Auth auth = new S3Auth("GXDYC1SINE72M7IMOEG3","z2w2T9wLNpdwaUNLJgG8vGRWO1i9stkxH5bMMLRA");
        System.out.println(auth.sign("GET",date,"/%2F"));

    }
}
