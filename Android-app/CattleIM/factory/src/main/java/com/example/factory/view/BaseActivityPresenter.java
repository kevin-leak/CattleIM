package com.example.factory.view;

import android.app.Activity;

import com.example.common.app.Application;
import com.example.common.app.BaseActivity;
import com.example.factory.contract.BaseContract;

public abstract class BaseActivityPresenter<Presenter extends BaseContract.Presenter>
        extends BaseActivity implements BaseContract.View<Presenter> {


    protected Presenter presenter;


    /**
     * View 必须具有展示错误信息的能力
     *
     * @param error 传入错误信息
     */
    @Override
    public void showError(int error) {
        // TODO 可以在此处写一个统一的放回错误到界面的逻辑
        Application.showToast(getActivity(), error);
    }

    @Override
    protected void initBefore() {
        super.initBefore();

        presenter = initPresent();
    }

    /**
     * 初始化presenter，要求子类必须实现
     */
    protected abstract Presenter initPresent();

    @Override
    public void setPresenter(Presenter presenter) {

        this.presenter = presenter;
    }


    /**
     * 展示进度栏
     */
    @Override
    public void showDialog() {

    }

    /**
     * 由于子类在都是fragment的情况下这个会被自动的复写
     * 我们不需要管她，在这里我们只是先声明一下我们子类具有这个方法
     * 方便我们在主线程和子线程之间的切换
     */
    @Override
    public Activity getActivity() {
        return this;
    }
}
