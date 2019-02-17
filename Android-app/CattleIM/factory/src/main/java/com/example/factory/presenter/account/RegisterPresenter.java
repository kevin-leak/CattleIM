package com.example.factory.presenter.account;

import com.example.common.factory.data.DataSource;
import com.example.common.utils.ValidateUtils;
import com.example.factory.R;
import com.example.factory.contract.account.RegisterContract;
import com.example.factory.presenter.BasePresenter;
import com.example.netKit.db.User;
import com.example.netKit.piece.account.RegisterPiece;

public class RegisterPresenter extends BasePresenter<RegisterContract.View>
        implements RegisterContract.Presenter, DataSource.Callback<User> {


    public RegisterPresenter(RegisterContract.View view) {
        super(view);
    }


    @Override
    public void register(final String phone,final String password) {
//        TODO 做数据检验并且回送消息到UI
        start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                    RegisterPiece registerPiece = new RegisterPiece(phone,  password);
                    AccountHelper.register(registerPiece, RegisterPresenter.this);
                }
//            }
        }).start();
    }

    @Override
    public boolean checkMobile(String phone) {

        if (ValidateUtils.isMobile(phone)){
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
        }else if(! ValidateUtils.isPassword(psd)){
            getView().showError(R.string.form_psd_error);
            return false;
        }

        return true;
    }

    @Override
    public boolean checkUserName(String username) {
        if (ValidateUtils.isUsername(username)){
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
        // 强制执行在主线程中, 因为传送文件是在子线程执行的

        if (user != null){
            view.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    view.registerSuccess();
                }
            });
        }

    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        final RegisterContract.View view = getView();

        view.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.showError(strRes);
            }
        });
    }
}
