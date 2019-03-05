package com.example.thinkpad.cattleim.activities;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.example.common.app.Application;
import com.example.factory.contract.account.AccountInfoContract;
import com.example.factory.presenter.account.AccountInfoPresenter;
import com.example.factory.presenter.BasePresenterActivity;
import com.example.thinkpad.cattleim.R;
import com.example.thinkpad.cattleim.frags.media.GalleryFragment;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author kevin
 * <p>
 * 处理两个问题：
 * 1. 填写个人信息
 * 2. 存储在本地数据库
 * 3. 处理后端信息储存
 */
public class AccountInfoActivity extends BasePresenterActivity<AccountInfoContract.Presenter>
        implements AccountInfoContract.View {
    @BindView(R.id.profile_avatar)
    CircleImageView profileAvatar;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.sn_sex)
    Spinner snSex;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.met_username)
    MaterialEditText metUsername;
    @BindView(R.id.met_desc)
    MaterialEditText metDesc;
    private String mAvatarPath;
    private ProgressDialog dialog;

    /**
     * @return 返回一个资源文件
     */
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_account_info;
    }

    // 初始化完了后改变 Spinner文字标题颜色


    @Override
    protected void initWidget() {
        super.initWidget();

        snSex.setSelected(false);

        setAvatarAnimator(4, -4, 4, -4, 300);
    }


    @OnClick(R.id.profile_avatar)
    void onAvatarView() {
        new GalleryFragment()
                .setListener(new GalleryFragment.OnSelectedListener() {
                    @Override
                    public void onSelectedImage(String path) {
                        UCrop.Options options = new UCrop.Options();
                        // 设置图片处理的格式JPEG
                        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                        // 设置压缩后的图片精度
                        options.setCompressionQuality(96);

                        // 得到头像的缓存地址
                        File dPath = Application.getAvatarTmpFile();

                        // 发起剪切
                        UCrop.of(Uri.fromFile(new File(path)), Uri.fromFile(dPath))
                                .withAspectRatio(1, 1) // 1比1比例
                                .withMaxResultSize(520, 520) // 返回最大的尺寸
                                .withOptions(options) // 相关参数
                                .start(getActivity());
                    }
                }).show(getSupportFragmentManager(), GalleryFragment.class.getName());
    }

    @OnClick(R.id.btn_submit)
    void onSubmit() {

        String desc = metDesc.getText().toString();
        String username = metUsername.getText().toString();
        int sex = snSex.getId();
        if (TextUtils.isEmpty(mAvatarPath)){
            setAvatarAnimator(8, -8, 8, -8, 100);
        }else if (TextUtils.isEmpty(username)){
            metUsername.setError("用户名不能为空");
        }else if (TextUtils.isEmpty(desc)){
            metDesc.setError("自我描述不能为空");
        }else {
            presenter.addAccountInfo(mAvatarPath, username, desc, sex);
        }
    }

    /**
     * 图片循环的动画
     */
    private void setAvatarAnimator(int fx, int tx, int fy, int ty, int time) {
        TranslateAnimation animator = new TranslateAnimation( fx,  tx,  fy,  ty);
        //设置持续时间
        animator.setDuration(time);
        //设置重复次数
        animator.setRepeatCount(ValueAnimator.INFINITE);
        //设置方向直行
        animator.setRepeatMode(Animation.REVERSE);

        profileAvatar.startAnimation(animator);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        /// 收到从Activity传递过来的回调，然后取出其中的值进行图片加载
        // 如果是我能够处理的类型
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            // 通过UCrop得到对应的Uri
            final Uri resultUri = UCrop.getOutput(data);
            if (resultUri != null) {
                loadPortrait(resultUri);
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            Application.showToast(getActivity(), R.string.data_rsp_error_unknown);
        }
    }

    @Override
    public void showDialog() {
        super.showDialog();

        dialog = ProgressDialog.show(this.getActivity(), "加载中", "");
    }

    /**
     * 加载Uri到当前的头像中
     *
     * @param uri Uri
     */
    private void loadPortrait(Uri uri) {
        // 得到头像地址
        mAvatarPath = uri.getPath();

        Glide.with(AccountInfoActivity.this)
                .load(uri)
                .asBitmap()
                .centerCrop()
                .into(profileAvatar);
    }

    @Override
    public void addSuccess() {
        dialog.cancel();
        Intent intent = new Intent(AccountInfoActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 初始化presenter，要求子类必须实现
     */
    @Override
    protected AccountInfoContract.Presenter initPresent() {
        return new AccountInfoPresenter(this);
    }

}
