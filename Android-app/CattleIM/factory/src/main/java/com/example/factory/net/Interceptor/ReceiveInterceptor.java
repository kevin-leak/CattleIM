package com.example.factory.net.Interceptor;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.common.Common;
import com.example.common.app.Application;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 在开始的时候拦截获取到"Set-Cookie"里面的信息
 */
public class ReceiveInterceptor implements Interceptor {
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {

            HashSet<String> cookies = new HashSet<>(originalResponse.headers("Set-Cookie"));

            //保存文件名字为"config",保存形式为Context.MODE_PRIVATE即该数据只能被本应用读取
            // todo 持久化到model里面
            SharedPreferences.Editor config = Application.getContext()
                    .getSharedPreferences("config", Context.MODE_PRIVATE)
                    .edit();
            config.putStringSet("cookie", cookies);
            Log.e("ooooooooooo", cookies.size() + "");
            config.apply();
        }

        return originalResponse;
    }
}