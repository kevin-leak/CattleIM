package com.example.netKit.net.push;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.common.Common;
import com.example.netKit.NetKit;
import com.example.netKit.net.CattleNetWorker;
import com.example.netKit.persistence.Account;

import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import static android.content.ContentValues.TAG;

/**
 * 1. 建立重新连接机制
 * 2. 消息发送机制
 * 3. push id 持久化
 * 4. 断包续传
 */
public class PushClient implements PushContract {

    private static PushClient pushClient;
    private Request request;
    private PushListener push;
    private WebSocket webSocket;
    private MessageListener listener;
    private boolean cancel = false;

    private PushClient() {
        initWebSocketClient();
    }

    /**
     * 允许被继承修改
     */
    public void initWebSocketClient() {
        request = new Request.Builder()
                .url(Common.Constance.WEB_SOCKET)
                .build();
        webSocket = CattleNetWorker.getClient()
                .newWebSocket(request, new StatusListener());

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

    private class StatusListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            // 如果当前是重启对象，则走重启对象的方法, 否则走成功信息发送的方法
            listener.sendSuccess(text);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            if (listener != null) {
                if (response != null && response.body() != null) {
                    listener.sendFailure("");
                } else {
                    listener.sendFailure("");
                }
            } else {
                reConnect();
            }
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            reConnect();
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            reConnect();
        }
    }


    @Override
    public void start(final PushListener push) {
        this.push = push;
        cancel = false;

        sendMessage(PushContract.connectMessage, new MessageListener() {
            @Override
            public void sendSuccess(String text) {
                push.messageArrival(text);
                Log.e(TAG, "sendSuccess: " + text);
                Account.setPushId(NetKit.getGson().fromJson(text, PushPieces.class).getPushId());
            }

            @Override
            public void sendFailure(String body) {
                // 调用进行一个重新连接
                reConnect();
            }

        });
    }


    @Override
    public void sendMessage(String message, MessageListener listener) {
        this.listener = listener;

        // 这里只处理wesocket 为空的情况，关于websocket的其他错误需要为外部进行拦截
        // 对于数据的错误我们在MessageListener 里面统一反馈

        if (webSocket != null) {
            webSocket.send(message);
        } else {
            this.listener.sendFailure("未知错误");
        }
    }


    /**
     * 获取到当重启成功的状态，同时处理重启websocket的任务
     * todo 处理当前未完成的任务
     */
    @Override
    public void reConnect() {
        if (cancel){
            Log.e(TAG, "reConnect: " + "sladjklakj" );
            return;
        }
        // 只要断了， 就必须重新建立， 不能复用之前websocket对象
        initWebSocketClient();
        // 给后端发送一个重连的包，更新以前断掉的连接
        sendMessage(PushContract.connectMessage, new MessageListener() {

            @Override
            public void sendSuccess(String text) {
                push.reStart();
            }

            @Override
            public void sendFailure(String body) {
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
        });
    }

    @Override
    public void disConnect() {
        if (webSocket != null) {
            webSocket.cancel();
        }
        cancel = true;
        Log.e(TAG, "disConnect: " + "skdj;lak");
    }


    /**
     * 1. 第一建立连接
     * 2. 消息到达，发送消息
     */
    public interface PushListener {

        void reStart();

        /**
         * @param message 这里的就是原生的message，经过状态过滤， 解析由factory去做
         */
        void messageArrival(String message);
    }
}
