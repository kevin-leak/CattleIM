package com.example.netKit.piece.account;


/**
 * 1.集合所有用户的信息
 */
public class AccountPiece {


    /**
     * status : 0
     * name : lkk
     * phone : 18870742138
     * avatar : asfklsajflkdsja
     */

    private int status;
    private String name;
    /**
     * 当数据返回正确，我们由当地缓存
     */
    private String phone;
    private String avatar;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
