package com.example.factory.presenter.chat;

import android.util.Log;

import com.example.factory.Factory;
import com.example.netKit.NetKit;
import com.example.netKit.db.Conversation;
import com.example.netKit.db.Conversation_Table;
import com.example.netKit.model.ConversationModel;
import com.example.netKit.net.push.ConversationFactory;
import com.example.netKit.net.push.PushClient;
import com.example.netKit.net.push.pieces.ConversationPieces;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import okhttp3.WebSocket;

public class ConversationHelper {

    public final static String TAG = ConversationHelper.class.getName();

    // 从本地找消息
    public static Conversation findFromLocal(String id) {
        try {
            Conversation conversation = SQLite.select()
                    .from(Conversation.class)
                    .where(Conversation_Table.id.eq(id)).querySingle();
            return conversation;
        }catch (Exception ignored){

        }

        return null;
    }


    public static void push(final ConversationPieces conversation) {
        NetKit.runOnAsync(new Runnable() {
            @Override
            public void run() {

                if (conversation == null){
                    return;
                }
                //发过的消息不能再发
                Conversation con = findFromLocal(conversation.getConId());
                if (con != null && con.isDone()){
                    return;
                }

                // 当前用户的本地发送，我们进行本地的分发

                final ConversationModel conversationModel = ConversationFactory.buildModel(conversation, true);


                // todo  处理没有网络的直接回调


                PushClient.getInstance().sendMessage(conversation, new PushClient.MessageListener() {
                    /**
                     * 消息发送成功的回调
                     *
                     * @param webSocket
                     */
                    @Override
                    public void onFailure(WebSocket webSocket) {
                        conversationModel.setDone(false);
                        Factory.getConversationCenter().dispatch(conversationModel);
                        Log.e(TAG, "onFailure: " + "push text failure" );
                    }

                    @Override
                    public void onSuccess() {
                        conversationModel.setDone(true);
                        Factory.getConversationCenter().dispatch(conversationModel);
                        Log.e(TAG, "onSuccess: " + "push text success" );
                        Log.e(TAG, "onSuccess: " + conversationModel.toString() );
                    }
                });
            }
        });
    }
}
