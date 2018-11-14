package com.example.factory.presenter.search;

import android.util.Log;

import com.example.common.factory.data.DataSource;
import com.example.factory.contract.search.SearchContract;
import com.example.factory.presenter.BasePresenter;
import com.example.factory.presenter.user.UserHelper;
import com.example.netKit.model.UserModel;

import java.util.List;

import retrofit2.Call;

import static android.content.ContentValues.TAG;

public class SearchUserPresenter extends BasePresenter<SearchContract.UserView>
        implements SearchContract.Presenter, DataSource.Callback<List<UserModel>> {


    private Call callSearch;

    public SearchUserPresenter(SearchContract.UserView view) {
        super(view);
    }

    @Override
    public void search(String content, int page) {
        start();
        if (page >= 0){
            Call call = callSearch;
            if (call != null && !call.isCanceled()) {
                call.cancel();
            }
            callSearch = UserHelper.searchBack(content, page, this);
        }
    }

    @Override
    public void onDataLoaded(final List<UserModel> userModels) {
        final SearchContract.UserView view = getView();


        if (view != null){
            view.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "onDataLoaded: " + "--------------");
                    view.onSearchDone(userModels);
                }
            });
        }
    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        final SearchContract.UserView view = getView();
        if (view != null){
            view.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    view.showError(strRes);
                }
            });
        }
    }


}
