package com.example.thinkpad.cattleim.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.common.app.BaseActivity;
import com.example.thinkpad.cattleim.R;
import com.example.thinkpad.cattleim.frags.main.BusinessFragment;
import com.example.thinkpad.cattleim.frags.main.ContactFragment;
import com.example.thinkpad.cattleim.frags.main.ScheduleFragment;
import com.example.thinkpad.cattleim.frags.main.TodoFragment;
import com.example.thinkpad.cattleim.helper.NavHelper;

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
    protected void initWidget() {
        super.initWidget();

        //初始化设置第一个fragment显示

        // 从底部导中接管我们的Menu，然后进行手动的触发第一次点击
        Menu menu = mNavigation.getMenu();
        // 触发首次选中Home
        menu.performIdentifierAction(R.id.action_todo, 0);

        bindFragment();
    }

    /**
     * fragment 与tab相互绑定
     */
    private void bindFragment() {

        mHelper = new NavHelper(this,R.id.lay_container,getSupportFragmentManager(), this);
        mHelper.add(R.id.action_todo, new NavHelper.Tab(TodoFragment.class, R.string.action_todo))
                .add(R.id.action_contact, new NavHelper.Tab<>(ContactFragment.class, R.string.action_contact))
                .add(R.id.action_schedule, new NavHelper.Tab<>(ScheduleFragment.class, R.string.action_schedule))
                .add(R.id.action_circle, new NavHelper.Tab<>(BusinessFragment.class, R.string.action_circle));


        mNavigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return mHelper.performClickMenu(menuItem.getItemId());
    }


    @Override
    public void OnNavChanged(NavHelper.Tab newTab, NavHelper.Tab oldTab) {
        //此处表现为已经进行fragment的切换处理
    }
}
