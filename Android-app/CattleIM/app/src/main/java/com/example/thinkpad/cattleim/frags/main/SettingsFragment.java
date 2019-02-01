package com.example.thinkpad.cattleim.frags.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.common.app.BaseFragment;
import com.example.common.widget.AvatarView;
import com.example.netKit.persistence.Account;
import com.example.thinkpad.cattleim.R;
import com.example.thinkpad.cattleim.activities.ProfileActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SettingsFragment extends BaseFragment {


    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.ll_account)
    LinearLayout llAccount;
    @BindView(R.id.civ_avatar)
    AvatarView civAvatar;

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
        civAvatar.setup((Glide.with(getActivity())), Account.getUser());
    }


    @OnClick(R.id.tv_account)
    void onclick() {
//        NetInterface connect = CattleNetWorker.getConnect();
//        connect.logout().enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });

        ProfileActivity.show(SettingsFragment.this.getActivity());
    }


}
