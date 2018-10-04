package com.example.common.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author kevin
 * 事件 --> 数据跳转 --> 初始化布局 --> 初始化控件 --> 初始化布局 --> 回退
 * 处理一个root复用问题
 * 处理fragment返回问题
 */
public abstract class BaseFragment extends android.support.v4.app.Fragment {

    private View mRoot;
    private Unbinder unbinder;

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
        initData();
    }

    protected void initData() {

    }

    protected void initWidgets(View root){
        unbinder = ButterKnife.bind(this, root);

    }

    protected void intiArgs(Bundle arguments) {

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
