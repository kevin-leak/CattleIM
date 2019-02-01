package com.example.thinkpad.cattleim.activities;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.common.app.BaseActivity;
import com.example.common.app.BaseFragment;
import com.example.common.tools.UITools;
import com.example.netKit.persistence.Account;
import com.example.thinkpad.cattleim.R;
import com.example.thinkpad.cattleim.frags.account.LoginFragment;
import com.example.thinkpad.cattleim.frags.account.RegisterFragment;
import com.example.thinkpad.cattleim.helper.ViewPageHelper;
import com.yalantis.ucrop.UCrop;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 1. 实现UI切换
 * 2. 实现数据反馈以及切换时候的交互
 * @author KevinLeak
 */
public class AccountActivity extends BaseActivity
        implements ViewPageHelper.ViewPagerCallback {


    private static final String TAG = "Account";
    @BindViews({R.id.btn_login, R.id.btn_register})
    List<TextView> navigationList;
    @BindView(R.id.vp_account)
    ViewPager pagerContainer;
    @BindView(R.id.iv_background)
    ImageView ivBackground;
    @BindView(R.id.fab_go)
    FloatingActionButton fabGo;

    private ViewPageHelper<TextView, BaseFragment> helper;
    private BaseFragment currentFragment;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startPager();

    }


    /**
     * 选择登入和还是注册
     */
    @OnClick(R.id.fab_go)
    void onFloatingAction() {

        currentFragment = helper.getCurrent();
        if (currentFragment == null) {
        } else if (currentFragment instanceof LoginFragment) {
            ((LoginFragment) currentFragment).login();
        } else if (helper.getCurrent() instanceof RegisterFragment) {
            ((RegisterFragment) currentFragment).register();
        }
    }

    /**
     * 开启pager的滑动事件
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void startPager() {
        helper = new ViewPageHelper<>(pagerContainer, this, this);
        helper.addItem(navigationList.get(0), new LoginFragment())
                .addItem(navigationList.get(1), new RegisterFragment());
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        // 为了适配不同的机型，我们动态的设置一下背景的高度
        ivBackground.getLayoutParams().height = (int) (UITools.getScreenHeight(this) * 0.3);
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_account;
    }


    @Override
    public void onChangedFragment(Object currentFragment) {


    }

    /**
     * 登入或者注册成功跳转
     */
    public void trigger(){

//        if (Account.isLogin()){
            Intent intent = new Intent();
            if (!Account.isComplete()){
                intent.setClass(AccountActivity.this, AccountInfoActivity.class);
            }else {
                intent.setClass(AccountActivity.this, MainActivity.class);
            }
            startActivity(intent);
            finish();
//        }

    }



}
