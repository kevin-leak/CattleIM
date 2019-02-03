package com.example.factory.middleware.user;


import android.icu.lang.UCharacter;

import com.example.netKit.model.AccountModel;
import com.example.netKit.model.UserModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 做一个model的分发，开启一个线程对其进行一个数据的额存储处理
 */
public class UserDispatch implements UserCenter {
    private static UserCenter instance;
    //建立单线程
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public static UserCenter instance() {
        if (instance == null) {
            synchronized (UserDispatch.class) {
                if (instance == null)
                    instance = new UserDispatch();
            }
        }
        return instance;
    }


    @Override
    public void dispatch(UserModel... model) {
        if (model == null || model.length == 0)
            return;

        // 丢到单线程池中
        executor.execute(new UserModelHandler(model));
    }

    private class UserModelHandler implements Runnable {


        private UserModel[] models;

        public UserModelHandler(UserModel[] models) {
            this.models = models;
        }

        @Override
        public void run() {

        }
    }
}
