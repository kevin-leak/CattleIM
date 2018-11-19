package com.example.factory.presenter.user;

import com.example.factory.contract.BaseContract;

public interface PersonContract {

    interface Presenter extends BaseContract.Presenter {

    }

    interface View extends BaseContract.View<Presenter>{

        String getUserId();

    }

}
