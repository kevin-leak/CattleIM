package com.example.factory.contract.account;

import com.example.factory.contract.BaseContract;
import com.example.netKit.db.User;

public interface ProfileContract {

    interface View extends BaseContract.View<Presenter>{


        void loadInfo(User user);



    }


    interface Presenter extends BaseContract.Presenter{

        void getAccountInfo(String id);

        void changeInfo(String id);

        void loginOut(String id);
    }
}
