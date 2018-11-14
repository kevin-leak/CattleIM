package com.example.netKit.model;


import com.example.netKit.db.User;

/**
 * 1.集合所有用户的信息
 */
public class UserModel {

    // 用户基本信息
    private User user;
    // 当前登录的账号
    private String account;
    // 标示是否已经绑定到了设备PushId
    private boolean isBind;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public boolean isBind() {
        return isBind;
    }

    public void setBind(boolean bind) {
        isBind = bind;
    }

    @Override
    public String toString() {
        return "AccountRspModel{" +
                "user=" + user +
                ", account='" + account + '\'' +
                ", isBind=" + isBind +
                '}';
    }
}
