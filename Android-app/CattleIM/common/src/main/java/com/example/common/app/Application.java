package com.example.common.app;

import android.content.Context;

public class Application extends android.app.Application{

    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;
    }

    public static Context getContext() {
        return application;
    }

}
