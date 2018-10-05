package com.example.thinkpad.cattleim.activities.account;

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
import com.example.thinkpad.cattleim.R;
import com.example.thinkpad.cattleim.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import de.hdodenhof.circleimageview.CircleImageView;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPageList();
        setPage();

        fabGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    private void setPage() {
        final BaseFragmentPageAdapter pageAdapter =
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
            LoginFragment loginFragment = new LoginFragment();
            RegisterFragment registerFragment = new RegisterFragment();
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
