package com.example.common.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.common.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author kevinleak
 * 事件 --> 数据跳转 --> 初始化布局 --> 初始化控件 --> 初始化布局 --> 回退
 * 处理一个root复用问题
 * 处理fragment返回问题
 */
public abstract class BaseFragment extends Fragment {

    private View mRoot;
    private Unbinder unbinder;
    private boolean mIsFirstInitData = true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        intiArgs(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // 处理复用问题
        if (mRoot == null){
            // 将attachRoot 设置为false， 等activity 后面进行一个插入
            View root = inflater.inflate(getContentLayoutId(), container, false);
            initWidgets(root);
            initData();
            mRoot = root;
        }else {
            // 如果用缓存，那么将我们以前的跟布局一出，再将我们的缓冲的mRoot 返回
            if (mRoot.getParent() != null){
                ((ViewGroup)mRoot.getParent()).removeView(mRoot);
            }
        }
        return mRoot;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mIsFirstInitData) {
            // 触发一次以后就不会触发
            mIsFirstInitData = false;
            // 触发
            onFirstInit();
        }

        // 当View创建完成后初始化数据
        initData();
    }

    protected void initData() {

    }

    protected void initWidgets(View root){
        unbinder = ButterKnife.bind(this, root);

    }

    protected void intiArgs(Bundle arguments) {

    }


    /**
     * 当首次初始化数据的时候会调用的方法
     */
    protected void onFirstInit() {

    }

    protected abstract int getContentLayoutId();

    /**
     * 返回按键触发时调用
     *
     * @return 返回True代表我已处理返回逻辑，Activity不用自己finish。
     * 返回False代表我没有处理逻辑，Activity自己走自己的逻辑
     */
    public boolean onBackPressed() {

        return false;
    }



}
