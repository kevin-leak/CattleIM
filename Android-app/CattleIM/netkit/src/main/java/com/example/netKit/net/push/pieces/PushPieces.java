package com.example.netKit.net.push.pieces;

import com.example.netKit.NetKit;
import com.example.netKit.net.push.helper.ClientHelper;
import com.example.netKit.persistence.Account;


/**
 * 用来处理连接和心跳包
 */
public class PushPieces<T> {

    /**
     * 如果是连接包，那么用这个状态
     */
    public final static int  CONNECT = 0;

    public final static int  HEART_BEAT = 1;


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

    /**
     * @param message 消息
     * 用来处理信息的发送
     */
    public PushPieces(T message, int status) {
        this.message = message;
        this.status = status;
    }


    /**
     * @param message 消息
     * 用来处理信息的发送
     */
    public PushPieces(T message) {
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

    public String toJson(){
        return ClientHelper.getGson().toJson(this);
    }

}
