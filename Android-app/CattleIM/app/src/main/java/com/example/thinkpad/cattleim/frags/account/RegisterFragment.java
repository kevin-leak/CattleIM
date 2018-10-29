package com.example.thinkpad.cattleim.frags.account;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.EditText;

import com.example.common.app.BaseFragment;
import com.example.factory.contract.account.RegisterContract;
import com.example.factory.presenter.account.RegisterPresenter;
import com.example.factory.view.BasePresenterFragment;
import com.example.netKit.piece.RspPiece;
import com.example.netKit.piece.account.AccountPiece;
import com.example.netKit.piece.account.RegisterPiece;
import com.example.netKit.net.NetInterface;
import com.example.netKit.net.NetWorker;
import com.example.thinkpad.cattleim.R;
import com.example.thinkpad.cattleim.activities.AccountActivity;

import java.util.Objects;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends BasePresenterFragment<RegisterContract.Presenter>
        implements RegisterContract.View{

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_re_psd)
    EditText etRePsd;


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_register;
    }

    public void register(String avatar, String username) {
        String password = etPassword.getText().toString();
        String phone = etPhone.getText().toString();
        String rePsd = etRePsd.getText().toString();
        presenter.register(phone, username, rePsd, avatar);
    }

    @Override
    protected RegisterContract.Presenter initPresent() {
        return new RegisterPresenter(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void registerSuccess() {
         ((AccountActivity) Objects.requireNonNull(getActivity())).trigger();
    }
}
