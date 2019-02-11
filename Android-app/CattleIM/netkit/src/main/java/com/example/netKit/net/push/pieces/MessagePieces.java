package com.example.netKit.net.push.pieces;

import com.example.common.tools.DateTimeUtil;
import com.example.netKit.persistence.Account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 消息发送用的pieces
 */
public class MessagePieces {

    /**
     * chatId :
     * fromId :
     * toId :
     * type :
     * info : [{"category":"","content":""},{}]
     * createTime :
     */

    private String chatId;
    private String fromId;
    private String toId;
    private int type;
    private String createTime;
    private List<InfoBean> info;


    public MessagePieces(String toId, String message){
        this.chatId = UUID.randomUUID().toString();
        this.fromId = Account.getUserId();
        this.toId = toId;
        this.type = 0;
        this.createTime = DateTimeUtil.getSampleDate(new Date());
        this.info = new ArrayList<>();
        InfoBean infoBean = new InfoBean();
        infoBean.category = 0;
        infoBean.content = message;
        info.add(infoBean);


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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    class InfoBean {
        /**
         * category :
         * content :
         */

        private int category;
        private String content;

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    @Override
    public String toString() {
        return "MessagePieces{" +
                "chatId='" + chatId + '\'' +
                ", fromId='" + fromId + '\'' +
                ", toId='" + toId + '\'' +
                ", type='" + type + '\'' +
                ", createTime='" + createTime + '\'' +
                ", info=" + info +
                '}';
    }
}
