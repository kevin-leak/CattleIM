package com.example.factory.model;


/**
 * 1.集合所有用户的信息
 */
public class AccountModel {

    /**
     * status : 0
     * name : lkk
     * avatar : asfklsajflkdsja
     * token : asjfklsjdkfjaslksa
     */

    private int status;
    private String name;
    private String avatar;
    private String token;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
