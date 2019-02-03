package com.example.thinkpad.cattleim.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.widget.AvatarView;
import com.example.factory.presenter.user.PersonContract;
import com.example.factory.presenter.user.PersonPresent;
import com.example.factory.presenter.user.UserHelper;
import com.example.factory.view.PresentToolActivity;
import com.example.thinkpad.cattleim.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("Registered")
public class PersonActivity extends
        PresentToolActivity<PersonContract.Presenter>
        implements PersonContract.View {


    private static final String USER_ID = "USER_ID";
    @BindView(R.id.im_header)
    ImageView imHeader;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.im_portrait)
    AvatarView imPortrait;
    @BindView(R.id.fab_follow)
    FloatingActionButton fabFollow;
    @BindView(R.id.txt_desc)
    TextView txtDesc;
    @BindView(R.id.btn_send_message)
    Button btnSendMessage;
    private String userId;

    public static void show(Context context, String userId) {
        context.startActivity(new Intent(context, PersonActivity.class).putExtra(USER_ID, userId));
    }

    /**
     * @return 返回一个资源文件
     */
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_person;
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        userId = bundle.getString(USER_ID);
        return super.initArgs(bundle);
    }

    @Override
    protected void initData() {
        super.initData();

        mPresenter.start();
    }

    @OnClick(R.id.btn_send_message)
    void sendMessage(){
        MessageActivity.show(PersonActivity.this, getUserId());
    }

    @OnClick(R.id.fab_follow)
    void addFriends(){
        
    }



    /**
     * 初始化Presenter
     *
     * @return Presenter
     */
    @Override
    protected PersonContract.Presenter initPresenter() {
        return new PersonPresent(this);
    }

    @Override
    public String getUserId() {
        return userId;
    }

}
