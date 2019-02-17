package com.example.factory.contract.todo;

import com.example.netKit.db.Event;
import com.example.netKit.model.ConversationModel;
import com.example.netKit.model.EventModel;

public interface EventCenter {

    // 分发处理一堆用户卡片的信息，并更新到数据库
    void dispatch(Event... model);

}
