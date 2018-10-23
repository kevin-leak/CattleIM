package com.example.netKit.net.Interceptor;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.common.app.Application;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 在开始的时候拦截获取到"Set-Cookie"里面的信息
 *
 * @author KevinLeak
 */
public class ReceiveInterceptor implements Interceptor {
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        if (originalResponse.request().body() != null) {

        }


        List<String> headers = originalResponse.headers("Set-Cookie");

        if (!headers.isEmpty()) {
            String cookies;

            // 调试发现 heads就是一个字符串
            if (headers.size() == 1) {
                String[] split = headers.get(0).split(";");
                cookies = split[0].split("=")[1];
            } else {
                cookies = headers.get(0).split("=")[1];
            }

            //保存文件名字为"config",保存形式为Context.MODE_PRIVATE即该数据只能被本应用读取
            // todo 持久化到model里面
            SharedPreferences.Editor config = Application.getInstance()
                    .getSharedPreferences("config", Context.MODE_PRIVATE)
                    .edit();
            config.putString("cookies", cookies);
            config.apply();
        }

        return originalResponse;
    }
}