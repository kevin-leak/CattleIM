package com.example.netKit.net.push;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.netKit.NetKit;
import com.example.netKit.net.push.contract.PushClientContract;
import com.example.netKit.persistence.Account;

public class PushService extends Service implements PushClient.PushListener {

    final static String TAG = "PushService";

    private Intent intent;


    /**
     * 开启推送服务
     */
    public static void startPush() {
        NetKit.app().startService(new Intent(NetKit.app(), PushService.class));
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // todo 获取intent的值，进行一个判断是否，应该开启，或者说是否应该断开
        if (Account.isLogin() & Account.isComplete()) {
            PushClient.getInstance().start(this);
        }
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onCreate() {
        super.onCreate();

        initBroadcastIntent();
    }


    /**
     * 初始化广播，用来当消息到达时通过广播的方式转发到其他包
     */
    private void initBroadcastIntent() {
        if (intent == null) {
            intent = new Intent();
            intent.setAction("com.example.netKit.net.push.PushService");
        }

        // 这里必须设置需要广播到的包名，否则无效
        intent.setPackage("com.example.thinkpad.cattleim");
    }


    /**
     * 推送开启的回调
     *
     * @param pushId
     */
    @Override
    public void onStart(String pushId) {
        // todo  进行消息的发送， 用来绑定push id
        sendPushId(pushId);
    }

    /**
     * @param pushId 后端传入的消息
     *             用于传送push id
     */
    private void sendPushId(String pushId) {
        sendMessage(pushId, PushClientContract.PUSH_ID);
    }

    @Override
    public void reStart(String text) {
        // todo  进行消息的发送， 用来绑定push id
        sendPushId(text);
    }

    @Override
    public void messageArrival(String message) {

        sendMessage(message, PushClientContract.MSG);
    }

    /**
     *
     * 用于消息发送
     * @param message 后端发来的文本消息
     * @param msg 用来标记传输的广播的消息
     */
    private void sendMessage(String message, int msg) {
        Log.e(TAG, "sendMessage: " + msg );
        Bundle bundle = new Bundle();
        bundle.putInt(PushClientContract.CMD_ACTION, msg);
        bundle.putString(Integer.toString(msg), message);
        intent.putExtras(bundle);
        //发送广播
        sendBroadcast(intent);
    }
}
