package com.example.netKit.net.Interceptor;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.common.app.Application;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 发送信息的时候我们需要在头部加上token，interceptor 事观察者的模式
 * @author KevinLeak
 */
public class SendInterceptor implements Interceptor {



    @Override
    public Response intercept(Chain chain) throws IOException {

        //获取到客户端发起的请求（chain类可以知道）
        Request oldReq = chain.request();
        //直接操作request发现并没有add 方法， 查看request发现他的构造方法是由一个build处理的
        Request.Builder builder = oldReq.newBuilder();

        String cookies = InterceptorTools.getCookies();

        if (!TextUtils.isEmpty(cookies) && cookies != null) {
            builder.addHeader("cookie", "csrftoken=" + cookies);
            builder.addHeader("X-CSRFtoken", cookies);
        }
        builder.addHeader("Content-Type", "application/json");

        return chain.proceed(builder.build());
    }


}
