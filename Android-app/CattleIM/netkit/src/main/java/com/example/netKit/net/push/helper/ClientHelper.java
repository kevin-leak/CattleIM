package com.example.netKit.net.push.helper;

import android.util.Log;

import com.example.netKit.net.push.contract.PushClientContract;
import com.example.netKit.utils.DBFlowExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.Objects;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class ClientHelper {

    final static String TAG = "ClientHelper";

    private static Gson gson;


    public static Gson getGson() {
        return gson;
    }


    public static void createGson() {
        gson = new GsonBuilder()
                // 设置时间格式
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                // 设置一个过滤器，数据库级别的Model不进行Json转换
                .setExclusionStrategies(new DBFlowExclusionStrategy())
                .create();
    }

    public static void createGson(String format) {
        gson = new GsonBuilder()
                // 设置时间格式
                .setDateFormat(format)
                // 设置一个过滤器，数据库级别的Model不进行Json转换
                .setExclusionStrategies(new DBFlowExclusionStrategy())
                .create();
    }


    public boolean isJson(String content) {

        try {

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 这是与后端交互信息的，结果
     */
    public static class StatusListener extends WebSocketListener {

        public final static int START = 0;
        public final static int RE_CONNECT = 1;

        /**
         * 这个状态是：客户端没有发送消息等待回调，
         * 只是与服务器保持通讯
         */
        public final static int MESSAGE = 2;


        /**
         * 发送消息的模式: 0：开启模式， 1：重新连接， 2：通信模式
         */
        private int mode;

        /**
         * 用来对后端信息的回调
         */
        private final PushClientContract.MessageListener listener;

        public StatusListener(PushClientContract.MessageListener listener, int mode) {

            this.listener = listener;
            this.mode = mode;
        }

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {


            if (listener != null)
                // 如果当前是重启对象，则走重启对象的方法, 否则走成功信息发送的方法
                this.listener.sendSuccess(text, mode);

            Log.e(TAG, "onMessage: " + text );
            // 如果启动成功，我们将当前的 mode 修改为通信模式
            if (mode != MESSAGE) {
                mode = MESSAGE;
            }

        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
//            if (listener != null) {
//                if (response != null && response.body() != null) {
//                    listener.sendFailure("");
//                } else {
//                    listener.sendFailure("");
//                }
//            } else {

            // 分析发送失败的原因，不一定是连接问题

            Log.e(TAG, "onFailure: " + t.getMessage() + " ==== ");
            if ("Connection reset".equals(t.getMessage())) {
                mode = RE_CONNECT;
            }
            if (listener != null)
                listener.sendFailure("", mode);


//            }
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {

            Log.e(TAG, "onClosing: " + code + "===" + reason + mode);

            // 发生关闭，那么我们需要怎么处理？

            if (code == 1000)
                mode = RE_CONNECT;
            if (listener != null)
                listener.sendFailure("", mode);
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {

            Log.e(TAG, "onClosing: " + code + "===" + reason);

            if (listener != null)
                listener.sendFailure("", mode);
        }


    }


}
