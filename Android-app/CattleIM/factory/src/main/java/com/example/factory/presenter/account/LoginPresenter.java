package com.example.factory.presenter.account;

import com.example.common.factory.data.DataSource;
import com.example.factory.contract.account.LoginContract;
import com.example.factory.presenter.BasePresenter;
import com.example.netKit.db.User;

public class LoginPresenter extends BasePresenter<LoginContract.View>
        implements LoginContract.Presenter {

    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    @Override
    public void start() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void login(String phone, String password) {

    }
}
