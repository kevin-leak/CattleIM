package com.example.factory.presenter.todo;

import com.example.factory.contract.todo.TimeLineContract;
import com.example.factory.contract.todo.TimeLineDataSource;
import com.example.factory.middleware.todo.TimeLineRepository;
import com.example.factory.presenter.BaseSourcePresenter;
import com.example.netKit.db.TimeLine;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

public class TimeLinePresenter extends BaseSourcePresenter<TimeLine, TimeLine, TimeLineDataSource, TimeLineContract.View>
        implements TimeLineContract.Presenter {


    public TimeLinePresenter(TimeLineContract.View view) {
        super(new TimeLineRepository(), view);
    }


    @Override
    public void onDataLoaded(List<TimeLine> timeLines) {


    }
}
