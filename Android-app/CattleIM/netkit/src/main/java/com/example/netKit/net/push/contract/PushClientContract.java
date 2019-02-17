package com.example.netKit.net.push.contract;

import com.example.netKit.net.push.PushClient;
import com.example.netKit.net.push.pieces.ConversationAck;
import com.example.netKit.net.push.pieces.ConversationPieces;
import com.example.netKit.net.push.pieces.PushPieces;

public interface PushClientContract {




    String CMD_ACTION = "CMD_ACTION";

    int MSG = 0;

    int PUSH_ID = 1;




    /**
     * websoket 发消息的时候的监听回调
     */
    interface MessageListener {

        void sendSuccess(String text, int status);

        void sendFailure(String text, int status);
    }

    interface BaseClient{
        void start(PushClient.PushListener push);

        void sendMessage(ConversationPieces pieces, PushClient.MessageListener listener);

        // 用来发送消息确定包
        void sendMessage(ConversationAck pieces, PushClient.MessageListener listener);

        void sendMessage(PushPieces pushPieces);

        void disConnect();

        void reConnect();
    }

}
