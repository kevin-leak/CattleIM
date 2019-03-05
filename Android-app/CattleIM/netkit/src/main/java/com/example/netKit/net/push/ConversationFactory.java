package com.example.netKit.net.push;

import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.example.netKit.db.Conversation;
import com.example.netKit.db.Conversation_Table;
import com.example.netKit.db.Event;
import com.example.netKit.db.Event_Table;
import com.example.netKit.model.ConversationModel;
import com.example.netKit.model.EventModel;
import com.example.netKit.net.push.helper.ClientHelper;
import com.example.netKit.net.push.pieces.ConversationAck;
import com.example.netKit.net.push.pieces.ConversationPieces;
import com.example.netKit.net.push.pieces.PushPieces;
import com.example.netKit.persistence.Account;
import com.google.gson.reflect.TypeToken;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ConversationFactory {


    /**
     * 用来建造心跳和回送包
     */
    public static String build(PushPieces pieces) {
        return pieces.toJson();
    }


    /**
     * 用来建造，多个会话
     */
    public static String build(List<ConversationPieces> piecesList) {
        PushPieces<List<ConversationPieces>> pushPieces = new PushPieces<>(piecesList);
        return pushPieces.toJson();
    }


    /**
     *
     * 会话确认包
     */
    public static String build(ConversationAck ack) {
        PushPieces<ConversationAck> pushPieces = new PushPieces<>(ack);
        return pushPieces.toJson();
    }

    /**
     * 将 ConversationModel 转化为数据库模型，方便存储
     *
     * @return
     */
    public static Conversation build(ConversationModel model) {
        Conversation conversation = new Conversation();

        // 处理后端发过来的消息已经存在
        if (model.getConId() == null) {
            conversation.setId(UUID.randomUUID().toString());
        } else {
            conversation.setId(model.getConId());
        }
//        conversation.setChatId(model.getChatId());
        // 不管是哪里的数据，都会已经存在 chat id
        conversation.setChatId(model.getChatId());


        conversation.setSend(model.getFromId());
        conversation.setReceive(model.getToId());

        conversation.setContent(model.getContent());
        conversation.setCategory(model.getCategroy());
        conversation.setType(model.getType());

        // 重新规划时间
        conversation.setCreateTimeAt(new Date());

        conversation.setDone(model.isDone());
        return conversation;
    }

    /**
     * 用来建造重新发送的
     */
    public static ConversationPieces buildModel(Conversation conversation) {

        ConversationPieces pieces = new ConversationPieces();

        pieces.setConId(conversation.getId());

        pieces.setToId(conversation.getReceive());
        // 消息重新发的，一定要再数据库中存在
        if (conversation.getEvent() != null &&
                TextUtils.isEmpty(conversation.getEvent().getChatId())) {
            pieces.setChatId(conversation.getEvent().getChatId());
        } else {
            if (TextUtils.isEmpty(conversation.chatId)) {
                pieces.setChatId(conversation.chatId);
            } else {
                pieces.setChatId(conversation.getChatId());
            }
        }
        pieces.setFromId(conversation.getSend());

        pieces.setType(conversation.getType());
        pieces.setCreateTime(conversation.getCreateTimeAt().toString());

        ConversationPieces.InfoBean infoBean = new ConversationPieces.InfoBean();
        infoBean.setContent(conversation.getContent());
        infoBean.setCategory(conversation.getCategory());

        pieces.setInfo(infoBean);

        return pieces;
    }

    /**
     * @param conversation 后端传来的数据
     * @param isOpen
     * @return 这里只用于处理后端传来的数据
     */
    public static List<ConversationModel> decodeConText(String conversation, boolean isOpen) {

        ArrayList<ConversationModel> conversationModels = new ArrayList<>();

        PushPieces<List<ConversationPieces>> pushPieces = ClientHelper.getGson()
                .fromJson(conversation,
                        new TypeToken<PushPieces<List<ConversationPieces>>>() {
                        }.getType());


        if (pushPieces != null) {
            List<ConversationPieces> messageList = pushPieces.getMessage();
            if (messageList != null) {
                // todo 处理类型的问题
                for (ConversationPieces pieces : messageList) {
                    ConversationModel model = ConversationFactory.buildModel(pieces, isOpen);
                    conversationModels.add(model);
                }
            }
        }

        return conversationModels;
    }

    /**
     * @param event 用来处理消息已经读出的建造
     */
    public static ConversationAck buildModel(Event event) {
        ConversationAck pieces = new ConversationAck();

        pieces.setChatId(event.getChatId());
        Conversation conversation = SQLite.select().from(Conversation.class)
                .where(Conversation_Table.chatId.eq(event.getChatId())).querySingle();
        // fixme 这里只处理了单聊
        if (conversation != null) {
            pieces.setFromId(conversation.getSend());
            pieces.setToId(conversation.getReceive());
        }

        return pieces;
    }


    /**
     * 用来建造每一个会话,  只用来建造新的pieces
     */
    public static class Builder {

        private ConversationPieces pieces;
        private final ArrayList<ConversationPieces.InfoBean> infoBeans;

        public Builder() {
            this.pieces = new ConversationPieces();
            pieces.setFromId(Account.getUserId());
            infoBeans = new ArrayList<>();
        }


        public Builder setReceiver(String receiver, int type) {
            pieces.setToId(receiver);
            pieces.setType(type);
            return this;
        }

        public Builder addContent(String content, int category) {
            ConversationPieces.InfoBean infoBean = new ConversationPieces.InfoBean();
            infoBean.setContent(content);
            infoBean.setCategory(category);
            pieces.setInfo(infoBean);
            return this;
        }

        public ConversationPieces build() {

            // 我们这样设置， chatId， 指的是event 的id， 随机生成的id

            // 自我生成 uuid
            pieces.setConId(UUID.randomUUID().toString());

            String chatIdFromLocal = findChatIdFromLocal();

            if (chatIdFromLocal != null) {
                // 已经建立过了会话
                pieces.setChatId(chatIdFromLocal);
            } else {
                // 没有建立会话
                pieces.setChatId(UUID.randomUUID().toString());
            }
            return pieces;
        }

        private String findChatIdFromLocal() {
            if (!TextUtils.isEmpty(pieces.getFromId()) && !TextUtils.isEmpty(pieces.getToId())) {
                String toId = pieces.getToId();
                String fromId = pieces.getFromId();
                try {

                    // 查询两者之间是否有消息来往
                    Conversation conversation = SQLite.select()
                            .from(Conversation.class)
                            .where(OperatorGroup.clause()
                                    .and(Conversation_Table.receive.eq(toId))
                                    .and(Conversation_Table.send.eq(fromId)))
                            .querySingle();

                    if (conversation != null) {
                        // 如果有设置chatId
                        if (conversation.getChatId() != null) {
                            return conversation.getChatId();
                        }
                        // 发现有来往，则有event，
                        try {
                            Event table_event = conversation.getEvent();
                            // 如果没有关联
                            if (table_event == null) {
                                Event event = SQLite.select()
                                        .from(Event.class)
                                        .where(Event_Table.chatId.eq(conversation.getChatId()))
                                        .querySingle();
                                if (event != null) {
                                    return event.getChatId();
                                }
                            } else {
                                return table_event.getChatId();
                            }

                        } catch (Exception ignored) {

                        }

                    }
                } catch (Exception e) {
                }


            }

            return null;
        }
    }


    /**
     * @param conversation 处理来自原前端的消息发送，所以只有一条消息, 与后端的消息
     * @param isOpen  聊天界面的状态
     * @return
     */
    public static ConversationModel buildModel(ConversationPieces conversation, boolean isOpen) {
        ConversationModel conversationModel = new ConversationModel();

        conversationModel.setFromId(conversation.getFromId());

        conversationModel.setChatId(conversation.getChatId());

        conversationModel.setToId(conversation.getToId());

        // 如果来自于服务器，那么我们需要在这里自动的生成conid,并设置为，已经与后端交互
        if (TextUtils.isEmpty(conversation.getConId())) {
            conversationModel.setConId(UUID.randomUUID().toString());
        } else {
            // 手机发过来的信息
            conversationModel.setConId(conversation.getConId());
        }
        conversationModel.setDone(isOpen);
        conversationModel.setType(conversation.getType());

        // 不管是后端还是前端，我们都要设置为当前本地时间到达
        conversationModel.setCreateAt(new Date());

        conversationModel.setCategroy(conversation.getInfo().getCategory());
        conversationModel.setContent(conversation.getInfo().getContent());
        return conversationModel;
    }


    public static Event createEvent(Conversation conversation) {
        if (conversation != null && conversation.chatId != null) {
            Event event = new Event();

            event.setChatId(conversation.chatId);
            event.setContent(conversation.getContent());
            event.setModifyAt(conversation.getCreateTimeAt());
            event.setType(conversation.getType());
            event.setUnReadCount(0);

            event.setForSome(conversation.getReceive().equals(Account.getUserId()) ?
                    conversation.getSend() : conversation.getReceive());
            return event;
        }
        return null;
    }

}
