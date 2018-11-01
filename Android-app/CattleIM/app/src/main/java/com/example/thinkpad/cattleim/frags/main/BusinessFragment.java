package com.example.thinkpad.cattleim.frags.main;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.common.Common;
import com.example.common.app.BaseFragment;
import com.example.thinkpad.cattleim.R;
import com.example.thinkpad.cattleim.services.StepService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


// todo 此处类等以后修改，现在主要实现了，一开始进入，在桌面出现步骤框

public class BusinessFragment extends BaseFragment {

    public StepService.LocalBinder mBinder;
    @BindView(R.id.wv_business)
    WebView wvBusiness;


    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mBinder = (StepService.LocalBinder) binder;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    private LocationReceiver locationReceiver;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_business;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initData() {
        super.initData();

        startStepService();

        // 步骤窗格
        locationReceiver = new LocationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("step");
        getActivity().registerReceiver(locationReceiver, filter);

//        wvBusiness.loadUrl("http://192.168.136.100:8000/android/web_view/index.html");

        WebSettings webSettings = wvBusiness.getSettings();

        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        wvBusiness.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        wvBusiness.loadUrl(Common.Constance.WEB_VIEW_URL + "index.html");

    }

    @OnClick(R.id.re_load)
    void OnClick(View v){
        wvBusiness.loadUrl(Common.Constance.WEB_VIEW_URL + "index.html");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        stopStepService();
    }

    private void stopStepService() {
        getActivity().unbindService(conn);
    }

    /**
     * 启动步骤悬浮框
     */
    private void startStepService() {

        Intent intent = new Intent(getContext(), StepService.class);
        //开启服务
        getActivity().bindService(intent, conn, Context.BIND_AUTO_CREATE);

        backHome();
    }

    /**
     * 返回到桌面
     */
    void backHome() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }


    //内部类，实现BroadcastReceiver
    public class LocationReceiver extends BroadcastReceiver {
        //必须要重载的方法，用来监听是否有广播发送
        @Override
        public void onReceive(Context context, Intent intent) {
            String intentAction = intent.getAction();
            if (intentAction.equals("step")) {
                if (intent.getBooleanExtra("cancel", false)) {
                    stopStepService();
                }
            }
        }
    }


}
