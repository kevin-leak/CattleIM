package com.example.thinkpad.cattleim.frags.account;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;

import com.example.common.app.BaseFragment;
import com.example.netKit.piece.account.AccountPiece;
import com.example.netKit.piece.account.RegisterPiece;
import com.example.netKit.net.NetInterface;
import com.example.netKit.net.Network;
import com.example.thinkpad.cattleim.R;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends BaseFragment {

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
        NetInterface connect = Network.getConnect();
        RegisterPiece registerModel = new RegisterPiece(phone, username, password, avatar);
        Log.e("TAG", "register: " + registerModel.getPhone());
        Call<AccountPiece> register = connect.register(registerModel);
        register.enqueue(new Callback<AccountPiece>() {
            @Override
            public void onResponse(@NonNull Call<AccountPiece> call, Response<AccountPiece> response) {
                assert response.body() != null;
                Log.e("register", response.body().getName());
            }

            @Override
            public void onFailure(@NonNull Call<AccountPiece> call, Throwable t) {

            }
        });
    }

}
