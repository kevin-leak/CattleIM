package com.example.factory.contract.chat;

import com.example.netKit.db.Conversation;
import com.example.netKit.db.Event;
import com.example.netKit.model.ConversationModel;
import com.example.netKit.model.EventModel;
import com.example.netKit.model.UserModel;

public interface ConversationCenter {

    // 分发处理一堆用户卡片的信息，并更新到数据库
    void dispatch(ConversationModel... model);

}
