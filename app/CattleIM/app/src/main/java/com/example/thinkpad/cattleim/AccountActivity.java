package com.example.thinkpad.cattleim;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.example.common.app.BaseActivity;
import com.example.common.app.BaseFragment;
import com.example.common.widget.ViewPager.BaseFragmentPageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;

public class AccountActivity extends BaseActivity {


    @BindViews({R.id.btn_login, R.id.btn_register})
    List<Button> navigationList;
    @BindView(R.id.vp_account)
    ViewPager pagerContainer;


    private List<BaseFragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPageList();
        setPage();


    }

    private void setPage() {
        final BaseFragmentPageAdapter pageAdapter =
                new BaseFragmentPageAdapter(getSupportFragmentManager(), getPageList());

        pagerContainer.setAdapter(pageAdapter);

        for (final Button button : navigationList ){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pagerContainer.setCurrentItem(navigationList.indexOf(button));
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
                for ( Button button : navigationList ){
                    if (navigationList.get(i) != button){
                        button.setBackgroundColor(getResources().getColor(R.color.colorWhite));
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
