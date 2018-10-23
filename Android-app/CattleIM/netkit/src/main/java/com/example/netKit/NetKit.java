package com.example.netKit;

import com.example.common.app.Application;

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
 * 处理多线程网络请求问题
 *
 * */
public class NetKit {


    private final ExecutorService executor;
    private static NetKit netKit;

    //    建立单例模式
    private NetKit() {
        executor = Executors.newFixedThreadPool(4);
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

}
