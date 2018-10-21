package com.example.factory.net.Interceptor;

import android.accounts.Account;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.example.common.app.Application;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 发送信息的时候我们需要在头部加上token，interceptor 事观察者的模式
 */
public class SendInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {

        //获取到客户端发起的请求（chain类可以知道）
        Request oldReq = chain.request();
        //直接操作request发现并没有add 方法， 查看request发现他的构造方法是由一个build处理的
        Request.Builder builder = oldReq.newBuilder();

        //TODO 后期对其进行封装到Account 类中进行统一的管理
        HashSet<String> cookiesSet = (HashSet<String>) Application.getContext().getSharedPreferences("config",
                Context.MODE_PRIVATE).getStringSet("cookie", null);
        if (cookiesSet != null) {
            for (String cookie : cookiesSet) {
                builder.addHeader("Cookie", cookie);
                Log.e("cookie", cookie);
            }
        }
        builder.addHeader("Content-Type", "application/json");

        return chain.proceed(builder.build());
    }
}
