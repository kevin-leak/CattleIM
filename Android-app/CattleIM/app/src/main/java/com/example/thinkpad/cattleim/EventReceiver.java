package com.example.thinkpad.cattleim;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.thinkpad.cattleim.activities.creators.TaskCreatorActivity;

/**
 * 此类来接收后端推送的消息
 */
public class EventReceiver extends BroadcastReceiver {
    public final String TAG = "EventReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
//        Log.e(TAG, "onReceive: " + intent.getStringExtra("msg") );
//        Intent sctivity = new Intent(context, TaskCreatorActivity.class);
//        context.startActivity(sctivity);
    }
}
