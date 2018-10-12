package com.example.thinkpad.cattleim;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.common.app.BaseActivity;
import com.example.thinkpad.cattleim.activities.AccountActivity;

import pub.devrel.easypermissions.EasyPermissions;

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
                Intent intent = new Intent(LaunchActivity.this, AccountActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
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
