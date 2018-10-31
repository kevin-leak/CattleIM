package com.example.factory.presenter.account;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.common.factory.data.DataSource;
import com.example.factory.contract.account.RegisterContract;
import com.example.factory.presenter.BasePresenter;
import com.example.factory.presenter.FileHelper;
import com.example.netKit.db.IMFile;
import com.example.netKit.db.User;
import com.example.netKit.piece.account.RegisterPiece;

public class RegisterPresenter extends BasePresenter<RegisterContract.View>
        implements RegisterContract.Presenter, DataSource.Callback<User> {


    public RegisterPresenter(RegisterContract.View view) {
        super(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void register(final String phone, final String name, final String password, final String avatarPath) {
//        TODO 做数据检验并且回送消息到UI

        new Thread(new Runnable() {
            @Override
            public void run() {
                String avatarUrl = FileHelper.fetchBackgroundFile(avatarPath);
                RegisterPiece registerPiece = new RegisterPiece(phone, name, password, avatarUrl);
                AccountHelper.register(registerPiece, RegisterPresenter.this);
            }
        }).start();
    }

    @Override
    public boolean checkMobile(String phone) {

        // todo 电话号码的检验
//        RegisterContract.View view = getView();
//        view.showError(R.string.FORM_TYPE_ERROR_HONE);
        return true;
    }

    @Override
    public void onDataLoaded(User user) {

        final RegisterContract.View view = getView();
        if (view == null)
            return;

        // 此时是从网络回送回来的，并不保证处于主现场状态
        // fixme 强制执行在主线程中
    }

    @Override
    public void onDataNotAvailable(int strRes) {

    }
}
