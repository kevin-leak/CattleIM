package com.example.thinkpad.cattleim.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.common.app.BaseActivity;
import com.example.thinkpad.cattleim.R;
import com.example.thinkpad.cattleim.frags.assist.NotificationsUtils;
import com.example.thinkpad.cattleim.frags.main.BusinessFragment;
import com.example.thinkpad.cattleim.frags.main.ContactFragment;
import com.example.thinkpad.cattleim.frags.main.ScheduleFragment;
import com.example.thinkpad.cattleim.frags.main.SettingsFragment;
import com.example.thinkpad.cattleim.frags.main.TodoFragment;
import com.example.thinkpad.cattleim.helper.NavHelper;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * 装载 "todo、contact、schedule、business"
 * 1. 将BottomNavigationView用BottomNavListen监控逻辑实现，实现封装
 */
public class MainActivity extends BaseActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        NavHelper.OnNavChangeListener {

    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;

    @BindView(R.id.lay_container)
    FrameLayout layContainer;
    @BindView(R.id.fa_add)
    FloatingActionButton faAdd;

    private NavHelper mHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initWindows() {
        super.initWindows();

        initPermission();
    }

    @Override
    protected void initWidget() {
        super.initWidget();


        bindFragment();
    }

    @Override
    protected void initData() {
        super.initData();

        //初始化设置第一个fragment显示

        // 从底部导中接管我们的Menu，然后进行手动的触发第一次点击
        Menu menu = mNavigation.getMenu();
        // 触发首次选中Home
        menu.performIdentifierAction(R.id.action_todo, 0);
    }

    /**
     * fragment 与tab相互绑定
     */
    private void bindFragment() {

        mHelper = new NavHelper(this, R.id.lay_container, getSupportFragmentManager(), this);
        mHelper.add(R.id.action_todo, new NavHelper.Tab(TodoFragment.class, R.string.action_todo));
        mHelper.add(R.id.action_contact, new NavHelper.Tab<>(ContactFragment.class, R.string.action_contact));
        mHelper.add(R.id.action_schedule, new NavHelper.Tab<>(ScheduleFragment.class, R.string.action_schedule));
        mHelper.add(R.id.action_circle, new NavHelper.Tab<>(BusinessFragment.class, R.string.action_circle));
        mHelper.add(R.id.action_settings, new NavHelper.Tab<>(SettingsFragment.class, R.string.action_settings));


        mNavigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return mHelper.performClickMenu(menuItem.getItemId());
    }


    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("RestrictedApi")
    @Override
    public void OnNavChanged(NavHelper.Tab newTab, NavHelper.Tab oldTab) {
        //此处表现为已经进行fragment的切换处理

        faAdd.setVisibility(View.VISIBLE);

        if (newTab.clx == SettingsFragment.class || newTab.clx == BusinessFragment.class){
            faAdd.setVisibility(View.GONE);
        }
        setStatusTextColor(newTab);


    }

    private void setStatusTextColor(NavHelper.Tab newTab) {
        //字体设置为亮色
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        if (newTab.clx == SettingsFragment.class || newTab.clx == ScheduleFragment.class){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initPermission() {
        String permissions[] = {
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.WAKE_LOCK,
                Manifest.permission.CAMERA,
                Manifest.permission.SYSTEM_ALERT_WINDOW,
                Manifest.permission.RECEIVE_BOOT_COMPLETED
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                //进入到这里代表没有权限.
                Log.e("--------->", "没有权限");
            } else {

                Log.e("--------->", "已经被授权");
            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }


        if (!NotificationsUtils.isNotificationEnabled(this)) {
            final AlertDialog dialog = new AlertDialog.Builder(this).create();
            dialog.show();

            View view = View.inflate(this, R.layout.dialog, null);
            dialog.setContentView(view);

            TextView context = (TextView) view.findViewById(R.id.tv_dialog_context);
            context.setText("检测到您没有打开通知权限，是否去打开");

            TextView confirm = (TextView) view.findViewById(R.id.btn_confirm);
            confirm.setText("确定");
            confirm.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dialog.cancel();
                    Intent localIntent = new Intent();
                    localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (Build.VERSION.SDK_INT >= 9) {
                        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                        localIntent.setData(Uri.fromParts("package", MainActivity.this.getPackageName(), null));
                    } else if (Build.VERSION.SDK_INT <= 8) {
                        localIntent.setAction(Intent.ACTION_VIEW);

                        localIntent.setClassName("com.android.settings",
                                "com.android.settings.InstalledAppDetails");

                        localIntent.putExtra("com.android.settings.ApplicationPkgName",
                                MainActivity.this.getPackageName());
                    }
                    startActivity(localIntent);
                }
            });

            TextView cancel = (TextView) view.findViewById(R.id.btn_off);
            cancel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
        }


    }
}
