package com.example.factory.contract;

import android.app.Activity;
import android.support.annotation.StringRes;

import com.example.common.widget.recycler.RecyclerAdapter;

/**
 * 抽象出所有present需要具备的方法， 抽象出所有的view需要具备方法
 * View： 展示错误， 设置present，展示和取消进度条
 *
 * @author KevinLeak
 */
public interface BaseContract {
    interface View<T extends Presenter> {

        /**
         * View 必须具有展示错误信息的能力
         * @param error 传入错误信息
         */
        void showError(@StringRes int error);

        /**
         * @param present 当前的present
         */
        void setPresenter(T present);

        /**
         * 展示进度栏
         */
        void showDialog();

        /**
         * 由于子类在都是fragment的情况下这个会被自动的复写
         * 我们不需要管她，在这里我们只是先声明一下我们子类具有这个方法
         * 方便我们在主线程和子线程之间的切换
         */
        Activity getActivity();
    }

    /**
     * 这里实现一个最为简单的presenter, 构造方法也没有实现，等待着各种子类自行实现
     */
    interface Presenter {
        // 共用的开始触发
        void start();

        // 共用的销毁触发
        void destroy();
    }

    // 基本的一个列表的View的职责
    interface RecyclerView<T extends Presenter, ViewMode> extends View<T> {
        // 界面端只能刷新整个数据集合，不能精确到每一条数据更新
        // void onDone(List<User> users);

        // 拿到一个适配器，然后自己自主的进行刷新
        RecyclerAdapter<ViewMode> getRecyclerAdapter();

        // 当适配器数据更改了的时候触发
        void onAdapterDataChanged();
    }
}
