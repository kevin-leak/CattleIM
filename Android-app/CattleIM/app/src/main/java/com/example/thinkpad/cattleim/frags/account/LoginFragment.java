package com.example.thinkpad.cattleim.frags.account;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.factory.contract.BaseContract;
import com.example.factory.contract.account.LoginContract;
import com.example.factory.presenter.account.LoginPresenter;
import com.example.factory.view.BasePresenterFragment;
import com.example.netKit.piece.RspPiece;
import com.example.netKit.piece.account.AccountPiece;
import com.example.netKit.net.NetInterface;
import com.example.netKit.net.NetWorker;
import com.example.netKit.piece.account.LoginPiece;
import com.example.thinkpad.cattleim.R;
import com.example.thinkpad.cattleim.activities.AccountActivity;

import java.util.Objects;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 进行登入事件的检验， 以及错误的显示
 */
public class LoginFragment extends BasePresenterFragment<LoginContract.Presenter>
        implements LoginContract.View {

    private static final String TAG = "LoginFragment";
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_forget)
    TextView tvForget;


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_login;
    }


    public void login(){
        Log.e(TAG, "login: get it");


        presenter.login(etPhone.getText().toString(), etPassword.getText().toString());
    }


    @Override
    protected LoginContract.Presenter initPresent() {
        return new LoginPresenter(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void loginSuccess() {
        // 登入成功，发生跳转
        ((AccountActivity) Objects.requireNonNull(getActivity())).trigger();
    }

    // 可以复写父类的方法实现对话框的消息展示，以及错误展示



}
