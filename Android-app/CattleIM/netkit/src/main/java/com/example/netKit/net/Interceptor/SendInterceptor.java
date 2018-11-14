package com.example.netKit.net.Interceptor;

import android.text.TextUtils;
import android.util.Log;

import com.example.netKit.persistence.Account;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * 发送信息的时候我们需要在头部加上token，interceptor 事观察者的模式
 *
 * @author KevinLeak
 */
public class SendInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {

        //获取到客户端发起的请求（chain类可以知道）
        Request oldReq = chain.request();
        //直接操作request发现并没有add 方法， 查看request发现他的构造方法是由一个build处理的
        Request.Builder builder = oldReq.newBuilder();
        String token = InterceptorTools.getToken();
        if (!TextUtils.isEmpty(token)) {
            builder.addHeader(
                    InterceptorTools.COOKIES_KEY,  InterceptorTools.CSRFTOKEN +  "=" + token
                            + "; sessionid=" + Account.getSession_id());
            builder.addHeader(InterceptorTools.X_CSRFTOKEN, token);
        }
        builder.addHeader(InterceptorTools.content_type, "application/json");

        return chain.proceed(builder.build());
    }


}
