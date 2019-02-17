package com.example.thinkpad.cattleim.frags.chat;

import com.example.factory.contract.chat.ChatContract;
import com.example.netKit.db.Tag;
import com.example.netKit.db.TagMember;
import com.example.thinkpad.cattleim.R;

import java.util.List;

public class ChatTagFragment extends ChatFragment<Tag> implements ChatContract.TagView {

    @Override
    protected int getHeaderLayoutId() {
        return R.layout.hello;
    }

    @Override
    public void onInit(Tag tag) {

    }

    @Override
    public void onInitGroupMembers(List<TagMember> members, long moreCount) {

    }

    /**
     * 初始化presenter，要求子类必须实现
     */
    @Override
    protected ChatContract.Presenter initPresent() {
        return null;
    }
}
