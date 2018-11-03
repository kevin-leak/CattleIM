package com.example.netKit.db;

import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;

/**
 * 更新db, k开发期间不涉及数据，就直接删库。。。
 */
//@Migration(version = AppDatabase.VERSION, database = AppDatabase.class)//=2的升级
public  class Migration1 extends AlterTableMigration<User> {
    public Migration1(Class<User> table) {
        super(table);
    }

    @Override
    public void onPreMigrate() {
        addColumn(SQLiteType.TEXT, "phone");
        addColumn(SQLiteType.TEXT, "desc");
        addColumn(SQLiteType.TEXT, "alias");
        addColumn(SQLiteType.INTEGER, "sex");
        addColumn(SQLiteType.INTEGER, "friends");
        addColumn(SQLiteType.INTEGER, "isFriend");
        addColumn(SQLiteType.BLOB, "modifyAt");

    }
}