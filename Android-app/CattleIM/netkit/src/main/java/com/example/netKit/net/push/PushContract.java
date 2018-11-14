package com.example.netKit.net.push;

import com.example.netKit.NetKit;
import com.example.netKit.persistence.Account;

public interface PushContract {

    PushPieces<String> connectPieces = new PushPieces<String>(1, Account.getPushId(), "");

    String connectMessage = NetKit.getGson().toJson(connectPieces);

    String MSG = "MSG";
    /**
     * @param push 传入一个监视者
     *             开启对后端消息的监控
     */
    void start(PushClient.PushListener push);

    /**
     * 发送消息给其他客户端
     *
     * @param message 传入一个json文本信息
     */
    void sendMessage(String message, MessageListener listener);


    /**
     * 重新连接
     */
    void reConnect();


    /**
     * 用来处理，用户关闭了网络，我们需要将我们的重连机制断开
     * 以避免不必要的消费
     */
    void disConnect();


    /**
     * 发消息的时候的监听回调
     */
    interface MessageListener {

        void sendSuccess(String text);

        void sendFailure(String body);
    }

}
