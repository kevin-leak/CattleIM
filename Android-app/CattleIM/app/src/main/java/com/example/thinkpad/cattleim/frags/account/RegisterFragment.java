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

    public void register() {
        String phone = etPhone.getText().toString();
        String rePsd = etRePsd.getText().toString();
        String password = etPassword.getText().toString();


        if (presenter.checkMobile(phone) &&
                presenter.checkPsd(password, rePsd)) {
            presenter.register(phone, rePsd);
        }

    }

    @Override
    protected RegisterContract.Presenter initPresent() {
        return new RegisterPresenter(this);
    }

    @Override
    public void registerSuccess() {

        if (dialog != null){
            dialog.cancel();
        }

        if (getActivity() != null)
            ((AccountActivity) getActivity()).trigger();
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
