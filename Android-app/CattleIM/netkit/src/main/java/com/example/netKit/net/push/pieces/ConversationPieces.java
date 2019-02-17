package com.example.netKit.net.push.pieces;

import android.text.TextUtils;

import com.example.netKit.db.Conversation;
import com.example.netKit.db.Conversation_Table;
import com.example.netKit.db.Event;
import com.example.netKit.db.Event_Table;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;

/**
 * 消息发送用的pieces
 */
public class ConversationPieces {


    private transient String conId; // 不会被转化为json

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
    private InfoBean info;



    public ConversationPieces() {
    }

    public String getChatId() {

        if (chatId == null){
            chatId = findChatIdFromLocal();
        }

        return chatId;
    }


    private String findChatIdFromLocal() {
        if (!TextUtils.isEmpty(fromId) && !TextUtils.isEmpty(toId)) {
            Conversation conversation = SQLite.select()
                    .from(Conversation.class)
                    .where(OperatorGroup.clause()
                            .and(Conversation_Table.receive.eq(toId))
                            .and(Conversation_Table.send.eq(fromId)))
                    .querySingle();
            if (conversation != null) {
                Event event = SQLite.select()
                        .from(Event.class)
                        .where(Event_Table.chatId.eq(conversation.getId()))
                        .querySingle();
                if (event !=null) {
                    return event.getChatId();
                }
            }
        }

        return null;
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


    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }


    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
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
        return "ConversationPieces{" +
                "chatId='" + chatId + '\'' +
                ", fromId='" + fromId + '\'' +
                ", toId='" + toId + '\'' +
                ", type='" + type + '\'' +
                ", createTime='" + createTime + '\'' +
                ", info=" + info +
                '}';
    }
}
