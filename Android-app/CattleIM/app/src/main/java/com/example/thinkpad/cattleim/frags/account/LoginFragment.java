package com.example.thinkpad.cattleim.frags.account;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.common.app.BaseFragment;
import com.example.factory.model.AccountModel;
import com.example.factory.net.NetInterface;
import com.example.factory.net.Network;
import com.example.thinkpad.cattleim.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
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
        Call<AccountModel> login = connect.login(etPhone.getText().toString(), etPassword.getText().toString());
        login.enqueue(new Callback<AccountModel>() {
            @Override
            public void onResponse(Call<AccountModel> call, Response<AccountModel> response) {
                assert response.body() != null;
                Log.e("----->",  response.body().getToken());
                Log.e("----->",  response.body().getAvatar());
                Log.e("----->",  response.body().getName());
                Log.e("----->",  response.body().getStatus() + " ");

            }

            @Override
            public void onFailure(Call<AccountModel> call, Throwable t) {

            }
        });
    }


}
