package com.example.netKit.db;

import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

@Table(database = AppDatabase.class)
public class GroupMember extends BaseDdModel<GroupMember>{


    // 主键
    @PrimaryKey
    private String id;

    @Override
    public boolean isSame(GroupMember old) {
        return false;
    }

    @Override
    public boolean isUiContentSame(GroupMember old) {
        return false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
