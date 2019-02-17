package com.example.common.utils;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;

/**
 * @author KevinLeak
 * 一个drawable 颜色转化器
 */
public class DrawChangeColor {

    /**
     * @param drawable 获取的draw 资源韩文件
     * @param colors 要设置的字符串， 如：#eeeee
     * @return
     */
    public static Drawable tintDrawable(Drawable drawable, String colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, Color.parseColor(colors));
        return wrappedDrawable;
    }

    /**
     * Drawable 颜色转化类
     *
     * @param drawable 源Drawable
     * @param colors
     * @return 改变颜色后的Drawable
     */
    public static Drawable tintListDrawable(@NonNull Drawable drawable, ColorStateList colors) {
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }


}
