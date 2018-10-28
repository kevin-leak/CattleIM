package com.example.thinkpad.cattleim;

import com.example.common.app.Application;
import com.example.netKit.NetKit;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NetKit.initDb();
    }

}
