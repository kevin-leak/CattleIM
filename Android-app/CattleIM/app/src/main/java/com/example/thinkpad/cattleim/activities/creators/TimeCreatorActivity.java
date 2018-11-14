package com.example.thinkpad.cattleim.activities.creators;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.example.common.app.BaseActivity;
import com.example.common.widget.ToolbarActivity;
import com.example.thinkpad.cattleim.R;

import butterknife.OnClick;

@SuppressLint("Registered")
public class TimeCreatorActivity extends BaseActivity {


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initWindows() {
        super.initWindows();

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_creator_time;
    }

    @OnClick(R.id.appbar)
    void onclick(View view){
        finish();
    }

}
