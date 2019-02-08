package com.example.factory.middleware;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.common.factory.data.DataSource;
import com.example.common.factory.data.DbDataSource;
import com.example.common.tools.CollectionUtil;
import com.example.netKit.db.BaseDdModel;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * 1. 注册绑定DbHelper 里面的反馈通知
 * 2. 建立需要通知的数据类型的扫描器
 * 3. 数据缓存与过滤
 */
public abstract class BaseDbRepository<Data extends BaseDdModel<Data>> implements DbDataSource<Data>,
        DbHelper.DataChangeListener<Data> , QueryTransaction.QueryResultListCallback<Data>{

    final static String TAG = "BaseDbRepository";

    /**
     * 被扫描出的数据类型
     */
    private Class<Data> tClass;

    private SucceedCallback<List<Data>> callback;
    /**
     * 用于缓存的数组， 链表操作
     */
    private List<Data> dataList =  new LinkedList<>();

    public BaseDbRepository() {

        // BaseDbRepository 被继承实例化实际的装载特殊的model类型的时候，进行数据类型扫描
        // 及邮递员在放入物品前，需要对物品的类型进行扫描，再开启箱子

        typeScanner();
    }

    /**
     * 进行数据类型的扫描
     */
    private void typeScanner() {
        // 拿当前类的范型数组信息
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        if (type != null) {
            Type[] actualTypeArguments = type.getActualTypeArguments();
            tClass = (Class<Data>) actualTypeArguments[0];
        }

    }

    /**
     * 提供注册监听器的方法，建立与数据存储的连接
     */
    private void registerDbChangeListener() {
        Log.e(TAG, "registerDbChangeListener: "  );
        DbHelper.addChangeListener(tClass, this);
    }


    /**
     * 邮递员 扫描物件后，对箱子进行了类型标识，把物品放入放入箱子，那一刻使用load
     */
    @Override
    public void load(SucceedCallback<List<Data>> callback) {
        this.callback = callback;
        // 当数据被加载的时候我们设置监听者
        registerDbChangeListener();
    }


    @Override
    public void dispose() {
        // 取消监听，销毁数据
        this.callback = null;
        DbHelper.removeChangeListener(tClass, this);
        dataList.clear();
    }



    @Override
    public void onDataSave(Data[] list) {
        boolean isChanged = false;
        // 当数据库数据变更的操作
        for (Data data : list) {
            // 是关注的人，同时不是我自己
            if (isRequired(data)) {
                insertOrUpdate(data);
                isChanged = true;
            }
        }
        Log.e(TAG, "notifyDataChange: " );
        // 有数据变更，则进行界面刷新
        if (isChanged)
            notifyDataChange();
    }

    // 通知界面刷新的方法
    private void notifyDataChange() {
        Log.e(TAG, "notifyDataChange: true" );
        SucceedCallback<List<Data>> callback = this.callback;
        if (callback != null)
            callback.onDataLoaded(dataList);
    }

    /**
     * 对于不同的数据进行过滤
     * @param data 需要使用的数据
     * @return 是否被需要
     */
    public abstract boolean isRequired(Data data);



    // 插入或者更新
    private void insertOrUpdate(Data data) {
        int index = indexOf(data);
        if (index >= 0) {
            replace(index, data);
        } else {
            insert(data);
        }
    }

    // 更新操作，更新某个坐标下的数据
    protected void replace(int index, Data data) {
        dataList.remove(index);
        dataList.add(index, data);
    }

    // 添加方法
    protected void insert(Data data) {
        dataList.add(data);
    }


    // 查询一个数据是否在当前的缓存数据中，如果在则返回坐标
    protected int indexOf(Data newData) {
        int index = -1;
        for (Data data : dataList) {
            index++;
            if (data.isSame(newData)) {
                return index;
            }
        }
        return -1;
    }

    @Override
    public void onDataDelete(Data[] list) {
        // 在删除情况下不用进行过滤判断
        // 但数据库数据删除的操作
        boolean isChanged = false;
        for (Data data : list) {
            if (dataList.remove(data))
                isChanged = true;
        }

        // 有数据变更，则进行界面刷新
        if (isChanged)
            notifyDataChange();
    }

    /**
     *
     * 基类统一处理，自我查询的信息的更新
     */
    @Override
    public void onListQueryResult(QueryTransaction transaction, @NonNull List<Data> tResult) {
        // 数据库加载数据成功
        if (tResult.size() == 0) {
            dataList.clear();
            notifyDataChange();
            return;
        }

        // 转变为数组
        Data[] users = CollectionUtil.toArray(tResult, tClass);
        // 回到数据集更新的操作中
        onDataSave(users);
    }
}
