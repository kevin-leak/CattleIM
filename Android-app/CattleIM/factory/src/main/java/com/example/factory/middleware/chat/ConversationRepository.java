package com.example.factory.middleware.chat;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.factory.middleware.BaseDbRepository;
import com.example.factory.contract.chat.ConversationDataSource;
import com.example.netKit.db.Conversation;
import com.example.netKit.db.Conversation_Table;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.util.Collections;
import java.util.List;

public class ConversationRepository
        extends BaseDbRepository<Conversation> implements ConversationDataSource {


    final String TAG = "ConversationRepository";

    private final String receiverId;



    public ConversationRepository(String receiverId) {
        super();
        this.receiverId = receiverId;
    }


    @Override
    public void load(SucceedCallback<List<Conversation>> callback) {
        super.load(callback);
        Log.e(TAG, "load: " + "-----本地数据加载-----" );
        SQLite.select()
                .from(Conversation.class)
                .where(OperatorGroup.clause()
                        .and(Conversation_Table.type.eq(Conversation.TYPE_SIMPLE))
                        .and(Conversation_Table.send.eq(receiverId))
                .or(Conversation_Table.receive.eq(receiverId)))
                .orderBy(Conversation_Table.createTimeAt, false)
                .limit(30)
                .async()
                .queryListResultCallback(this)
                .execute();

    }

    @Override
    public boolean isRequired(Conversation conversation) {
        // receiverId 如果是发送者，那么Group==null情况下一定是发送给我的消息
        // 如果消息的接收者不为空，那么一定是发送给某个人的，这个人只能是我或者是某个人
        // 如果这个"某个人"就是receiverId，那么就是我需要关注的信息
//        return (receiverId.equalsIgnoreCase(((User)conversation.getSender()).getId())
//                && conversation.getType() == Conversation.TYPE_SIMPLE)
//                || (conversation.getSend() != null
//                && receiverId.equalsIgnoreCase(conversation.getReceive())
//        );
        // todo 限定过滤条件
        return true;
    }


    @Override
    public void onListQueryResult(QueryTransaction transaction, @NonNull List<Conversation> tResult) {
        Log.e(TAG, "onListQueryResult: " + tResult.size());
        // 反转返回的集合
        Collections.reverse(tResult);
        // 然后再调度
        super.onListQueryResult(transaction, tResult);


    }
}
