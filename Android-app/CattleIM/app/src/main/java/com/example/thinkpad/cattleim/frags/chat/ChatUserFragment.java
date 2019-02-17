package com.example.thinkpad.cattleim.frags.chat;


import android.os.Bundle;

import com.example.factory.contract.chat.ChatContract;
import com.example.factory.presenter.chat.ChatUserPresenter;
import com.example.netKit.db.User;
import com.example.thinkpad.cattleim.R;
import com.example.thinkpad.cattleim.activities.ConversationActivity;

public class ChatUserFragment extends ChatFragment<User> implements ChatContract.UserView {


    private String receiveId;

    @Override
    protected int getHeaderLayoutId() {
        return R.layout.fragment_chat_user;
    }

    @Override
    protected void intiArgs(Bundle arguments) {
        super.intiArgs(arguments);


        receiveId = arguments.getString(ConversationActivity.KEY_RECEIVER_ID);

    }

    @Override
    protected void initToolbar() {
        super.initToolbar();

    }

    @Override
    public void onInit(User user) {
        mToolbar.setTitle(user.getUsername());
    }

    /**
     * 初始化presenter，要求子类必须实现
     */
    @Override
    protected ChatContract.Presenter initPresent() {
        return new ChatUserPresenter(this, receiveId);
    }
}
