package com.example.netKit.piece.account;

public class AccountInfoPiece {

    private String avatar;
    private String userId;
    private String username;
    private String desc;
    private int sex = 0; // 默认为男

    public AccountInfoPiece(String avatar, String userId, String userName, String desc, int sex) {
        this.avatar = avatar;
        this.userId = userId;
        this.username = userName;
        this.desc = desc;
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }
}
