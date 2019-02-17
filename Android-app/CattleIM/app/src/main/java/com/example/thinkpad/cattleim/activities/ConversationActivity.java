package com.example.thinkpad.cattleim.activities;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;

import com.example.common.app.BaseFragment;
import com.example.common.factory.profile.Profile;
import com.example.common.widget.ToolbarActivity;
import com.example.netKit.db.Conversation;
import com.example.netKit.db.Event;
import com.example.netKit.db.Group;
import com.example.netKit.db.Tag;
import com.example.thinkpad.cattleim.R;
import com.example.thinkpad.cattleim.frags.chat.ChatFragment;
import com.example.thinkpad.cattleim.frags.chat.ChatGroupFragment;
import com.example.thinkpad.cattleim.frags.chat.ChatTagFragment;
import com.example.thinkpad.cattleim.frags.chat.ChatUserFragment;
import com.example.thinkpad.cattleim.frags.main.todo.EventFragment;

import java.util.List;

public class ConversationActivity extends ToolbarActivity {

    public static final String TAG = "ConversationActivity";


    public static final String KEY_RECEIVER_ID = "KEY_RECEIVER_ID";
    public static final String KEY_MESSAGE_TYPE = "KEY_MESSAGE_TYPE";
    public static final int TYPE_USER = Conversation.TYPE_SIMPLE;
    public static final int TYPE_GROUP = Conversation.TYPE_GROUP;
    public static final int TYPE_TAG = 2;
    private static EventFragment eventFragment;
    private static Event event;


    private String receiverID;
    private int messageType;


    ChatFragment fragment = null;

    /**
     * 判断是否是打开状态
     */
    public static boolean isOpen = false;

    public static void show(Context context, String userId) {

        Intent intent = new Intent(context, ConversationActivity.class);
        intent.putExtra(KEY_RECEIVER_ID, userId);
        intent.putExtra(KEY_MESSAGE_TYPE, TYPE_USER);
        context.startActivity(intent);
        isOpen = true;
    }

    /**
     * @param user 在用户的列表的地方点击，传入好友的信息对象
     */
    public static void show(Context context, Profile user) {

        Intent intent = new Intent(context, ConversationActivity.class);
        intent.putExtra(KEY_RECEIVER_ID, user.getId());
        intent.putExtra(KEY_MESSAGE_TYPE, TYPE_USER);
        context.startActivity(intent);
        isOpen = true;
    }

    /**
     * @param group 传入的群对象
     */
    public static void show(Context context, Group group) {

        Intent intent = new Intent(context, ConversationActivity.class);
        intent.putExtra(KEY_RECEIVER_ID, group.getId());
        intent.putExtra(KEY_MESSAGE_TYPE, TYPE_GROUP);
        context.startActivity(intent);
        isOpen = true;
    }

    /**
     * @param tag 传入tag 对象
     */
    public static void show(Context context, Tag tag) {

        Intent intent = new Intent(context, ConversationActivity.class);
        intent.putExtra(KEY_RECEIVER_ID, tag.getId());
        intent.putExtra(KEY_MESSAGE_TYPE, TYPE_TAG);
        context.startActivity(intent);
        isOpen = true;
    }

    public static void show(EventFragment eventFragment, Event event) {
        ConversationActivity.eventFragment = eventFragment;
        ConversationActivity.event = event;
        Intent intent = new Intent(eventFragment.getContext(), ConversationActivity.class);
        intent.putExtra(KEY_RECEIVER_ID, event.getForSome());
        intent.putExtra(KEY_MESSAGE_TYPE, event.getType());
        eventFragment.startActivity(intent);
        isOpen = true;
    }


    @Override
    protected boolean initArgs(Bundle bundle) {
        receiverID = bundle.getString(KEY_RECEIVER_ID);
        messageType = bundle.getInt(KEY_MESSAGE_TYPE);
        Log.e(TAG, "initArgs: " + receiverID + " " +messageType );
        return !TextUtils.isEmpty(receiverID);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void initWindows() {
        super.initWindows();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void initWidget() {
        super.initWidget();
        // 设置下边线

        setTitle("");
        switch (messageType){
            case TYPE_USER:
                fragment = new ChatUserFragment();
                break;
            case TYPE_GROUP:
                fragment = new ChatGroupFragment();
                break;
            default:
                fragment = new ChatTagFragment();
                break;
        }

        // 从Activity传递参数到Fragment中去
        if (fragment != null){
            Bundle bundle = new Bundle();
            bundle.putString(KEY_RECEIVER_ID, receiverID);
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_container, fragment)
                    .commit();
        }
    }

    @Override
    public void finish() {
        isOpen = false;

        // 进行消息已经读取的操作 todo, 如果是群的处理方式
        if (fragment != null){
            fragment.read(receiverID, event);
        }

//         作为后备处理
//        if (eventFragment != null){
//            eventFragment.read(event);
//        }
        super.finish();
    }

    /**
     * @return 返回一个资源文件
     */
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_message;
    }

    public static boolean isAPPBroughtToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (topActivity.getClassName().equals(context.getClass().getName())) {

                return true;
            }else{
            }
        }
        return false;
    }



}
