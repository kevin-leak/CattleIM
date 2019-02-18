package com.example.netKit.model;

import java.io.Serializable;
import java.util.Date;

public class ConversationModel {

    private String conId; // conversation 的id,这里只是本地的id
    private String content;
    private int categroy;
    private int type;
    private String chatId;  // 后端的消息，到来，一般情况下，本地是找不到的。
    private String fromId;
    private String toId;

    @Override
    public String toString() {
        return "ConversationModel{" +
                "content='" + content + '\'' +
                ", categroy=" + categroy +
                ", type=" + type +
                ", chatId='" + chatId + '\'' +
                ", fromId='" + fromId + '\'' +
                ", toId='" + toId + '\'' +
                ", createAt=" + createAt +
                ", isDone=" + isDone +
                '}';
    }

    private Date createAt;
    private boolean isDone;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCategroy() {
        return categroy;
    }

    public void setCategroy(int categroy) {
        this.categroy = categroy;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }
}
