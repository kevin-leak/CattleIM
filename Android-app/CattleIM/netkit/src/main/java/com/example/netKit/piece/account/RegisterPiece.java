package com.example.netKit.piece.account;

public class RegisterPiece {
    /**
     * phone : 18870742138
     * name : kevin
     * password : 199shadjfk
     * avatar : sdajfklajflksdaj;
     */

    private String phone;
    private String password;

    public RegisterPiece(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
