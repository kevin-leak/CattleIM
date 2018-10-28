package com.example.factory.contract;

import android.support.annotation.StringRes;

/**
 * 抽象出所有present需要具备的方法， 抽象出所有的view需要具备方法
 * view： 展示错误， 设置present，展示和取消进度条
 *
 * @author KevinLeak
 */
public interface BaseContract {
    interface View<T extends Presenter> {

        /**
         * view 必须具有展示错误信息的能力
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
}
