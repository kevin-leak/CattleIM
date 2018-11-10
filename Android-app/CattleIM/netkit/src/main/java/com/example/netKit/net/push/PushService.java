package com.example.netKit.net.push;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.netKit.NetKit;
import com.example.netKit.persistence.Account;

import static android.support.constraint.Constraints.TAG;

public class PushService extends Service implements PushClient.PushListener {

    private Intent intent;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // todo 获取intent的值，进行一个判断是否，应该开启，或者说是否应该断开
        if (Account.isLogin()){
            PushClient.getInstance().start(this);
        }
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onCreate() {
        super.onCreate();

        initBroadcastIntent();
    }


    private void initBroadcastIntent() {
        if (intent == null) {
            intent = new Intent();
            intent.setAction("com.example.netKit.net.push.PushService");
        }
    }


    @Override
    public void reStart() {
        Log.e(TAG, "reStart: " + "lasdkl");
    }

    @Override
    public void messageArrival(String message) {

        //创建Intent
        intent.putExtra(PushContract.MSG, message);
        //发送广播
        sendBroadcast(intent);
    }

    public static void startPush() {
        NetKit.app().startService(new Intent(NetKit.app(), PushService.class));
    }
}
