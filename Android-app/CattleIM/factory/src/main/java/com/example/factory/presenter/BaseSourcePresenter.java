package com.example.factory.presenter;

import com.example.common.factory.data.DataSource;
import com.example.common.factory.data.DbDataSource;
import com.example.factory.contract.BaseContract;


import java.util.List;


/**
 * @param <Data> 数据模型
 * @param <ViewModel>
 * @param <Source> 数据源，数据仓库
 * @param <View>
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
}
