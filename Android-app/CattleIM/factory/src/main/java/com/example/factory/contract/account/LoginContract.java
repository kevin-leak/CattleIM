package com.example.factory.contract.account;

import com.example.factory.contract.BaseContract;

public interface LoginContract {
    interface View extends BaseContract.View<Presenter>{
        void loginSuccess();
    }

    interface Presenter extends BaseContract.Presenter {
        void login(String phone, String password);

        /**
         * @param phone
         * @return
         */
        // 检查手机号是否正确, 实现给presenter用
        boolean checkMobile(String phone);

        boolean checkPsd(String psd);
    }
}
