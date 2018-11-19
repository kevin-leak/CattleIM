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
import com.example.thinkpad.cattleim.R;
import com.example.thinkpad.cattleim.frags.account.Login;
import com.example.thinkpad.cattleim.frags.account.Register;
import com.example.thinkpad.cattleim.helper.ViewPageHelper;
import com.yalantis.ucrop.UCrop;

import java.util.List;
import java.util.Objects;

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
    @BindView(R.id.profile_avatar)
    CircleImageView profileAvatar;
    @BindView(R.id.fab_go)
    FloatingActionButton fabGo;
    @BindView(R.id.et_user_name)
    EditText etUserName;

    private ViewPageHelper<TextView, BaseFragment> helper;
    private String mAvatarPath;
    private BaseFragment currentFragment;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startPager();

    }

    /**
     * 设置头像选择器
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick(R.id.profile_avatar)
    void onAvatarView() {
        currentFragment = helper.getCurrent();
        Log.e(TAG, "onAvatarView: sdafg" );
        if (Objects.nonNull(currentFragment)){
            ((Register) currentFragment).getAvatar();
        }

    }

    /**
     * 选择登入和还是注册
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick(R.id.fab_go)
    void onFloatingAction() {

        currentFragment = helper.getCurrent();
        if (Objects.isNull(currentFragment)) {
        } else if (currentFragment instanceof Login) {
            ((Login) currentFragment).login();
        } else if (helper.getCurrent() instanceof Register) {
            String username = etUserName.getText().toString();
            ((Register) currentFragment).register(mAvatarPath, username);
        }
    }

    /**
     * 开启pager的滑动事件
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void startPager() {
        helper = new ViewPageHelper<>(pagerContainer, this, this);
        helper.addItem(navigationList.get(0), new Login())
                .addItem(navigationList.get(1), new Register());
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

        if (currentFragment instanceof Register) {
            profileAvatar.setVisibility(View.VISIBLE);
            setAvatarAnimator();
            etUserName.setVisibility(View.VISIBLE);
            ivBackground.setImageResource(R.mipmap.register_background);
        } else {
            profileAvatar.setVisibility(View.GONE);
            profileAvatar.clearAnimation();
            etUserName.setVisibility(View.GONE);
            ivBackground.setImageResource(R.mipmap.login_background);
        }

    }

    /**
     * 登入或者注册成功跳转
     */
    public void trigger(){
        Intent intent = new Intent(AccountActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 收到从Activity传递过来的回调，然后取出其中的值进行图片加载
        // 如果是我能够处理的类型
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            // 通过UCrop得到对应的Uri
            final Uri resultUri = UCrop.getOutput(data);
            if (resultUri != null) {
                loadPortrait(resultUri);
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            //TODO 处理错误弹框
        }
    }

    /**
     * 加载Uri到当前的头像中
     *
     * @param uri Uri
     */
    private void loadPortrait(Uri uri) {
        // 得到头像地址
        mAvatarPath = uri.getPath();

        Glide.with(this)
                .load(uri)
                .asBitmap()
                .centerCrop()
                .into(profileAvatar);
    }

    /**
     * 图片循环的动画
     */
    private void setAvatarAnimator() {
        TranslateAnimation animator =new TranslateAnimation(4,4,-4,4);
        //设置持续时间
        animator.setDuration(300);
        //设置重复次数
        animator.setRepeatCount(ValueAnimator.INFINITE);
        //设置方向直行
        animator.setRepeatMode(Animation.REVERSE);

        profileAvatar.startAnimation(animator);
    }


}
