package com.example.netKit.net.push;

import android.util.Log;

import com.example.common.Common;
import com.example.netKit.net.CattleNetWorker;
import com.example.netKit.net.push.contract.PushClientContract;
import com.example.netKit.net.push.helper.ClientHelper;
import com.example.netKit.net.push.pieces.MessagePieces;
import com.example.netKit.net.push.pieces.PushPieces;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.WebSocket;

/**
 * 建立三种回调
 * 1. 连接的回调
 * 2. 服务器发来的消息回调
 * 3. 本身发送消息的回调
 */
public class PushClient implements PushClientContract.BaseClient, PushClientContract.MessageListener {

    final static String TAG = "PushClient";


    private static PushClient pushClient;
    private Request request;
    private WebSocket webSocket;
    /**
     * 用来处理断网时，切断连接
     */
    private boolean cancel = false;
    /**
     * 用来处理与服务之间的回调
     */
    private PushListener pushListener;
    /**
     * 处理消息发送之间成功与失败的回调
     */
    private MessageListener listener;

    private PushClient() {
        initWebSocketClient(ClientHelper.StatusListener.START);
        ClientHelper.createGson();
    }

    /**
     * 建立单列模式
     */
    public static PushClient getInstance() {
        if (pushClient == null) {
            pushClient = new PushClient();
        }
        return pushClient;
    }


    /**
     * 允许被继承修改
     */
    public void initWebSocketClient(int mode) {
        request = new Request.Builder()
                .url(Common.Constance.WEB_SOCKET)
                .build();
        webSocket = CattleNetWorker.getClient()
                .newWebSocket(request, new ClientHelper.StatusListener(this, mode));
    }


    @Override
    public void start(PushListener pushListener) {
        this.pushListener = pushListener;
        cancel = false;
        // 此时client 刚开始初始化， Helper 里面的mode是 开启模式
        sendMessage(new PushPieces<String>("开启连接", PushPieces.CONNECT));
    }

    @Override
    public void sendMessage(MessagePieces pieces, MessageListener listener) {
//        todo 是否做拦截，发多条,或者单条

        ArrayList<MessagePieces> messagePieces = new ArrayList<>();
        messagePieces.add(pieces);
        String messageJson = MessageFactory.build(messagePieces);
//        String messageJson = MessageFactory.build(pieces);

        this.listener = listener;
        if (webSocket != null) {
            webSocket.send(messageJson);
        } else {
            reConnect();
        }
    }

    /**
     * @param pushPieces 这个用处理信息正在service 上的回调
     */
    @Override
    public void sendMessage(PushPieces pushPieces) {
        if (webSocket != null) {
            webSocket.send(pushPieces.toJson());

        } else {
            reConnect();
        }
    }

    @Override
    public void disConnect() {
        if (webSocket != null) {
            webSocket.cancel();
        }
        cancel = true;
    }

    @Override
    public void reConnect() {
        if (cancel) {
            return;
        }

        // 只要断了， 就必须重新建立， 不能复用之前websocket对象
        initWebSocketClient(ClientHelper.StatusListener.RE_CONNECT);
        cancel = false;
        // 此时client 刚开始初始化， Helper 里面的mode是 开启模式
        sendMessage(new PushPieces<String>("重新连接", PushPieces.CONNECT));
    }

    @Override
    public void sendSuccess(String text, int status) {

        // text 解析，1. 发出消息时的回调 2. 消息到来
        PushPieces pushPieces = null;
        try {
            pushPieces = ClientHelper
                    .getGson().fromJson(text, new TypeToken<PushPieces<String>>() {
                    }.getType());
        }catch (Exception ignored){
            pushPieces = ClientHelper
                    .getGson().fromJson(text, new TypeToken<PushPieces<List<MessagePieces>>>() {
                    }.getType());
        }
        //  如果message是ok证明是消息包
        if (!pushPieces.getMessage().equals("ok")){
            pushListener.messageArrival(text);
        }else {
            String pushId = pushPieces.getPushId();
            switch (status){
                case ClientHelper.StatusListener.START:
                    Log.e(TAG, "sendSuccess: " + "START" );
                    pushListener.onStart(pushId);
                    break;
                case ClientHelper.StatusListener.RE_CONNECT:
                    Log.e(TAG, "sendSuccess: " + "RE_CONNECT" );
                    pushListener.reStart(pushId);
                    break;
                case ClientHelper.StatusListener.MESSAGE:
                    Log.e(TAG, "sendSuccess: " + "MESSAGE" );
                    if (listener != null)
                    listener.onSuccess();
            }

        }


    }

    @Override
    public void sendFailure(String text, int status) {

        if (ClientHelper.StatusListener.RE_CONNECT == status) {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        Thread.sleep(3000);//休眠1秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // TODO 进行一个当前网络的一个判断来决定是否要进行重新连接还是通知用户开启网络
                    reConnect();
                }
            }.start();

        }
    }


    /**
     * 用来处理推送的逻辑
     * 处理一些别人发来的信息
     */
    public interface PushListener {


        /**
         * 推送开启的回调
         * @param text
         */
        void onStart(String text);

        void reStart(String text);

        /**
         * @param message 这里的就是原生的message，经过状态过滤， 解析由factory去做
         */
        void messageArrival(String message);
    }

    /**
     * 后面来添加回调的参数， 用来处理重新连接，以再次发送的建立等等其他问题
     */
    public interface MessageListener {

        /**
         * 消息发送成功的回调
         */
        void onFailure();

        /**
         * 消息发送失败的回调
         */
        void onSuccess();
    }
}
