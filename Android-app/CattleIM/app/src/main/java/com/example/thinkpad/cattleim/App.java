package com.example.thinkpad.cattleim;

import com.example.common.app.Application;
import com.example.netKit.NetKit;
import com.example.netKit.persistence.Account;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // todo  做好多用户的操作。
        NetKit.initDb();
    }

}
