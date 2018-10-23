package com.example.common.app;

import android.content.Context;

/**
 * 做为一个维护应用程序全局状态的基类
 * @author KevinLeak
 */
public class Application extends android.app.Application{

    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;
    }

    public static Application getInstance() {
        return application;
    }



}
