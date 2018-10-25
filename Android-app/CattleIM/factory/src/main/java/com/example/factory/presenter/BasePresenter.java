package com.example.factory.presenter;

import com.example.factory.contract.BaseContract;

/**
 * @author KevinLeak
 * 统一实现present的功能
 *
 */
public class BasePresenter<T extends BaseContract.View> implements BaseContract.Presenter {


    private T mView;

    public BasePresenter(T view) {
        setView(view);
    }

    /**
     * 此处是为扩展，我们可能设置view的方法不一样，所有不放在初始化里面执行
     * @param view 传入所需view的实例对象
     */
    public void setView(T view){
        this.mView = view;
        this.mView.setPresenter(this);
    }

    public T getView(){
        return this.mView;
    }

    @Override
    public void start() {

    }

    @Override
    public void destroy() {

    }
}
