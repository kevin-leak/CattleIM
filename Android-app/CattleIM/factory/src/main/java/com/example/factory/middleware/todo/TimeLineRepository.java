package com.example.factory.middleware.todo;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.factory.contract.todo.TimeLineDataSource;
import com.example.factory.middleware.BaseDbRepository;
import com.example.netKit.db.Event;
import com.example.netKit.db.Event_Table;
import com.example.netKit.db.TimeLine;
import com.example.netKit.db.TimeLine_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.util.Collections;
import java.util.List;

public class TimeLineRepository extends BaseDbRepository<TimeLine> implements TimeLineDataSource {
    /**
     * 对于不同的数据进行过滤
     *
     * @param timeLine 需要使用的数据
     * @return 是否被需要
     */
    @Override
    public boolean isRequired(TimeLine timeLine) {
        return true;
    }


    @Override
    public void load(SucceedCallback<List<TimeLine>> callback) {
        super.load(callback);



        // 数据库查询
        SQLite.select()
                .from(TimeLine.class)
                .orderBy(TimeLine_Table.startTime, false)
                .async()
                .queryListResultCallback(this)
                .execute();
    }

    @Override
    public void onListQueryResult(QueryTransaction transaction, @NonNull List<TimeLine> tResult) {

        // 反转返回的集合
        Collections.reverse(tResult);

        super.onListQueryResult(transaction, tResult);
    }



}
