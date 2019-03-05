package com.example.factory.contract.todo;

import com.example.factory.contract.BaseContract;
import com.example.netKit.db.LinkTask;

public interface TaskCreatorContract {

    interface Presenter extends BaseContract.Presenter{

    }

    interface View extends BaseContract.View<Presenter>{

    }
}
