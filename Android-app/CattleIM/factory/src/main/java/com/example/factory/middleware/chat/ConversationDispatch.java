package com.example.factory.middleware.chat;

import android.text.TextUtils;
import android.util.Log;

import com.example.factory.contract.chat.ConversationCenter;
import com.example.factory.middleware.DbHelper;
import com.example.factory.middleware.user.UserDispatch;
import com.example.factory.presenter.chat.ConversationHelper;
import com.example.netKit.db.Conversation;
import com.example.netKit.model.ConversationModel;
import com.example.netKit.net.push.ConversationFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConversationDispatch implements ConversationCenter {

    final static String TAG = "ConversationDispatch";

    private static ConversationCenter instance;
    //建立单线程
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public static ConversationCenter instance() {
        if (instance == null) {
            synchronized (UserDispatch.class) {
                if (instance == null)
                    instance = new ConversationDispatch();
            }
        }
        return instance;
    }


    @Override
    public void dispatch(ConversationModel... model) {
        if (model == null || model.length == 0)
            return;

        // 丢到单线程池中
        executor.execute(new ConversationModelHandler(model));
    }

    private class ConversationModelHandler implements Runnable {
        private ConversationModel[] models;

        public ConversationModelHandler(ConversationModel[] models) {
            this.models = models;
        }


        @Override
        public void run() {
            List<Conversation> conversations = new ArrayList<>();
            // 遍历
            for (ConversationModel model : models) {
                // 卡片基础信息过滤，错误卡片直接过滤
                if (model == null || TextUtils.isEmpty(model.getFromId())
                        || TextUtils.isEmpty(model.getFromId())
                        || (TextUtils.isEmpty(model.getChatId())))
                    continue;

                // 发送消息流程：写消息->发送网络->网络返回->存储本地->->刷新本地状态
                // 查询是否在本地数据库创建，如果已经创建了的，一定是从摸个数据的状态发生了变化，只需要做修改
                Conversation conversation = ConversationHelper.findFromLocal(model.getConId());
                if (conversation == null) {
                    
                    // 没找到本地消息，初次在数据库存储
                    // 可能是本地数据的第一次发送
                    // 可能是后端推送过来的数据
                    conversation = ConversationFactory.build(model);
                    Log.e(TAG, "run: " + "模型转换" );
                }else {
                    // 处理重新发送的消息，需要将其的状态改变
                    conversation.setDone(model.isDone());
                }
                if (conversation != null)
                    conversations.add(conversation);
            }

            Log.e(TAG, "run: " + conversations.size() );
            if (conversations.size() > 0) {
                DbHelper.save(Conversation.class, conversations.toArray(new Conversation[0]));
            }
        }
    }
}
