package com.example.netKit.db;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Table(database = AppDatabase.class)
public class Conversation extends BaseDdModel<Conversation> implements Serializable {

    // 规定是什么类型的新（tag, group, user
    // "0":"系统消息",
    // "1":"普通消息",
    // "2":"请求消息",
    // "3":"主题消息",
    // "4":"公告消息",
    // "5":"任务消息",
    // "6":"关联消息",
    // "7":"群消息",
    public static final int TYPE_SYS = 0;
    public static final int TYPE_SIMPLE = 1;
    public static final int TYPE_REQUEST = 2;
    public static final int TYPE_TOPIC = 3;
    public static final int TYPE_TIPS = 4;
    public static final int TYPE_TASK = 5;
    public static final int TYPE_LINK = 6;
    public static final int TYPE_GROUP = 7;


    // 规定对话的类型
    // "0":"文本",
    // "1":"图片",
    // "2":"语音",
    // "3":"文件"
    public static final int CATEGORY_TEXT = 0;
    public static final int CATEGORY_PIC = 1;
    public static final int CATEGORY_SOUND = 2;
    public static final int CATEGORY_FILE = 3;




    // 主键
    @PrimaryKey
    private String id;

    @Column
    private int type;


    @Column
    private String send;

    @Column
    private String receive;

    @Column
    private int category;

    @Column
    private boolean done;

    @Column
    private String content;

    @Column
    private Date createTimeAt;// 创建时间

    @Column
    public String chatId;



    @ForeignKey(tableClass = Event.class, stubbedRelationship = true)
    private Event  event = null; // 用来会话与event 的绑定



    public String getId() {
        return id;
    }

    public String getSend() {
        return send;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public BaseDdModel getSender() {
        switch (type){
            case TYPE_GROUP:
                return SQLite.select()
                        .from(Group.class)
                        .where(Group_Table.id.eq(send))
                        .querySingle();
            default:
                // 好友聊天，返回一个user
                return SQLite.select()
                        .from(User.class)
                        .where(User_Table.id.eq(send))
                        .querySingle();
        }
    }



    public void setSend(String send) {
        this.send = send;
    }

    public String getReceive() {
        return receive;
    }

    public void setReceive(String receive) {
        this.receive = receive;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTimeAt() {
        return createTimeAt;
    }

    public void setCreateTimeAt(Date createTimeAt) {
        this.createTimeAt = createTimeAt;
    }

//    public String getChatId() {
//        return chatId;
//    }
//
//    public void setChatId(String chatId) {
//        this.chatId = chatId;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Conversation that = (Conversation) o;


        return type == that.type && category == that.category &&
                done == that.done && id.equals(that.id) &&
                send.equals(that.send) && receive.equals(that.send) &&
                content.equals(that.content) && createTimeAt.equals(that.createTimeAt) &&
                chatId.equals(that.chatId);

    }

    @Override
    public String toString() {
        return "Conversation{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", send='" + send + '\'' +
                ", receive='" + receive + '\'' +
                ", category=" + category +
                ", done=" + done +
                ", content='" + content + '\'' +
                ", createTimeAt=" + createTimeAt +
                ", chatId='" + chatId + '\'' +
                ", event=" + event +
                '}';
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean isSame(Conversation oldT) {
        // 两个类，是否指向的是同一个消息
        return id.equals(oldT.id);
    }

    @Override
    public boolean isUiContentSame(Conversation oldT) {
        // 对于同一个消息当中的字段是否有不同
        // 这里，对于消息，本身消息不可进行修改；只能添加删除
        // 唯一会变化的就是本地（手机端）消息的状态会改变
        return oldT == this || isDone() == oldT.isDone();
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
}
