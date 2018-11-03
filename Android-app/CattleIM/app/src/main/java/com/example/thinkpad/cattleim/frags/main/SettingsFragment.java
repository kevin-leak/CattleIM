package com.example.thinkpad.cattleim.frags.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.common.app.BaseFragment;
import com.example.netKit.net.CattleNetWorker;
import com.example.netKit.net.NetInterface;
import com.example.thinkpad.cattleim.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends BaseFragment {


    @BindView(R.id.tv_account)
    TextView tvAccount;

    @Override
    protected void initWidgets(View root) {
        super.initWidgets(root);

    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_settings;
    }

    @Override
    protected void initData() {
        super.initData();


    }

    @OnClick(R.id.tv_account)
    void onclick(){
        NetInterface connect = CattleNetWorker.getConnect();
        connect.logout().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }



}
