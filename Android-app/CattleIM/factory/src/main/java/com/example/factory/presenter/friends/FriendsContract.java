package com.example.factory.presenter.friends;

import com.example.factory.contract.BaseContract;
import com.example.netKit.db.User;

/**
 * 建立好友与团的查询
 */
public interface FriendsContract extends BaseContract {

    // 什么都不需要额外定义，开始就是调用start即可
    interface Presenter extends BaseContract.Presenter {

    }


    // 都在基类完成了
    interface View extends BaseContract.RecyclerView<Presenter, User> {

    }

}
