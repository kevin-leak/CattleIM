package com.example.netKit.db;

import android.util.Log;

public class DbTest {

    public final User user;

    public DbTest() {
        user = new User();
        user.setName("kk");
        user.setId("ppp");
    }

    public static void getInfo(User user){
        Log.e("user", user.getName());
    }
}
