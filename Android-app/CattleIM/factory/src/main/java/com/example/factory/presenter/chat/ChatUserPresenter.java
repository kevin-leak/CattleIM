package com.example.factory.presenter.chat;

import com.example.factory.contract.chat.ChatContract;
import com.example.factory.middleware.chat.ConversationRepository;
import com.example.factory.presenter.user.UserHelper;
import com.example.netKit.db.Conversation;
import com.example.netKit.db.User;

public class ChatUserPresenter extends ChatPresenter<ChatContract.UserView>
        implements ChatContract.Presenter{

    private String receiverId;

    public ChatUserPresenter(ChatContract.UserView view, String receiverId) {
        super(new ConversationRepository(receiverId), view, receiverId, Conversation.TYPE_SIMPLE);
        this.receiverId = receiverId;
    }

    @Override
    public void start() {
        super.start();

        // 从本地拿这个人的信息
        User receiver = UserHelper.findFromLocal(receiverId);
        getView().onInit(receiver);
    }
}
