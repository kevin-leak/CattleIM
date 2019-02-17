package com.example.factory.middleware.user;


import android.text.TextUtils;

import com.example.factory.contract.user.UserCenter;
import com.example.factory.middleware.DbHelper;
import com.example.netKit.db.User;
import com.example.netKit.model.UserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

        // 丢到单线程池中,进行异步存储
        executor.execute(new UserModelHandler(model));
    }

    /**
     * 用来异步存储用户数据
     * 1. 将数组转化为list
     * 2. 进行小部分的过滤
     * 3. 丢入Dbhlper  里面启用观察模式
     */
    class UserModelHandler implements Runnable {


        private UserModel[] models;

        public UserModelHandler(UserModel[] models) {
            this.models = models;
        }

        @Override
        public void run() {

            // 单被线程调度的时候触发
            List<User> users = new ArrayList<>();
            for (UserModel user : models) {
                // 进行过滤操作
                if (user == null || TextUtils.isEmpty(user.getId()))
                    continue;
                // 添加操作
                users.add(user.build());
            }

            // 进行数据库存储，并分发通知, 异步的操作
            DbHelper.save(User.class, users.toArray(new User[0]));

        }
    }
}
