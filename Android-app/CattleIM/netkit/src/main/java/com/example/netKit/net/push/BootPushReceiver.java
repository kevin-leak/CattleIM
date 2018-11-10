package com.example.netKit.net.push;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.netKit.persistence.Account;

import java.util.Objects;

/**
 * todo 还有问题，启动不了
 */
public class BootPushReceiver extends BroadcastReceiver {

    public final String TAG = "oooo";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Account.isLogin()){
            Log.e(TAG, "onReceive: " + "llllllllllllll");
            PushService.startPush();
        }
    }
}
