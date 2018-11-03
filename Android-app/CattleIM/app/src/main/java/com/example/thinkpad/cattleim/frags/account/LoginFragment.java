package com.example.thinkpad.cattleim.frags.account;

import android.app.ProgressDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.EditText;
import android.widget.TextView;

import com.example.factory.contract.account.LoginContract;
import com.example.factory.presenter.account.LoginPresenter;
import com.example.factory.view.BasePresenterFragment;
import com.example.thinkpad.cattleim.R;
import com.example.thinkpad.cattleim.activities.AccountActivity;

import java.util.Objects;

import butterknife.BindView;

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
    private ProgressDialog dialog;


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_login;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void login(){
        String phone = etPhone.getText().toString();
        String psd = etPassword.getText().toString();
        if (presenter.checkMobile(phone)){
            presenter.login(phone, psd);
        }
    }


    @Override
    protected LoginContract.Presenter initPresent() {
        return new LoginPresenter(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void loginSuccess() {
        if (dialog != null){
            dialog.cancel();
        }
        // 登入成功，发生跳转
        ((AccountActivity) Objects.requireNonNull(getActivity())).trigger();
    }


    @Override
    public void showDialog() {
        super.showDialog();
        dialog = ProgressDialog.show(this.getActivity(), "加载中", "");
    }

    @Override
    public void showError(int error) {
        super.showError(error);
        if (dialog != null){
            dialog.cancel();
        }
    }
}
