package com.example.netKit.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * 用来储存数据的信息
 */
@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {
    /**
     * 数据库的名字
     */
    public static final String NAME = "EventDatabase";
    /**
     * 数据库的版本，只允许递增更新数据库
     */
    public static final int VERSION = 1;
}

