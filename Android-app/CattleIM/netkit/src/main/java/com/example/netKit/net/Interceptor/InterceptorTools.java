package com.example.netKit.net.Interceptor;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.common.app.Application;

import java.util.List;

import okhttp3.Response;

/**
 * 持久化token
 */
public class InterceptorTools {

    private static final String SET_COOKIE = "Set-Cookie";

    private static final String COOKIES_KEY = "cookies";


    public static void saveCookies(Response originalResponse) {
        List<String> headers = originalResponse.headers(SET_COOKIE);

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
            SharedPreferences.Editor config = Application.getInstance()
                    .getSharedPreferences("config", Context.MODE_PRIVATE)
                    .edit();
            config.putString(COOKIES_KEY, cookies);
            config.apply();
        }
    }


    public static String getCookies() {
        SharedPreferences config = Application.getInstance().getSharedPreferences("config",
                Context.MODE_PRIVATE);
        return config.getString(COOKIES_KEY, null);
    }
}
