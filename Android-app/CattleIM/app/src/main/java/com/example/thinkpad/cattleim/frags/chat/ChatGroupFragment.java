package com.example.thinkpad.cattleim.frags.chat;

import android.view.View;

import com.example.factory.contract.chat.ChatContract;
import com.example.netKit.db.Group;
import com.example.netKit.db.GroupMember;
import com.example.thinkpad.cattleim.R;

import java.util.List;

public class ChatGroupFragment extends ChatFragment<Group> implements ChatContract.GroupView {


    @Override
    protected int getHeaderLayoutId() {
        return R.layout.hello;
    }

    @Override
    protected void initWidgets(View root) {
        super.initWidgets(root);


    }


    @Override
    public void onInit(Group group) {

    }

    @Override
    public void showAdminOption(boolean isAdmin) {

    }

    @Override
    public void onInitGroupMembers(List<GroupMember> members, long moreCount) {

    }

    /**
     * 初始化presenter，要求子类必须实现
     */
    @Override
    protected ChatContract.Presenter initPresent() {
        return null;
    }
}
