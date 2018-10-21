package com.example.thinkpad.cattleim.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.app.BaseActivity;
import com.example.common.app.BaseFragment;
import com.example.common.tools.UITools;
import com.example.common.widget.ViewPager.BaseFragmentPageAdapter;
import com.example.factory.net.NetInterface;
import com.example.factory.net.Network;
import com.example.thinkpad.cattleim.R;
import com.example.thinkpad.cattleim.frags.account.LoginFragment;
import com.example.thinkpad.cattleim.frags.account.RegisterFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 1. 实现UI切换
 * 2. 实现数据反馈以及切换时候的交互
 */
public class AccountActivity extends BaseActivity {


    @BindViews({R.id.btn_login, R.id.btn_register})
    List<TextView> navigationList;
    @BindView(R.id.vp_account)
    ViewPager pagerContainer;
    @BindView(R.id.iv_background)
    ImageView ivBackground;
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.fab_go)
    FloatingActionButton fabGo;


    private List<BaseFragment> fragmentList;
    private BaseFragmentPageAdapter pageAdapter;

    /**
     * 用来记录当前的fragment是哪个
     * todo 有时间就对 fragmentPage 做一次封装，将他封装进去
     */
    private int CURRENT_CONTENT = 0;
    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPageList();
        setPage();

        fabGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CURRENT_CONTENT == 0){
                    if (loginFragment != null){
                        loginFragment.login();
                    }
                }

                Intent intent = new Intent( AccountActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    protected void initWidget() {
        super.initWidget();
        ivBackground.getLayoutParams().height = (int) (UITools.getScreenHeight(this) * 0.3);
    }

    /**
     * 将page 设定到viewPage里面并实现滑动与导航栏的切换，点击与page的切换
     */
    private void setPage() {
        pageAdapter =
                new BaseFragmentPageAdapter(getSupportFragmentManager(), getPageList());

        pagerContainer.setAdapter(pageAdapter);

        for (final TextView textView : navigationList) {
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pagerContainer.setCurrentItem(navigationList.indexOf(textView));
                }
            });
        }


        pagerContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int currentItem, float proportion, int i1) {
                //todo 后期优化实现平滑滚动
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onPageSelected(int i) {
                CURRENT_CONTENT = i;
                navigationList.get(i).setBackgroundResource(R.drawable.top_border);
                if (fragmentList.get(i) instanceof RegisterFragment) {
                    profileImage.setVisibility(View.VISIBLE);
                    ivBackground.setImageResource(R.mipmap.register_background);
                } else {
                    profileImage.setVisibility(View.GONE);
                    ivBackground.setImageResource(R.mipmap.login_background);
                }

                for (TextView textView : navigationList) {
                    if (navigationList.get(i) != textView) {
                        textView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    /**
     * @return 获取到滑动的page list
     */
    private List<BaseFragment> getPageList() {
        if (fragmentList == null) {
            loginFragment = new LoginFragment();
            registerFragment = new RegisterFragment();
            fragmentList = new ArrayList<>();
            fragmentList.add(loginFragment);
            fragmentList.add(registerFragment);
        }

        return fragmentList;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_account;
    }

}
