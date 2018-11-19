package com.example.factory.presenter.friends;

import com.example.factory.contract.BaseContract;

public interface FriendContract {

    interface View extends BaseContract.View<Present> {

        // 发送提添加好友的成功
        void sendSuccess();

    }


    interface Present extends BaseContract.Presenter {

        void addFriend(String id);

    }
}
