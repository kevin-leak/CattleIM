package com.example.factory.contract.account;

import com.example.factory.contract.BaseContract;

public interface RegisterContract{

    interface View extends BaseContract.View<Presenter>{
        // 注册成功
        void registerSuccess();

    }
    interface Presenter extends BaseContract.Presenter {
        // 发起一个注册
        void register(String phone, String name, String password, String avatar);

        // 检查手机号是否正确, 实现给presenter用
        boolean checkMobile(String phone);
    }
}
