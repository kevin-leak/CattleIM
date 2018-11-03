package com.example.factory.presenter.account;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;

import com.example.common.factory.data.DataSource;
import com.example.common.tools.ValidateTools;
import com.example.factory.R;
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
                if (TextUtils.isEmpty(avatarUrl)){
                    getView().showError(R.string.form_avatar_error);
                }else {
                    RegisterPiece registerPiece = new RegisterPiece(phone, name, password, avatarUrl);
                    AccountHelper.register(registerPiece, RegisterPresenter.this);
                }
            }
        }).start();
    }

    @Override
    public boolean checkMobile(String phone) {

        if (ValidateTools.isMobile(phone)){
            return true;
        }
        getView().showError(R.string.form_phone_error);
        return false;
    }

    @Override
    public boolean checkPsd(String psd, String re_psd) {

        if (!psd.equals(re_psd)){
            getView().showError(R.string.data_psd_not_same);
            return false;
        }else if(! ValidateTools.isPassword(psd)){
            getView().showError(R.string.form_psd_error);
            return false;
        }

        return true;
    }

    @Override
    public boolean checkUserName(String username) {
        if (ValidateTools.isUsername(username)){
            return true;
        }
        getView().showError(R.string.form_username_error);
        return false;
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
