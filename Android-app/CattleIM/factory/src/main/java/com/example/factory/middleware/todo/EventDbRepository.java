package com.example.factory.middleware.todo;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.common.app.Application;
import com.example.common.utils.NotificationUtils;
import com.example.factory.R;
import com.example.factory.contract.todo.EventDataSource;
import com.example.factory.middleware.BaseDbRepository;
import com.example.netKit.db.Event;
import com.example.netKit.db.Event_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class EventDbRepository extends BaseDbRepository<Event> implements EventDataSource {

    final static String TAG = EventDbRepository.class.getName();


    @Override
    public void load(SucceedCallback<List<Event>> callback) {
        super.load(callback);

        // 数据库查询
        SQLite.select()
                .from(Event.class)
                .orderBy(Event_Table.modifyAt, false)
                .limit(100)
                .async()
                .queryListResultCallback(this)
                .execute();
    }

    @Override
    protected void insert(Event event) {
        dataList.addFirst(event);
    }

    /**
     * 对于不同的数据进行过滤
     *
     * @param event 需要使用的数据
     * @return 是否被需要
     */
    @Override
    public boolean isRequired(Event event) {
        return true;
    }


    @Override
    public void onListQueryResult(QueryTransaction transaction, @NonNull List<Event> tResult) {

        Log.e(TAG, "onListQueryResult: " + tResult.size() );

        // 反转返回的集合
        Collections.reverse(tResult);

        super.onListQueryResult(transaction, tResult);
    }
}
