package com.example.factory.contract.todo;

import com.example.factory.contract.BaseContract;
import com.example.factory.presenter.BasePresenter;
import com.example.netKit.db.Event;

public interface EventContract {
    interface Presenter extends BaseContract.Presenter{

        // 用来处理消息已经读取与后端的通知
        void sendReadEvent(Event event);
    }

    interface View extends BaseContract.RecyclerView<Presenter, Event>{


        void read(Event event);
    }
}
