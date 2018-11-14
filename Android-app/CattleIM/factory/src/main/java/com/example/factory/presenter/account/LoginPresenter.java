package com.example.factory.presenter.account;

import android.text.TextUtils;

import com.example.common.factory.data.DataSource;
import com.example.common.tools.ValidateTools;
import com.example.factory.R;
import com.example.factory.contract.account.LoginContract;
import com.example.factory.contract.account.RegisterContract;
import com.example.factory.presenter.BasePresenter;
import com.example.netKit.db.User;
import com.example.netKit.piece.account.LoginPiece;

/**
 * DataSource 实现了presenter 与 Helper之间的监听通信，
 * Helper是实现数据本地化和数据网络请求的类， presenter是实现Helper与View时间的关系，
 * Helper大大分担了presenter的逻辑
 * BasePresenter 实现了所有的  presenter 的实例化，以及向view与presenter的传递
 */
public class LoginPresenter extends BasePresenter<LoginContract.View>
        implements LoginContract.Presenter, DataSource.Callback<User> {


    /**
     * 必须实现父类的构造方法，presenter的实例化在具体的类中实现
     * 只有构造出父类，才能构造出子类
     */
    public LoginPresenter(LoginContract.View view) {
        super(view);
    }


    @Override
    public void login(String phone, String password) {
        start();
        LoginPiece loginPiece = new LoginPiece(phone, password);
        AccountHelper.login(loginPiece, this);
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
    public boolean checkPsd(String psd) {
        if (TextUtils.isEmpty(psd)){
            getView().showError(R.string.NULL_PASSWORD);
        }
        return true;
    }

    // 接下来的方法处理，helper处完数据的回调

    @Override
    public void onDataLoaded(User user) {
        final LoginContract.View view = getView();
        if (view == null)
            return;

        // 此时是从网络回送回来的，并不保证处于主现场状态
        // 强制执行在主线程中, 因为传送文件是在子线程执行的

        if (user != null){
            view.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    view.loginSuccess();
                }
            });
        }
    }

    @Override
    public void onDataNotAvailable(int strRes) {
        getView().showError(strRes);
    }
}

