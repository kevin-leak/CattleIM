package com.example.thinkpad.cattleim.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.common.app.BaseActivity;
import com.example.thinkpad.cattleim.R;

@SuppressLint("Registered")
public class PersonActivity extends BaseActivity {


    private String userId;

    public static void show(Context context, String userId) {
        context.startActivity(new Intent(context, PersonActivity.class).putExtra("userId", userId));
    }

    /**
     * @return 返回一个资源文件
     */
    @Override
    protected int getContentLayoutId() {
        return R.layout.hello;
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        userId = bundle.getString("userId");
        if (!TextUtils.isEmpty(userId)){

        }
        return super.initArgs(bundle);
    }
}
