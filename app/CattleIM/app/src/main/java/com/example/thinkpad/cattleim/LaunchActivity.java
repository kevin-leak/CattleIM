package com.example.thinkpad.cattleim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.example.common.app.BaseActivity;

public class LaunchActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable(){
            public void run(){
                Intent intent = new Intent(LaunchActivity.this, AccountActivity.class);
                startActivity(intent);
                finish();
            }
        },5000);

    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_launch;
    }
}
