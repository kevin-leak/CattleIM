package com.example.thinkpad.cattleim.frags.main;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.common.app.BaseFragment;
import com.example.thinkpad.cattleim.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class TodoFragment extends BaseFragment {



    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_todo;
    }

    @Override
    protected void initData() {
        super.initData();

    }

}
