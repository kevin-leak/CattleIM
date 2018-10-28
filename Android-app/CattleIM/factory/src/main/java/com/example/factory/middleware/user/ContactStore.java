package com.example.factory.middleware.user;

import com.example.factory.middleware.BaseDbStore;
import com.example.netKit.db.BaseDdModel;
import com.example.netKit.db.User;

/**
 * 数据的仓库
 */
public class ContactStore extends BaseDbStore<User> implements ContactDataSource{

}
