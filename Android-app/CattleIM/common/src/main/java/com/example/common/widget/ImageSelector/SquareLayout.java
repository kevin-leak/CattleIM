package com.example.common.widget.ImageSelector;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 给图片选择器设置成正方形，方便显示，运用在图片选择器的item中
 */
public class SquareLayout extends FrameLayout {

    public SquareLayout(@NonNull Context context) {
        super(context);
    }

    public SquareLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 给父类传递的测量值都为宽度，
        // 那么就是基于宽度的正方形控件了
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
