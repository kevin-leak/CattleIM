package com.example.netKit.net.Interceptor;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.common.app.Application;
import com.example.common.utils.StringsUtils;
import com.example.netKit.persistence.Account;

import java.util.List;

import okhttp3.Response;

/**
 * 持久化token
 */
public class InterceptorTools {

    /**
     * 获取服务端原始的cookies
     */
    public static final String SET_COOKIE = "Set-Cookie";
    public static String cookies; // 加工过的cookies

    // 用来持久化和拼接cookies的  键
    public static final String X_CSRFTOKEN = "X-CSRFtoken";
    public static final String content_type = "Content-Type";
    public static final String COOKIES_KEY = "cookie";
    public static final String CSRFTOKEN = "csrftoken";

    // 用来持久化和拼接cookies的  值
    public static String csrftoken;
    public static String session;



    public static void saveCookies(Response originalResponse) {
        List<String> headers = originalResponse.headers(SET_COOKIE);

        if (!headers.isEmpty()) {

            // 由于不同的浏览器返回的可能是list，或者就一条字符串，这里统一转化为字符串再处理
            if (headers.size() == 1) {
                String[] split = headers.get(0).split(";");
                csrftoken = split[0].split("=")[1];
                cookies = headers.get(0);
            } else {
                csrftoken = headers.get(0).split("=")[1];
                cookies = StringsUtils.ListToString(headers);
            }

            //获取session
            if (cookies.contains("sessionid=")){
                session = cookies.split("sessionid=")[1].split(";")[0];
                Account.setSession_id(session);
            }

            //保存文件名字为"config",保存形式为Context.MODE_PRIVATE即该数据只能被本应用读取
            SharedPreferences.Editor config = Application.getInstance()
                    .getSharedPreferences("config", Context.MODE_PRIVATE)
                    .edit();
            config.putString(CSRFTOKEN, csrftoken);
            config.apply();
        }
    }


    public static String getToken() {
        SharedPreferences config = Application.getInstance().getSharedPreferences("config",
                Context.MODE_PRIVATE);
        return config.getString(CSRFTOKEN, "");
    }
}
