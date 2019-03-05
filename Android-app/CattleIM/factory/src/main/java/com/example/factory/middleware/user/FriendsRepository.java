package com.example.factory.middleware.user;

import com.example.factory.contract.user.ContactDataSource;
import com.example.factory.middleware.BaseDbRepository;
import com.example.netKit.db.User;
import com.example.netKit.db.User_Table;
import com.example.netKit.persistence.Account;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

/**
 * 数据的仓库
 */
public class FriendsRepository extends BaseDbRepository<User> implements ContactDataSource {


    @Override
    public void load(SucceedCallback<List<User>> callback) {
        super.load(callback);

        // 加载本地数据库数据, 并进行回调，这里我们统一在基类进行处理
        SQLite.select()
                .from(User.class)
                .where(User_Table.isFriend.eq(true))
                .and(User_Table.id.notEq(Account.getUserId()))
                .orderBy(User_Table.username, true)
                .limit(100)
                .async()
                .queryListResultCallback(this)
                .execute();
    }

    /**
     * 对于通知更新的数据，我们进行不是好友，以及本身的过滤
     */
    @Override
    public boolean isRequired(User user) {
        // fixme 如果APP  没有卸载，导致数据依旧存在，当用户再次注册的时候，会加载到其他用户的数据
        return user.isFriend() && !Account.getUserId().equals(user.getId());
    }
}
