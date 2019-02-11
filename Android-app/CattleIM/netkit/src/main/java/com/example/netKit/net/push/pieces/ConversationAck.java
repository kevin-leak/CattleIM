package com.example.netKit.net.push.pieces;

/**
 * 用来处理对话收到的确认
 */
public class ConversationAck {


    /**
     * fromId :
     * toId :
     * chatId :
     * info : ok
     */
    private String fromId;
    private String toId;
    private String chatId;
    private String info = "ok";

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

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
