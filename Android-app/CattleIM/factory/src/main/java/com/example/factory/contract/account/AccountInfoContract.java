package com.example.factory.contract.account;

import com.example.factory.contract.BaseContract;

public interface AccountInfoContract {

    interface Presenter extends BaseContract.Presenter {
        void addAccountInfo(String path, String username, String desc, int sex);
    }

    interface View extends BaseContract.View<Presenter> {

          void addSuccess();
    }
}
