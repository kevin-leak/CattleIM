package com.example.netKit.db;

import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.io.Serializable;

@Table(database = AppDatabase.class)
public class Group extends BaseDdModel<User> implements Serializable {

    // 主键
    @PrimaryKey
    private String id;

    @Override
    public boolean isSame(User old) {
        return false;
    }

    @Override
    public boolean isUiContentSame(User old) {
        return false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
