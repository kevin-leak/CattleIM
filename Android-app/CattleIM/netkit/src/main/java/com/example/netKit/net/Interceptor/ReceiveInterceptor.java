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

        InterceptorTools.saveCookies(originalResponse);

        return originalResponse;
    }
}