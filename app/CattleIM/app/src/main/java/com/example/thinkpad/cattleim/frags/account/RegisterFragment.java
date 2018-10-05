package com.example.thinkpad.cattleim.frags.account;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.common.app.BaseFragment;
import com.example.thinkpad.cattleim.R;

public class RegisterFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activtiy_register;
    }
}
