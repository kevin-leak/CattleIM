package com.example.factory.contract.todo;

import com.example.factory.contract.BaseContract;
import com.example.netKit.db.TimeLine;

public interface TimeLineContract {
    interface Presenter extends BaseContract.Presenter {

    }

    interface View extends BaseContract.RecyclerView<Presenter, TimeLine> {


    }
}
