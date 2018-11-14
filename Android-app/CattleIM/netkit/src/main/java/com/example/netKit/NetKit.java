package com.example.netKit;

import android.content.Intent;
import android.support.annotation.StringRes;

import com.example.common.app.Application;
import com.example.common.factory.data.DataSource;
import com.example.netKit.db.User;
import com.example.netKit.net.push.PushService;
import com.example.netKit.persistence.Account;
import com.example.netKit.piece.RspPiece;
import com.example.netKit.utils.DBFlowExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



/*
 * fixme
 * 1. 处理网络本地化数据， 处理将数据转化为bean
 * 2. 处理数据持久化，处理数据的缓存
 * 3. 处理各种消息的接收，处理网络报活机制 ,处理消息的接收的服务
 * 处理网络，以及性能优化问题
 * */


/**
 * 1. 处理多线程网络请求问题
 * 2. 处理db初始化
 * 3. 处理状态码解码，已经回调
 * */
public class NetKit {


    private static Gson gson;
    private final ExecutorService executor;
    private static NetKit netKit;

    //    建立单例模式
    private NetKit() {
        executor = Executors.newFixedThreadPool(4);
        gson = new GsonBuilder()
                // 设置时间格式
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                // 设置一个过滤器，数据库级别的Model不进行Json转换
                .setExclusionStrategies(new DBFlowExclusionStrategy())
                .create();
    }

    static {
        netKit = new NetKit();
    }

    public static NetKit getInstance() {
        return netKit;
    }


    public static void runOnAsync(Runnable runnable) {
        // 拿到单例，拿到线程池，然后异步执行
        netKit.executor.execute(runnable);
    }

    public static Application app(){
        return Application.getInstance();
    }

    public static void initDb(){
        // 初始化数据库
        FlowManager.init(new FlowConfig.Builder(app())
                .openDatabasesOnInit(true) // 数据库初始化的时候就开始打开
                .build());

        // 持久化的数据进行初始化
        Account.init(NetKit.app());

    }


    public static void decodeRep(RspPiece piece, DataSource.FailedCallback callback){
        switch (piece.getStatus()){
            case RspPiece.SUCCEED:
                return;
            case RspPiece.ERROR_NET:
                decodeRep(R.string.data_network_error, callback);
                break;
            case RspPiece.ERROR_PASSWORD:
                decodeRep(R.string.error_password, callback);
                break;
            case RspPiece.ERROR_REPEAT_LOGIN:
                decodeRep(R.string.error_repeat_login, callback);
                break;
            case RspPiece.FORMAT_ERROR_AVATAR:
                decodeRep(R.string.form_avatar_error, callback);
                break;
            case RspPiece.FORMAT_ERROR_FILE:
                decodeRep(R.string.FORMAT_ERROR_FILE, callback);
                break;
            case RspPiece.SAME_PHONE:
                decodeRep(R.string.SAME_PHONE, callback);
                break;
            case RspPiece.SAME_USERNAME:
                decodeRep(R.string.same_username, callback);
                break;
            case RspPiece.NULL_DATA:
                decodeRep(R.string.NULL_DATA, callback);
                break;
            case RspPiece.EXIST_FRIENDS:
                decodeRep(R.string.friend_relation_exit, callback);
                break;
        }
    }

    public static void  decodeRep(@StringRes final int resId,
                                  final DataSource.FailedCallback callback){
        if (callback != null){
            callback.onDataNotAvailable(resId);
        }
    }

    /**
     * @return 返回一个个全局的gson 字段，用来处理piece里面想Date的字段，进行一个formate
     */
    public static Gson getGson() {
        return gson;
    }

    /**
     * 初始化推送的服务
     */
    public static void initPush() {
        Intent intent = new Intent(app(), PushService.class);
        app().startService(intent);
    }
}
