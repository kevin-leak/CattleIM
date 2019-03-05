package com.example.factory.contract.todo;

import com.example.factory.contract.BaseContract;

import java.util.ArrayList;
import java.util.Date;

public interface TimeCreatorContract extends BaseContract {

    interface Presenter extends BaseContract.Presenter{

        void add(String content, Date start, Date end, int type);
    }


    interface View extends BaseContract.View<Presenter>{

    }
}
