
package com.example.factory.middleware.todo;

import com.example.factory.contract.todo.EventCenter;
import com.example.factory.middleware.DbHelper;
import com.example.factory.middleware.user.UserDispatch;
import com.example.netKit.db.Event;
import com.example.netKit.model.ConversationModel;
import com.example.netKit.model.EventModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class EventDispatch implements  EventCenter{

    private static  EventCenter instance;

    //建立单线程
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public static EventCenter instance() {
        if (instance == null) {
            synchronized (UserDispatch.class) {
                if (instance == null)
                    instance = new EventDispatch();
            }
        }
        return instance;
    }


    @Override
    public void dispatch(Event... model) {
        if (model == null || model.length == 0)
            return;

        // 丢到单线程池中
        executor.execute(new EventModelHandler(model));
    }

    private class EventModelHandler implements Runnable {

        private final Event[] model;

        public EventModelHandler(Event... model) {
            this.model = model;
        }


        @Override
        public void run() {
            if (model.length > 0)
            DbHelper.save(Event.class, model);
        }
    }
}