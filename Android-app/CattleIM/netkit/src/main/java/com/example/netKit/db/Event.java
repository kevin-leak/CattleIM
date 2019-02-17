package com.example.netKit.db;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import static com.example.netKit.db.Conversation_Table.send;

@Table(database = AppDatabase.class)
public class Event extends BaseDdModel<Event> implements Serializable {


    final static String TAG = Event.class.getName();


    public static String INFO_AVATAR = "INFO_AVATAR";
    public static String INFO_NAME = "INFO_NAME";


    @PrimaryKey
    private String chatId; // 这个id 是建立message 时候生成的， chat id, 或者是群的id

    @Column
    private String forSome; // 这里确定出，是与什么进行会话的id

    @Column
    private String content; // 显示在界面上的简单内容，是Message的一个描述
    /**
     * 类型，对应人，或者群消息, 与消息那里相互呼应
     */
    @Column
    private int type;
    @Column
    private int unReadCount; // 未读数量，当没有在当前界面时，应当增加未读数量
    @Column
    private Date modifyAt; // 最后更改时间


    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUnReadCount() {
        return unReadCount;
    }

    public void setUnReadCount(int unReadCount) {
        this.unReadCount = unReadCount;
    }

    public Date getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }


    public String getForSome() {
        return forSome;
    }

    public void setForSome(String forSome) {
        this.forSome = forSome;
    }


    public HashMap<String, String> getSomeone() {
        HashMap<String, String> map = new HashMap<>();
        switch (type) {
            case Conversation.TYPE_GROUP:
                Group group = (Group) SQLite.select()
                        .from(Group.class)
                        .where(Group_Table.id.eq(forSome))
                        .querySingle();
                // todo 如果是群  后期处理
//                map.put(INFO_AVATAR, group.get)

            default:
                Log.e(TAG, "getSomeone: send :" + forSome);
                // 好友聊天，返回一个user
                User user = null;
//                try {
                    user = SQLite.select()
                            .from(User.class)
                            .where(User_Table.id.eq(forSome))
                            .querySingle();
//                } catch (Exception ignored) {
//                    ignored.getStackTrace();
//                }
                if (user != null) {
                    map.put(INFO_AVATAR, user.getAvatar());
                    map.put(INFO_NAME, user.getUsername());
                } else {
                    map.put(INFO_AVATAR, "");
                    map.put(INFO_NAME, "0");
                }

                return map;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return type == event.type &&
                unReadCount == event.unReadCount &&
                Objects.equals(chatId, event.chatId) &&
                Objects.equals(forSome, event.forSome) &&
                Objects.equals(content, event.content) &&
                Objects.equals(modifyAt, event.modifyAt);
    }

    @Override
    public int hashCode() {
        int result = chatId != null ? chatId.hashCode() : 0;
        result = 31 * result + type;
        return result;
    }

    @Override
    public boolean isSame(Event oldT) {
        return chatId.equals(oldT.chatId) && type == oldT.type;
    }

    @Override
    public boolean isUiContentSame(Event oldT) {
        return this.content.equals(oldT.content)
                && this.modifyAt.equals(oldT.modifyAt);
    }

}
