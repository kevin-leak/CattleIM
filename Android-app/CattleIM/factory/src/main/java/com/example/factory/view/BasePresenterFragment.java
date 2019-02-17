package com.example.factory.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ProgressBar;

import com.example.common.app.Application;
import com.example.common.app.BaseFragment;
import com.example.factory.contract.BaseContract;
import com.example.factory.presenter.BasePresenter;


/**
 * 此类是为了建议统一的fragment View 中的一个标准，在其中可以通过他对想要继承的子类进行约束
 * 如：protected abstract void initPresent();
 * 同时我们将此类设置为抽象类，这同时使得基础的 BaseFragment 的约束不会改变
 *
 * 记录一下：突然明白，理解了泛型是对属性的类型的一种扩展，抽象类型是对子类的一种约束，接口是对属性的一种扩展
 *
 * 这里还统一实现了以fragment这一层次的抽象出来的功能，同时供子类复写
 * @param <Presenter> 传入一个自己需求的presenter
 */
public abstract class BasePresenterFragment<Presenter extends BaseContract.Presenter>
        extends BaseFragment implements BaseContract.View<Presenter> {

    /**
     * 为了可以被子类调用 protected
     */
    protected Presenter presenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //在activity 和 fragment建立连接之前 初始化presenter
        initPresent();
    }

    /**
     * 初始化presenter，要求子类必须实现
     */
    protected abstract Presenter initPresent();

    @Override
    public void showError(int error) {
        // TODO 可以在此处写一个统一的放回错误到界面的逻辑
        Application.showToast(getActivity(), error);
    }

    @Override
    public void setPresenter(Presenter presenter) {

        this.presenter = presenter;
    }

    @Override
    public void showDialog() {

//        ProgressDialog dialog = new ProgressDialog(this.getActivity());
//
//        dialog.show();

    }


}
