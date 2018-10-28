package com.example.netKit.utils;

import android.support.v7.util.DiffUtil;

public class DiffUiDataCallback<T extends DiffUiDataCallback.UiDataDiffer<T>> extends DiffUtil.Callback{


    @Override
    public int getOldListSize() {
        return 0;
    }

    @Override
    public int getNewListSize() {
        return 0;
    }

    @Override
    public boolean areItemsTheSame(int i, int i1) {
        return false;
    }

    @Override
    public boolean areContentsTheSame(int i, int i1) {
        return false;
    }

    // 进行比较的数据类型
    // 范型的目的，就是你是和一个你这个类型的数据进行比较
    public interface UiDataDiffer<T> {
        // 传递一个旧的数据给你，问你是否和你标示的是同一个数据
        boolean isSame(T old);

        // 你和旧的数据对比，内容是否相同
        boolean isUiContentSame(T old);
    }
}
