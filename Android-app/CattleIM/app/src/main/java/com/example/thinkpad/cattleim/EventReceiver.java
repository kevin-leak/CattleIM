package com.example.thinkpad.cattleim;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.example.common.utils.NotificationUtils;
import com.example.factory.Factory;
import com.example.factory.presenter.account.AccountHelper;
import com.example.netKit.net.push.contract.PushClientContract;
import com.example.netKit.persistence.Account;
import com.example.thinkpad.cattleim.activities.ConversationActivity;
import com.example.thinkpad.cattleim.activities.creators.TaskCreatorActivity;

/**
 * 此类来接收后端推送的消息
 */
public class EventReceiver extends BroadcastReceiver {
    public final String TAG = "EventReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive: " + "kasdjfklajfm;lk");
//        Log.e(TAG, "onReceive: " + intent.getExtras().getString(PushClientContract.MSG) );
//        Intent sctivity = new Intent(context, TaskCreatorActivity.class);
//        context.startActivity(sctivity);


        Bundle extras = intent.getExtras();

        if (extras != null) {
            int actionMode = extras.getInt(PushClientContract.CMD_ACTION);
            String actionText = extras.getString(Integer.toString(actionMode));
            Log.e(TAG, "onReceive: "  );
            switch (actionMode) {
                case PushClientContract.PUSH_ID:
                    onClientInit(actionText);
                    Log.e(TAG, "onReceive: " + "-------push id 进行绑定-------");
                    break;
                case PushClientContract.MSG:
                    Log.e(TAG, "onReceive: " + "-------消息进行分发-------");
                    onMessageArrived(actionText);
                    break;
            }

        }
    }


    /**
     * 当Id初始化的试试
     *
     * @param cid 设备Id
     */
    private void onClientInit(String cid) {
        // 设置设备Id
        Account.setPushId(cid);
        if (Account.isLogin()) {
            // 账户登录状态，进行一次PushId绑定
            // 没有登录是不能绑定PushId的
            AccountHelper.bindPush(null);
        }
    }

    /**
     * 消息达到时
     *
     * @param message 新消息
     */
    private void onMessageArrived(String message) {
        Log.e(TAG, "onMessageArrived: " + message );

        // todo  通知栏记得优化
        Context applicationContext = App.getInstance().getApplicationContext();
        NotificationUtils notificationUtils = new NotificationUtils(applicationContext,
                R.mipmap.cattle, "消息", "有一条新的消息到了！！");
        notificationUtils.getBuilder().setLargeIcon(BitmapFactory.decodeResource(applicationContext.getResources(), R.mipmap.chat_default));
        notificationUtils.notifyed();

        // 交给Factory处理
        Factory.dispatchPush(message, ConversationActivity.isOpen);
    }
}
