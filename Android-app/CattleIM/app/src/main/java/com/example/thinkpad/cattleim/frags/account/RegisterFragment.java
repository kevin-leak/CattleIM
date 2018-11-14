package com.example.thinkpad.cattleim.frags.account;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.widget.EditText;

import com.example.common.app.Application;
import com.example.factory.contract.account.RegisterContract;
import com.example.factory.presenter.account.RegisterPresenter;
import com.example.factory.view.BasePresenterFragment;
import com.example.thinkpad.cattleim.R;
import com.example.thinkpad.cattleim.activities.AccountActivity;
import com.example.thinkpad.cattleim.frags.media.GalleryFragment;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.Objects;

import butterknife.BindView;

import static com.example.netKit.db.User_Table.avatar;
import static com.example.netKit.db.User_Table.id;

public class RegisterFragment extends BasePresenterFragment<RegisterContract.Presenter>
        implements RegisterContract.View {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_re_psd)
    EditText etRePsd;
    private ProgressDialog dialog;


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_register;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void register(String path, String username) {
        String phone = etPhone.getText().toString();
        String rePsd = etRePsd.getText().toString();
        String password = etPassword.getText().toString();

        if (TextUtils.isEmpty(path)) {
            showError(R.string.null_avatar);
        }

        if (presenter.checkUserName(username) &&
                presenter.checkMobile(phone) &&
                presenter.checkPsd(password, rePsd)) {
            presenter.register(phone, username, rePsd, path);
        }

    }

    @Override
    protected RegisterContract.Presenter initPresent() {
        return new RegisterPresenter(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void registerSuccess() {

        if (dialog != null){
            dialog.cancel();
        }
        ((AccountActivity) Objects.requireNonNull(getActivity())).trigger();
    }

    /**
     * 获取图片信息
     * todo 设置拍照获取
     */
    public void getAvatar() {
        new GalleryFragment()
                .setListener(new GalleryFragment.OnSelectedListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
                                .start(Objects.requireNonNull(getActivity()));
                    }
                })// show 的时候建议使用getChildFragmentManager，
                // tag GalleryFragment class 名
                .show(getChildFragmentManager(), GalleryFragment.class.getName());
    }


    @Override
    public void showDialog() {
        super.showDialog();

        dialog = ProgressDialog.show(this.getActivity(), "加载中", "");
    }

    @Override
    public void showError(int error) {
        super.showError(error);
        if (dialog != null){
            dialog.cancel();
        }
    }

}
