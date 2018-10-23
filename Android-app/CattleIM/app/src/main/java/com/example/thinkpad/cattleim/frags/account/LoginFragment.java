package com.example.thinkpad.cattleim.frags.account;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.common.app.BaseFragment;
import com.example.netKit.piece.account.AccountPiece;
import com.example.netKit.net.NetInterface;
import com.example.netKit.net.Network;
import com.example.thinkpad.cattleim.R;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 进行登入事件的检验， 以及错误的显示
 */
public class LoginFragment extends BaseFragment {

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
        NetInterface connect = Network.getConnect();
        Call<AccountPiece> login = connect.login(etPhone.getText().toString(), etPassword.getText().toString());
        login.enqueue(new Callback<AccountPiece>() {
            @Override
            public void onResponse(Call<AccountPiece> call, Response<AccountPiece> response) {
                assert response.body() != null;
                Log.e("----->",  response.body().getAvatar());
                Log.e("----->",  response.body().getName());
                Log.e("----->",  response.body().getStatus() + " ");

            }

            @Override
            public void onFailure(Call<AccountPiece> call, Throwable t) {

            }
        });
    }


}
