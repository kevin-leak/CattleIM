package com.example.factory.presenter.account;

import android.text.TextUtils;

import com.example.common.factory.data.DataSource;
import com.example.factory.R;
import com.example.factory.contract.account.AccountInfoContract;
import com.example.factory.presenter.BasePresenter;
import com.example.factory.presenter.FileHelper;
import com.example.netKit.db.User;
import com.example.netKit.persistence.Account;
import com.example.netKit.piece.account.AccountInfoPiece;

public class AccountInfoPresenter extends BasePresenter<AccountInfoContract.View>
        implements AccountInfoContract.Presenter, DataSource.Callback<User>{


    public AccountInfoPresenter(AccountInfoContract.View view) {
        super(view);
    }

    @Override
    public void addAccountInfo(final String path, final String username, final String desc, final int sex) {
        start();
        new Thread(new Runnable() {
            @Override
            public void run() {
//                 todo 后期进行分离，注册的逻辑太多了，而且如果没有登入不允许上传文件
                String avatarUrl = FileHelper.fetchBackgroundFile(path);
                if (TextUtils.isEmpty(avatarUrl)){
                    getView().showError(R.string.data_network_error);
                }else {
                    AccountInfoPiece infoPiece = new AccountInfoPiece(avatarUrl, Account.getUserId(),username, desc, sex);
                    AccountHelper.completeInfo(infoPiece, AccountInfoPresenter.this);
                }
            }
        }).start();
    }

    @Override
    public void onDataLoaded(User user) {
        getView().getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getView().addSuccess();
            }
        });
    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        getView().getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getView().showError(strRes);
            }
        });
    }
}
