package com.example.thinkpad.cattleim.activities.contact;

import android.content.Context;
import android.content.Intent;

import com.example.common.app.BaseActivity;
import com.example.thinkpad.cattleim.R;
import com.example.thinkpad.cattleim.activities.SearchActivity;

public class CreateInfoActivity extends BaseActivity {

    public static void show(Context context) {
        context.startActivity(new Intent(context, CreateInfoActivity.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_create_info;
    }
}
