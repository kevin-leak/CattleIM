package com.example.factory.middleware;

import com.example.common.factory.data.DataSource;
import com.example.netKit.db.User;

/**
 * 建立对增删查改处理与监听，并反馈回给Dbdatabase，可有结构图分析
 * @author KevinLeak
 */
public class DbHelper {


    public static void save(Class<User> userClass, User user) {

    }

    interface DataChangeListener{

    }
}
