package com.example.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.bumptech.glide.RequestManager;
import com.example.common.Common;
import com.example.common.R;
import com.example.common.factory.profile.Profile;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class AvatarView extends CircleImageView {
    public AvatarView(Context context) {
        super(context);
    }

    public AvatarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AvatarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setup(RequestManager manager, Profile author) {
        if (author == null)
            return;
        // 进行显示
        setup(manager, author.getAvatar());
    }


    public void setup(RequestManager manager, String url) {
        if (url.contains("media")){
            url = url.replace("media/", "");
        }
        url = Common.Constance.BASE_PHONE_UTL + url;
        Log.e(TAG, "setup: " + url);
        setup(manager, R.mipmap.default_avatar, url);
    }


    public void setup(RequestManager manager, int resourceId, String url) {
        if (url == null)
            url = "";
        manager.load(url)
                .placeholder(resourceId)
                .centerCrop()
                .dontAnimate() // CircleImageView 控件中不能使用渐变动画，会导致显示延迟
                .into(this);

    }
}
