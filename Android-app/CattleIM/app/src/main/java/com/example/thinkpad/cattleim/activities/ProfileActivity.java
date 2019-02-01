package com.example.thinkpad.cattleim.activities;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.common.app.BaseActivity;
import com.example.common.widget.AvatarView;
import com.example.factory.contract.account.ProfileContract;
import com.example.factory.presenter.account.ProfilePresenter;
import com.example.factory.view.PresentToolActivity;
import com.example.netKit.db.User;
import com.example.netKit.persistence.Account;
import com.example.thinkpad.cattleim.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;

public class ProfileActivity extends PresentToolActivity<ProfileContract.Presenter>
        implements ProfileContract.View {

    private static boolean OUT_FLAG = false;
    private static Context context;
    @BindView(R.id.im_header)
    ImageView imHeader;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.im_portrait)
    AvatarView imPortrait;
    @BindView(R.id.met_phone)
    MaterialEditText metPhone;
    @BindView(R.id.met_sex)
    MaterialEditText metSex;
    @BindView(R.id.met_desc)
    MaterialEditText metDesc;
    private String TAG = "ProfileActivity";

    public static void show(Context context) {
        ProfileActivity.context = context;
        context.startActivity(new Intent(context, ProfileActivity.class));
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void initWidget() {
        super.initWidget();
        mToolbar.setCollapsible(true);
    }

    @Override
    protected void initData() {
        super.initData();

        if (Account.isLogin() && Account.isComplete()){
            loadInfo(Account.getUser());
        }else {
            mPresenter.getAccountInfo(Account.getUserId());
        }
    }

    /**
     * @return 返回一个资源文件
     */
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_profile;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_edit:
                break;
            case R.id.item_out:
                mPresenter.loginOut(Account.getUserId());
                OUT_FLAG = true;
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化Presenter
     *
     * @return Presenter
     */
    @Override
    protected ProfileContract.Presenter initPresenter() {
        return new ProfilePresenter(this);
    }

    @Override
    public void loadInfo(User user) {
        Log.e(TAG, "loadInfo: " + "load" );
        imPortrait.setup((Glide.with(ProfileActivity.this)), user);
        txtName.setText(user.getUsername());
        metPhone.setText(user.getPhone());
        metSex.setText((user.getSex() != 0) ? "女" : "男");
        metDesc.setText(user.getDesc());
    }


    @Override
    public void finish() {
        super.finish();

        if (context != null && OUT_FLAG){
            ((BaseActivity) context).finish();
        }
    }
}
