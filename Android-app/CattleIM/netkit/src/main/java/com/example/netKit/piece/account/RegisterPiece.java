package com.example.netKit.piece.account;

public class RegisterPiece {
    /**
     * phone : 18870742138
     * name : kevin
     * password : 199shadjfk
     * avatar : sdajfklajflksdaj;
     */

    private String phone;
    private String username;
    private String password;
    private String avatar;

    public RegisterPiece(String phone, String username, String password, String avatar) {
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


}
