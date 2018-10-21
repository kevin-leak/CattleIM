package com.example.common.widget.ViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Button;

import com.example.common.app.BaseFragment;

import java.util.List;


/**
 * @author Kevin
 * 实现导航栏与page的联动
 * 处理fragment的缓冲问题，实现滑动和点击的切换
 * */

public class BaseFragmentPageAdapter extends FragmentPagerAdapter {

    private final List<BaseFragment> fragmentList;

    /**
     * @param fm ragmentManager
     * @param fragmentList 要滑动的fragment
     */
    public BaseFragmentPageAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }


    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }



    @Override
    public int getCount() {
        return fragmentList.size();
    }


}
