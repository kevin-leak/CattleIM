package com.example.thinkpad.cattleim;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.common.app.BaseActivity;
import com.example.netKit.NetKit;
import com.example.netKit.net.CattleNetWorker;
import com.example.netKit.net.push.PushService;
import com.example.netKit.persistence.Account;
import com.example.thinkpad.cattleim.activities.AccountActivity;
import com.example.thinkpad.cattleim.activities.MainActivity;
import com.example.thinkpad.cattleim.frags.assist.NotificationsUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaunchActivity extends BaseActivity {

    private Class<?> intentClass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 初始化push的服务
        NetKit.initPush();


        // 选择跳转
        intentClass = AccountActivity.class;

        if (Account.isLogin()) {
            intentClass = MainActivity.class;
        }

//        if (Build.VERSION.SDK_INT >= 23) {
//            if (!Settings.canDrawOverlays(LaunchActivity.this)) {
//                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                        Uri.parse("package:" + getPackageName()));
//                startActivityForResult(intent, 10);
//            }
//
//        }


        getPermission(new PermissionListener() {
            @Override
            public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                new Handler().postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    public void run() {
//                        initPermission();
                        updateCattle();
                        Intent intent = new Intent(LaunchActivity.this, intentClass);
                        startActivity(intent);
                    }
                }, 3000);
            }

            @Override
            public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                new Handler().postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    public void run() {
//                        initPermission();
                        updateCattle();
                        Intent intent = new Intent(LaunchActivity.this, intentClass);
                        startActivity(intent);
                    }
                }, 3000);
            }
        });


    }

    /**
     * 获取当前版本信息，与后端进行一个比对，进行版本更新，获取token值
     */
    private void updateCattle() {
        CattleNetWorker.getConnect().getCall().enqueue(new Callback<ResponseBody>() {
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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 10) {
//            if (Build.VERSION.SDK_INT >= 23) {
//                if (!Settings.canDrawOverlays(this)) {
//                    // SYSTEM_ALERT_WINDOW permission not granted...
//                    Toast.makeText(LaunchActivity.this, "not granted", Toast.LENGTH_SHORT);
//                }
//            }
//        }
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                updateCattle();
//                Intent intent = new Intent(LaunchActivity.this, intentClass);
//                startActivity(intent);
//                finish();
//            }
//        }, 1000);
//    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initPermission() {
        String permissions[] = {
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.WAKE_LOCK,
                Manifest.permission.CAMERA,
                Manifest.permission.RECEIVE_BOOT_COMPLETED,
                Manifest.permission.SYSTEM_ALERT_WINDOW,
                Manifest.permission.RECEIVE_BOOT_COMPLETED
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                //进入到这里代表没有权限.
                Log.e("--------->", "没有权限");
            } else {

                Log.e("--------->", "已经被授权");
            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }


        if (!NotificationsUtils.isNotificationEnabled(this)) {
            final AlertDialog dialog = new AlertDialog.Builder(this).create();
            dialog.show();

            View view = View.inflate(this, R.layout.dialog, null);
            dialog.setContentView(view);

            TextView context = (TextView) view.findViewById(R.id.tv_dialog_context);
            context.setText("检测到您没有打开通知权限，是否去打开");

            TextView confirm = (TextView) view.findViewById(R.id.btn_confirm);
            confirm.setText("确定");
            confirm.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dialog.cancel();
                    Intent localIntent = new Intent();
                    localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (Build.VERSION.SDK_INT >= 9) {
                        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                        localIntent.setData(Uri.fromParts("package", LaunchActivity.this.getPackageName(), null));
                    } else if (Build.VERSION.SDK_INT <= 8) {
                        localIntent.setAction(Intent.ACTION_VIEW);

                        localIntent.setClassName("com.android.settings",
                                "com.android.settings.InstalledAppDetails");

                        localIntent.putExtra("com.android.settings.ApplicationPkgName",
                                LaunchActivity.this.getPackageName());
                    }
                    startActivity(localIntent);
                }
            });

            TextView cancel = view.findViewById(R.id.btn_off);
            cancel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
        }

    }

    void getPermission(PermissionListener permissionListener) {
        String permissions[] = {
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.WAKE_LOCK,
                Manifest.permission.CAMERA,
                Manifest.permission.RECEIVE_BOOT_COMPLETED,
                Manifest.permission.SYSTEM_ALERT_WINDOW,
                Manifest.permission.RECEIVE_BOOT_COMPLETED
        };
        AndPermission.with(this)
                .permission(permissions)
                .callback(permissionListener)
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                        AndPermission.rationaleDialog(LaunchActivity.this, rationale).show();
                    }
                })
                .start();

    }

}
