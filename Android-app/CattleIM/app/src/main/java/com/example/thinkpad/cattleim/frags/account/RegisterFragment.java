package com.example.thinkpad.cattleim.frags.account;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.EditText;

import com.example.common.app.Application;
import com.example.common.app.BaseFragment;
import com.example.common.tools.StringsTools;
import com.example.factory.contract.account.RegisterContract;
import com.example.factory.presenter.account.RegisterPresenter;
import com.example.factory.view.BasePresenterFragment;
import com.example.netKit.piece.RspPiece;
import com.example.netKit.piece.account.AccountPiece;
import com.example.netKit.piece.account.RegisterPiece;
import com.example.netKit.net.NetInterface;
import com.example.netKit.net.NetWorker;
import com.example.thinkpad.cattleim.R;
import com.example.thinkpad.cattleim.activities.AccountActivity;
import com.example.thinkpad.cattleim.frags.media.GalleryFragment;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.Objects;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends BasePresenterFragment<RegisterContract.Presenter>
        implements RegisterContract.View{

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void register(String path, String username) {
        String phone = etPhone.getText().toString();
        String rePsd = etRePsd.getText().toString();
        String password = etPassword.getText().toString();
        String avatar = StringsTools.ImageToStrings(path);
        presenter.register(phone, username, rePsd, avatar);
    }

    @Override
    protected RegisterContract.Presenter initPresent() {
        return new RegisterPresenter(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void registerSuccess() {
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
}
