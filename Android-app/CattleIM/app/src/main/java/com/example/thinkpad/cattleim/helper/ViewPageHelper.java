package com.example.thinkpad.cattleim.helper;

import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.common.app.BaseActivity;
import com.example.common.widget.ViewPager.ViewPageAdapter;
import com.example.thinkpad.cattleim.R;
import com.example.thinkpad.cattleim.frags.account.RegisterFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @param <V> 导航栏的view
 * @param <F> 切换的fragment
 * @author KevinLeak
 */
public class ViewPageHelper<V extends View, F extends Fragment> {


    private ViewPageAdapter pageAdapter;
    private ViewPager viewPager;
    private ViewPagerCallback callback;
    private AppCompatActivity context;
    private List<V> navList = new ArrayList<>();
    private List<F> fragments = new ArrayList<>();

    private Map<V, F> items = new HashMap<>();

    /**
     * 切换时候显示的边框，默认上边框
     */
    private int topBorder = R.drawable.top_border;
    /**
     * 处于negative状态的背景颜色
     */
    private int negativeColor = R.color.colorWhite;
    /**
     * 当前的一个V,暴露给外界
     */
    private F current;


    /**
     * @param callback 必须与view 相关联
     */
    public ViewPageHelper(ViewPager viewPager, AppCompatActivity context, ViewPagerCallback callback) {
        this.viewPager = viewPager;
        this.callback = callback;
        this.context = context;
        pageAdapter = new ViewPageAdapter(context.getSupportFragmentManager());
        viewPager.setAdapter(pageAdapter);
        switchPage();
    }


    /**
     * @param v 导航的一个view，一个整体的nav item, 不支持这里设置title
     * @param f 切换的一个view
     * @return 返回当前对象，方便再次添加
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public  ViewPageHelper<V, F> addItem(V v, F f){
        if (Objects.nonNull(v) || Objects.nonNull(f)){
            navList.add(v);
            fragments.add(f);
            pageAdapter.addFragment(f);
            clickToSwitch();
            if (navList.size() == 1){
                setFirstActive(0);
            }
        }
        return this;
    }


    public void setFirstActive(int position){
        if (position < 0 || position > navList.size() || position > fragments.size()){
            return;
        }
        if (navList.size() != 0 && fragments.size() != 0) {
            viewPager.setCurrentItem(position);
            current = fragments.get(position);
        }
    }

    /**
     * 设置监控，点击导航栏发生fragment的切换
     */
    private void clickToSwitch(){
        for (final View view : navList) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(navList.indexOf(view));
                }
            });
        }
    }

    /**
     * @param bottomBorder 一个drawable 文件，设置边框， active 背景也在此刻设置好
     */
    public void setNavBackground(@DrawableRes int bottomBorder){
        this.topBorder = bottomBorder;
    }

    /**
     * @param negativeColor 一个color资源，设置处于negative的时候nav item 的颜色
     */
    public void setNavNegativeColor(@ColorInt int negativeColor){

        this.negativeColor = negativeColor;
    }

    /**
     * @param pageAdapter 提供一个方法供外部调用实现自己的adapter
     */
    public void setPageAdapter(ViewPageAdapter pageAdapter) {
        this.pageAdapter = pageAdapter;
    }



    /**
     * 将page 设定到viewPage里面并实现滑动与导航栏的切换，点击与page的切换
     */
    private void switchPage() {


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int currentItem, float proportion, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                current = fragments.get(i);

                navList.get(i).setBackgroundResource(topBorder);
                for (V view : navList) {
                    if (navList.get(i) != view) {
                        view.setBackgroundColor(context.getResources().getColor(negativeColor));
                    }
                }

                callback.onChangedFragment(current);
            }

            @Override
            public void onPageScrollStateChanged(int i) {


            }
        });
    }

    /**
     * @return 返回给外界，调用
     */
    public F getCurrent() {
        return current;
    }



    public interface ViewPagerCallback<T>{

        void onChangedFragment(T currentFragment);

    }

}
