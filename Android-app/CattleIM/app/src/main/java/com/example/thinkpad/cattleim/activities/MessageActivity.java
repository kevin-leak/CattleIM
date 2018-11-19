package com.example.thinkpad.cattleim.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.common.widget.ToolbarActivity;
import com.example.thinkpad.cattleim.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageActivity extends ToolbarActivity {


    private static final String USER_ID = "USER_ID";
    @BindView(R.id.ll_message_sender)
    LinearLayout llMessageSender;
    @BindView(R.id.et_message)
    EditText etMessage;
    private String userId;

    public static void show(Context context, String userId) {

        context.startActivity(new Intent(context, MessageActivity.class).putExtra(USER_ID, userId));
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        userId = bundle.getString(USER_ID);
        if (!TextUtils.isEmpty(userId)) {

        }
        return super.initArgs(bundle);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void initWindows() {
        super.initWindows();

        // 让输入法不遮盖输入框 todo 发现bar被顶上去了
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void initWidget() {
        super.initWidget();
        // 设置下边线
    }

    /**
     * @return 返回一个资源文件
     */
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_message;
    }


}
