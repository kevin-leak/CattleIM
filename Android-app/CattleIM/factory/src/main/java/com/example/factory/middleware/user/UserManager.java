package com.example.factory.middleware.user;

import com.example.netKit.model.AccountModel;

/**
 * 推送的用户信息管理器
 */
public interface UserManager {

    // 分发处理一堆用户卡片的信息，并更新到数据库
    void dispatch(AccountModel... model);
}
