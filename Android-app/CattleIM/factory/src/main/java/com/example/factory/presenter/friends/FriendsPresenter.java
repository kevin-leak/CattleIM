package com.example.factory.presenter.friends;

import android.support.v7.util.DiffUtil;
import android.util.Log;

import com.example.common.factory.data.DataSource;
import com.example.common.widget.recycler.RecyclerAdapter;
import com.example.factory.middleware.user.ContactDataSource;
import com.example.factory.middleware.user.FriendsRepository;
import com.example.factory.presenter.BaseSourcePresenter;
import com.example.factory.presenter.user.UserHelper;
import com.example.netKit.db.User;
import com.example.netKit.utils.DiffUiDataCallback;

import java.util.List;

/**
 * 加好友的类
 */
public class FriendsPresenter extends BaseSourcePresenter<User, User, ContactDataSource, FriendsContract.View>
        implements FriendsContract.Presenter, DataSource.SucceedCallback<List<User>> {

    final String TAG = "FriendsPresenter";



    public FriendsPresenter(FriendsContract.View view) {
        super(new FriendsRepository(), view);

        UserHelper.refreshContacts();
    }


    @Override
    public void onDataLoaded(List<User> users) {
        // 无论怎么操作，数据变更，最终都会通知到这里来
        FriendsContract.View view = getView();
        if (view == null)
            return;

        RecyclerAdapter<User> adapter = view.getRecyclerAdapter();
        List<User> old = adapter.getItems();



        // 进行数据对比
        DiffUtil.Callback callback = new DiffUiDataCallback<>(old, users);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        // 调用基类方法进行界面刷新
        refreshData(result, users);
    }
}
