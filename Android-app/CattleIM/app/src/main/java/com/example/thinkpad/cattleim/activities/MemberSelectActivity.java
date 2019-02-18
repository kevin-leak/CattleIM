package com.example.thinkpad.cattleim.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.example.common.app.BaseActivity;
import com.example.thinkpad.cattleim.R;
import com.example.thinkpad.cattleim.activities.creators.TimeCreatorActivity;

@SuppressLint("Registered")
public class MemberSelectActivity extends BaseActivity {


    public static void show(Context context) {
        context.startActivity(new Intent(context, MemberSelectActivity.class));
    }

    /**
     * @return 返回一个资源文件
     */
    @Override
    protected int getContentLayoutId() {
        return R.layout.hello;
    }
}
