package com.example.factory.presenter;

import com.example.common.factory.data.DataSource;
import com.example.common.factory.data.DbDataSource;
import com.example.factory.contract.BaseContract;


import java.util.List;


/**
 * @param <Data> 关注的数据，数据库中的
 * @param <ViewModel> 页面展示要用的model， 数据库中经过处理的数据模型
 * @param <Source> 数据来源
 * @param <View> 我们需要控制的view
 */
public abstract class BaseSourcePresenter<Data, ViewModel,
        Source extends DbDataSource<Data>,
        View extends BaseContract.RecyclerView>
        extends BaseRecyclerPresenter<ViewModel, View>
        implements DataSource.SucceedCallback<List<Data>> {

    protected Source mSource;

    public BaseSourcePresenter(Source source, View view) {
        super(view);
        this.mSource = source;
    }

    @Override
    public void start() {
        super.start();
        if (mSource != null)
            mSource.load(this);
    }

    @Override
    public void destroy() {
        super.destroy();
        mSource.dispose();
        mSource = null;
    }

    @Override
    public void onDataLoaded(List<Data> data) {

    }
}
