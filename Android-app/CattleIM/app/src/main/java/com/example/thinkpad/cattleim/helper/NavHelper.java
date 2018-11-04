package com.example.thinkpad.cattleim.helper;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.SparseArray;

/**
 * @author kevin
 * 0.FragmentManager 切换fragment 需要的参数: fragment存放位置ID, fragment实例，tag(fragemnt名字）, context初始化fragment
 * 1.实现BottomNavigationView 中fragment与bottom menu之间的绑定，实现复用，处理重复点击
 * 2.实现回调监听，提供接口给调用者实现切换之后的效果，则我们需要调用者传入一个监听的对象，---> 建立一个内部监听类
 * 3.把一个fragment 封装成一个tab，进行属性的储存
 * 4.由于不知道是由什么组成的tab(由使用Helper的人决定，实现解耦)，则将tab定义为泛型，则NavBottomHelper也需要进行数据的泛型。
 * 5.Tab 对象至少具有属性：对单例fragment对象储存，tag的储存
 */

public class NavHelper<T> {


    private int containerId;
    private FragmentManager mFragmentManager;
    private OnNavChangeListener listener;
    // 所有的Tab集合，SparseArray 内存优化
    private final SparseArray<Tab<T>> tabs = new SparseArray<>();
    /**
     * 建立此刻的tab，进行与oldTab的缓存
     */
    private Tab<T> currentTab;
    private Context context;


    /**
     * @param containerId 存放fragment的资源文件的id
     * @param mFragmentManager 获取当前activity 的一个fragment manager
     */
    public NavHelper(Context context, int containerId, FragmentManager mFragmentManager, OnNavChangeListener listener) {
        this.containerId = containerId;
        this.mFragmentManager = mFragmentManager;
        this.listener = listener;
        this.context = context;
    }

    /**
     * 添加Tab
     *
     * @param menuId Tab对应的菜单Id
     * @param tab    Tab
     */
    public NavHelper<T> add(int menuId, Tab<T> tab) {
        tabs.put(menuId, tab);
        return this;
    }


    /**
     * @param menuId 当前点击的tab的位置
     * @return 返回是否处理
     */
    public boolean performClickMenu(int menuId) {
        // 集合中寻找点击的菜单对应的Tab，
        // 如果有则进行处理
        Tab<T> tab = tabs.get(menuId);
        if (tab != null) {
            doSelect(tab);            return true;
        }

        return false;
    }

    /**
     * @param tab 当前点击的要切换的tab
     */
    private void doSelect(Tab<T> tab) {
        Tab<T> oldTab = null;

        if (currentTab != null) {
            oldTab = currentTab;
            if (oldTab == tab) {
                // 如果说当前的Tab就是点击的Tab，
                // 那么我们不做处理
                notifyTabReselect(tab);
                return;
            }
        }
        // 赋值并调用切换方法
        currentTab = tab;
        doTabChanged(currentTab, oldTab);
    }

    /**
     * 执行切换
     * 同时我们设置拦截
     */
    private void doTabChanged(Tab<T> newTab, Tab<T> oldTab) {

        FragmentTransaction ft = mFragmentManager.beginTransaction();

        if (oldTab != null) {
            if (oldTab.fragment != null) {
                // 从界面移除，但是还在Fragment的缓存空间中
                ft.detach(oldTab.fragment);
            }
        }

        if (newTab != null) {
            if (newTab.fragment == null) {
                // 首次新建
                Fragment fragment = Fragment.instantiate(context, newTab.clx.getName(), null);
                // 缓存起来
                newTab.fragment = fragment;
                // 提交到FragmentManger
                ft.add(containerId, fragment, newTab.clx.getName());
            } else {
                // 从FragmentManger的缓存空间中重新加载到界面中
                ft.attach(newTab.fragment);
            }
        }
        // 提交事务
        ft.commit();
        // 通知回调
        notifyTabSelect(newTab, oldTab);

    }

    private void notifyTabSelect(Tab<T> newTab, Tab<T> oldTab) {
        listener.OnNavChanged(newTab, oldTab);
    }

    private void notifyTabReselect(Tab<T> tab) {
        // TODO 二次点击Tab所做的操作
    }

    //需要的属性： Tab封装，
    /**
     * 我们的所有的Tab基础属性
     * 在这里的底部栏中clx表现为，Fragment.class, extra为他的tag
     * @param <T> 范型的额外参数
     */
    public static class Tab<T> {
        public Tab(Class<?> clx, T extra) {
            this.clx = clx;
            this.extra = extra;
        }

        // Fragment对应的Class信息
        public Class<?> clx;
        // 额外的字段，用户自己设定需要使用
        public T extra;

        // 内部缓存的对应的Fragment，
        // Package权限，外部无法使用
        Fragment fragment;
    }




    public interface OnNavChangeListener<T>{
        void OnNavChanged(Tab<T> newTab, Tab<T> oldTab);
    }

}
