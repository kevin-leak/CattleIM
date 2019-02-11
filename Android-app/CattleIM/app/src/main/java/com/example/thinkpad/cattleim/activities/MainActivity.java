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
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.common.app.BaseActivity;
import com.example.netKit.NetKit;
import com.example.netKit.net.CattleNetWorker;
import com.example.netKit.net.NetInterface;
import com.example.thinkpad.cattleim.R;
import com.example.thinkpad.cattleim.activities.creators.TaskCreatorActivity;
import com.example.thinkpad.cattleim.activities.creators.TimeCreatorActivity;
import com.example.thinkpad.cattleim.frags.assist.NotificationsUtils;
import com.example.thinkpad.cattleim.frags.main.BusinessFragment;
import com.example.thinkpad.cattleim.frags.main.ContactFragment;
import com.example.thinkpad.cattleim.frags.main.ScheduleFragment;
import com.example.thinkpad.cattleim.frags.main.SettingsFragment;
import com.example.thinkpad.cattleim.frags.main.TodoFragment;
import com.example.thinkpad.cattleim.helper.NavHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
    @BindView(R.id.curtain)
    View curtain;
    @BindView(R.id.fb_notice)
    FloatingActionButton fbNotice;
    @BindView(R.id.fb_topic)
    FloatingActionButton fbTopic;
    @BindView(R.id.fb_task)
    FloatingActionButton fbTask;
    @BindView(R.id.fb_time)
    FloatingActionButton fbTime;
    @BindView(R.id.rl_menu)
    RelativeLayout rlMenu;

    private NavHelper mHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void initWindows() {
        super.initWindows();
    }

    @Override
    protected void initWidget() {
        super.initWidget();


        bindFragment();


        initAdd();
    }

    private void initAdd() {
        fbNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TaskCreatorActivity.class);
                startActivity(intent);
                closeAddMenu();
            }
        });

        fbTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAddMenu();
                Intent intent = new Intent(MainActivity.this, TaskCreatorActivity.class);
                startActivity(intent);
            }
        });

        fbTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAddMenu();
                Intent intent = new Intent(MainActivity.this, TaskCreatorActivity.class);
                startActivity(intent);

            }
        });

        fbTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAddMenu();
                Intent intent = new Intent(MainActivity.this, TimeCreatorActivity.class);
                startActivity(intent);
            }
        });


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
        if (curtain.getVisibility() == View.VISIBLE){
            return false;
        }
        return mHelper.performClickMenu(menuItem.getItemId());
    }


    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("RestrictedApi")
    @Override
    public void OnNavChanged(NavHelper.Tab newTab, NavHelper.Tab oldTab) {
        //此处表现为已经进行fragment的切换处理
        faAdd.setVisibility(View.GONE);
        if (newTab.clx == TodoFragment.class || newTab.clx == ScheduleFragment.class) {
            faAdd.setVisibility(View.VISIBLE);
        }

        setStatusTextColor(newTab);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setStatusTextColor(NavHelper.Tab newTab) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        if (newTab.clx == TodoFragment.class || newTab.clx == BusinessFragment.class) {
            //字体设置为亮色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }

    @SuppressLint("RestrictedApi")
    @OnClick(R.id.fa_add)
    void onclick(View view) {

        startAddMenu();

        curtain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAddMenu();
            }
        });
    }

    @SuppressLint("RestrictedApi")
    private void closeAddMenu() {
        faAdd.setVisibility(View.VISIBLE);
        rlMenu.setVisibility(View.GONE);
        curtain.setVisibility(View.GONE);
    }

    /**
     * 开启menu
     */
    @SuppressLint("RestrictedApi")
    private void startAddMenu() {
        faAdd.setVisibility(View.GONE);
        rlMenu.setVisibility(View.VISIBLE);
        curtain.setVisibility(View.VISIBLE);
        curtain.setClickable(true);
    }


    @Override
    protected void onDestroy() {

        NetInterface connect = CattleNetWorker.getConnect();
        connect.getCall().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
        super.onDestroy();
    }
}
