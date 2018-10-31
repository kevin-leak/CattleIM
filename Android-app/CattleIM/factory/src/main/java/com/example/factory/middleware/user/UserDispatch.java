package com.example.factory.middleware.user;


import com.example.netKit.model.UserModel;

/**
 * 做一个model的分发，开启一个线程对其进行一个数据的额存储处理
 */
public class UserDispatch implements UserManager {
    @Override
    public void dispatch(UserModel... model) {

    }
}
