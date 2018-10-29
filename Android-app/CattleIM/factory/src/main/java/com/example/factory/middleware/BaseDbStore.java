package com.example.factory.middleware;

import com.example.common.factory.data.DbDataSource;
import com.example.netKit.db.BaseDdModel;

import java.util.List;

/**
 *
 * @param <Data> 各类model
 *
 */
public class BaseDbStore<Data extends BaseDdModel<Data>> implements DbDataSource<Data>,
        DbHelper.DataChangeListener  {
    @Override
    public void load(SucceedCallback<List<Data>> callback) {

    }

}
