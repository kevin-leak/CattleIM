package com.example.factory.contract.user;

import com.example.netKit.model.AccountModel;
import com.example.netKit.model.UserModel;

/**
 * 建立一个分发中心
 */
public interface UserCenter {

    // 分发处理一堆用户卡片的信息，并更新到数据库
    void dispatch(UserModel... model);
}
