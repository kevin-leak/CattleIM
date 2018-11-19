package com.example.factory.presenter.friends;

import com.example.common.app.Application;
import com.example.common.factory.data.DataSource;
import com.example.factory.presenter.BasePresenter;
import com.example.factory.presenter.user.UserHelper;
import com.example.netKit.model.AccountModel;
import com.example.netKit.model.UserModel;

public class FriendPresenter extends BasePresenter<FriendContract.View>
        implements FriendContract.Present, DataSource.Callback<UserModel> {


    public FriendPresenter(FriendContract.View viewHolder) {
        super(viewHolder);

    }

    @Override
    public void onDataLoaded(UserModel accountModel) {

    }

    @Override
    public void onDataNotAvailable(int strRes) {
        Application.showToast(getView().getActivity(), strRes);
        getView().showError(strRes);
    }

    @Override
    public void addFriend(String id) {
        UserHelper.createRelation(id, this);
    }
}
