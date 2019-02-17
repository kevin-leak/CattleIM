package com.example.netKit.db;

import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

@Table(database = AppDatabase.class)
public class TagMember extends BaseDdModel<TagMember>{

    // 主键
    @PrimaryKey
    private String id;

    @Override
    public boolean isSame(TagMember old) {
        return false;
    }

    @Override
    public boolean isUiContentSame(TagMember old) {
        return false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
