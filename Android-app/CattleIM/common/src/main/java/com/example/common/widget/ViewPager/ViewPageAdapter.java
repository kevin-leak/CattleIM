package com.example.common.widget.ViewPager;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @author Kevin
 * 实现导航栏与page的联动
 * 处理fragment的缓冲问题，实现滑动和点击的切换
 * */

public class ViewPageAdapter<F extends Fragment> extends FragmentPagerAdapter {

    public FragmentManager manager;
    private List<F> fragmentList = new ArrayList<>();

    /**
     * @param fm ragmentManager
     * @param fragmentList 要滑动的fragment
     */
    public ViewPageAdapter(FragmentManager fm, List<F> fragmentList) {
        super(fm);
        manager = fm;
        this.fragmentList = fragmentList;
    }

    public ViewPageAdapter(FragmentManager fm) {
        super(fm);
    }



    public void addFragment(F fragment){
        fragmentList.add(fragment);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public int getCount() {
        if (fragmentList == null){
            return 0;
        }
        return fragmentList.size();
    }


}
