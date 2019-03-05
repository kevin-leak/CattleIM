package com.example.factory.presenter.todo;

import com.example.factory.contract.todo.TaskCreatorContract;
import com.example.factory.presenter.BasePresenter;
import com.example.factory.presenter.BasePresenterActivity;

public class TaskCreatorPresenter extends BasePresenter<TaskCreatorContract.View>
        implements TaskCreatorContract.Presenter {


    public TaskCreatorPresenter(TaskCreatorContract.View view) {
        super(view);
    }

    
}
