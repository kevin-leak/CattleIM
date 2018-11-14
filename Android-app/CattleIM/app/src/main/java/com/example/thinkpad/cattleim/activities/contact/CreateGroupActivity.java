package com.example.thinkpad.cattleim.activities.contact;

import android.content.Context;
import android.content.Intent;

import com.example.common.app.BaseActivity;
import com.example.thinkpad.cattleim.R;

public class CreateGroupActivity extends BaseActivity {


    public static void show(Context context) {
        context.startActivity(new Intent(context, CreateGroupActivity.class));
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_create_group;
    }
}
