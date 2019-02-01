package com.example.factory.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import com.example.common.app.Application;
import com.example.common.widget.ToolbarActivity;
import com.example.factory.R;
import com.example.factory.contract.BaseContract;

public abstract class PresentToolActivity<Presenter extends BaseContract.Presenter>
        extends ToolbarActivity implements BaseContract.View<Presenter> {


    protected Presenter mPresenter;
    protected ProgressDialog mLoadingDialog;

    @Override
    protected void initBefore() {
        super.initBefore();

        mPresenter = initPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 界面关闭时进行销毁的操作
        if (mPresenter != null) {
            mPresenter.destroy();
        }
    }

    /**
     * 初始化Presenter
     *
     * @return Presenter
     */
    protected abstract Presenter initPresenter();

    @Override
    public void showError(int str) {
        // 不管你怎么样，我先隐藏我
        hideLoading();
        Application.showToast(getActivity(),str);
    }

    @Override
    public void showDialog() {
        ProgressDialog dialog = mLoadingDialog;
        if (dialog == null) {
            // todo 制定加载框的演示
//            dialog = new ProgressDialog(this, R.style.AppTheme_Dialog_Alert_Light);
            // 不可触摸取消
            dialog.setCanceledOnTouchOutside(false);
            // 强制取消关闭界面
            dialog.setCancelable(true);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });
            mLoadingDialog = dialog;

//            dialog.setMessage(getText(R.string.prompt_loading));
            dialog.show();
        }
    }

    protected void hideDialog() {
        ProgressDialog dialog = mLoadingDialog;
        if (dialog != null) {
            mLoadingDialog = null;
            dialog.dismiss();
        }
    }

    protected void hideLoading() {
        // 不管你怎么样，我先隐藏我
        hideDialog();
    }

    @Override
    public void setPresenter(Presenter present) {

        this.mPresenter = present;
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}
