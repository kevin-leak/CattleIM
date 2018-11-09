package com.example.netKit.net.push;

import com.example.netKit.persistence.Account;

public class PushPieces<T> {


    /**
     * status : 1
     * pushId : uuid
     * message :
     */

    private int status = 1;
    private String pushId = Account.getPushId();
    private T message;

    public PushPieces(){

    }

    public PushPieces(int status, String pushId, T message) {
        this.status = status;
        this.pushId = pushId;
        this.message = message;
    }

    public int getStatus() {

        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "PushPieces{" +
                "status=" + status +
                ", pushId='" + pushId + '\'' +
                ", message='" + message.toString() + '\'' +
                '}';
    }
}
