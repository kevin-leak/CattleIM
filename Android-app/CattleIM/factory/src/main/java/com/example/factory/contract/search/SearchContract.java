package com.example.factory.contract.search;

import com.example.factory.contract.BaseContract;
import com.example.netKit.model.AccountModel;
import com.example.netKit.model.UserModel;

import java.util.List;

public interface SearchContract {


    interface Presenter extends BaseContract.Presenter {

        // 搜索内容
        void search(String content, int page);
    }


    interface UserView extends BaseContract.View<Presenter> {
        void onSearchDone(List<UserModel> userCards);
    }

    interface GroupView extends BaseContract.View<Presenter> {

    }

    interface TagView extends BaseContract.View<Presenter> {

    }

    interface TimeView extends BaseContract.View<Presenter> {

    }

    interface TopicView extends BaseContract.View<Presenter> {

    }

    interface TaskView extends BaseContract.View<Presenter> {

    }

    interface NoticeView extends BaseContract.View<Presenter> {

    }


}
