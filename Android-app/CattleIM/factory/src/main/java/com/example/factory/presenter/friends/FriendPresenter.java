package com.example.factory.presenter.friends;

import android.support.annotation.Nullable;

import com.example.common.factory.data.DataSource;
import com.example.factory.R;
import com.example.factory.presenter.BasePresenter;
import com.example.factory.presenter.user.UserHelper;
import com.example.netKit.model.UserModel;

public class FriendPresenter extends BasePresenter<FriendContract.View>
        implements FriendContract.Present, DataSource.Callback<UserModel> {


    public FriendPresenter(FriendContract.View viewHolder) {
        super(viewHolder);


    }

    @Override
    public void onDataLoaded(UserModel userModel) {

    }

    @Override
    public void onDataNotAvailable(int strRes) {
        getView().showError(strRes);
    }

    @Override
    public void addFriend(String id) {
        UserHelper.createRelation(id, this);
    }
}
