package com.example.common.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;

/**
 * @author kevin
 * 事件 --> 窗口变化 --> 是否跳转, 数据 --> 界面初始化 --> 控件初始化 --> 数据初始化 --> 处理退出 --> 结束事件
 * 根据来处理：
 *  1. 初始化窗口
 *  2. 不跳转不初始化
 *  3. 获取布局文件，初始化布局控件
 *  4. 初始化数据
 *  5. 处理销毁
 *  6. 处理多个fragment退出时的情况
 * */

public abstract class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initWindows();

        if (initArgs(getIntent().getExtras())) {
            int contentLayoutId = getContentLayoutId();
            setContentView(contentLayoutId);
            initWidget();
            initData();
        }else {
            finish();
        }

    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 初始化控件
     */
    protected void initWidget() {
        ButterKnife.bind(this);

    }

    /**
     * 用来判断是否应该跳转，以及activity的消息传递
     * @param bundle 参数Bundle
     * @return 如果参数正确返回True，错误返回False
     */
    protected boolean initArgs( Bundle bundle) {
        return true;
    }

    /**
     * 初始化窗口
     */
    protected void initWindows() {

    }

    /**
     * @return 返回一个资源文件
     */
    protected abstract int getContentLayoutId();

    @Override
    public boolean onSupportNavigateUp() {
        // 当点击返回键时销毁当前的activity
        finish();
        return super.onSupportNavigateUp();
    }


    /**
     * 处理一个界面有多个fragment的情况
     */
    @Override
    public void onBackPressed() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null && fragments.size() > 0){
            for (Fragment fragment: fragments){
                if (fragment instanceof  com.example.common.app.BaseFragment){
                    // 如果已经处理则会返回正确，如果没有，则有activity 处理
                    if (((BaseFragment) fragment).onBackPressed()){
                        return;
                    }
                }
            }
        }
        super.onBackPressed();
        finish();
    }
}
