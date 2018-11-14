package com.example.thinkpad.cattleim.activities.creators;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.example.common.app.BaseActivity;
import com.example.common.widget.ToolbarActivity;
import com.example.thinkpad.cattleim.R;
import com.example.thinkpad.cattleim.frags.main.BusinessFragment;
import com.example.thinkpad.cattleim.frags.main.TodoFragment;
import com.example.thinkpad.cattleim.helper.NavHelper;

import butterknife.OnClick;

@SuppressLint("Registered")
public class TaskCreatorActivity extends BaseActivity{


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initWidget() {
        super.initWidget();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_creator_task;
    }

    @OnClick(R.id.appbar)
    void onclick(View view){
        finish();
    }
}
