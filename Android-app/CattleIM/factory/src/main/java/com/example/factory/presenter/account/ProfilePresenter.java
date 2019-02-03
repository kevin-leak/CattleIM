package com.example.factory.presenter.account;


import com.example.common.factory.data.DataSource;
import com.example.factory.contract.account.ProfileContract;
import com.example.factory.presenter.BasePresenter;
import com.example.netKit.persistence.Account;

public class ProfilePresenter extends BasePresenter<ProfileContract.View>
        implements ProfileContract.Presenter , DataSource.Callback<String>{
    public ProfilePresenter(ProfileContract.View view) {
        super(view);
    }


    @Override
    public void onDataNotAvailable(int strRes) {

    }

    @Override
    public void getAccountInfo(String id) {

    }

    @Override
    public void changeInfo(String id) {

    }

    @Override
    public void loginOut(String id) {

        AccountHelper.loginOut(this);

    }

    @Override
    public void onDataLoaded(String s) {
        if ("ok".equals(s)){
            if (Account.removeLogin(getView().getActivity()))
                getView().getActivity().finish();
        }
    }
}


