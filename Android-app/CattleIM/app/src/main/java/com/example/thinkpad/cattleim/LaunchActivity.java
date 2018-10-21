package com.example.thinkpad.cattleim;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.common.app.BaseActivity;
import com.example.factory.net.Network;
import com.example.thinkpad.cattleim.activities.AccountActivity;

import okhttp3.ResponseBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaunchActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(LaunchActivity.this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 10);
            }

        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                getToken();
                Intent intent = new Intent(LaunchActivity.this, AccountActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);


    }

    /**
     * 第一次访问获取token值
     */
    private void getToken() {
        Network.getConnect().getCall().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }




    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_launch;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (!Settings.canDrawOverlays(this)) {
                    // SYSTEM_ALERT_WINDOW permission not granted...
                    Toast.makeText(LaunchActivity.this, "not granted", Toast.LENGTH_SHORT);
                }
            }
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(LaunchActivity.this, AccountActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }


}
