package com.example.common.factory.data;


import java.util.List;

/**
 * 本地数据库管控接口，针对于不同的数据进行不同的管控，扩展
 *
 * 用来处理BaseDbRepository 的建立，用来回调数据，用来扩展数据在数据库中回调成功的方法
 */
public interface DbDataSource<Data> extends DataSource {

    /**
     * 一个基本的数据源加载方法
     *
     * @param callback 传递一个callback回调，一般回调到Presenter
     */
    void load(SucceedCallback<List<Data>> callback);



}

