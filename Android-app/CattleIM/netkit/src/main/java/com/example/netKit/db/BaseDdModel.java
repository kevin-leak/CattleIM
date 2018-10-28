package com.example.netKit.db;

import com.example.netKit.utils.DiffUiDataCallback;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * 基础DBflow的数据模型， 同时实现对各种表的分发，采用泛型
 */
public class BaseDdModel<Model> extends BaseModel implements DiffUiDataCallback.UiDataDiffer<Model>{
    @Override
    public boolean isSame(Model old) {
        return false;
    }

    @Override
    public boolean isUiContentSame(Model old) {
        return false;
    }
}
