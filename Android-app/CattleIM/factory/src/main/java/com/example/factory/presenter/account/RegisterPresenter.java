package com.example.factory.presenter.account;

import com.example.common.factory.data.DataSource;
import com.example.factory.R;
import com.example.factory.contract.account.RegisterContract;
import com.example.factory.presenter.BasePresenter;
import com.example.factory.view.BasePresenterFragment;
import com.example.netKit.db.User;
import com.example.netKit.piece.account.RegisterPiece;

public class RegisterPresenter extends BasePresenter<RegisterContract.View>
        implements RegisterContract.Presenter, DataSource.Callback<User> {


    public RegisterPresenter(RegisterContract.View view) {
        super(view);
    }

    @Override
    public void register(String phone, String name, String password, String avatar) {
//        TODO 做数据检验并且回送消息到UI
        RegisterPiece registerPiece = new RegisterPiece(phone, name, password, avatar);
        AccountHelper.register(registerPiece,this);
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
