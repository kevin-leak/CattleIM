package com.example.factory.presenter;

import android.arch.lifecycle.ViewModel;
import android.support.v7.util.DiffUtil;
import android.util.Log;

import com.example.common.app.Application;
import com.example.common.widget.recycler.RecyclerAdapter;
import com.example.factory.contract.BaseContract;

import java.util.List;

/**
 * 对具有recycleview的view 再一次封装，添加两个刷新数据的功能方法
 */
class BaseRecyclerPresenter<ViewMode, View extends BaseContract.RecyclerView>
        extends BasePresenter<View> {

    final static String TAG = "BaseRecyclerPresenter";

    public BaseRecyclerPresenter(View view) {
        super(view);
    }

    /**
     * 刷新一堆新数据到界面中
     *
     * @param dataList 新数据
     */
    protected void refreshData(final List<ViewMode> dataList) {
        getView().getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View view = getView();
                if (view == null)
                    return;

                // 基本的更新数据并刷新界面
                RecyclerAdapter<ViewMode> adapter = view.getRecyclerAdapter();
                adapter.replace(dataList);
                view.onAdapterDataChanged();
            }
        });
    }

    /**
     * 刷新界面操作，该操作可以保证执行方法在主线程进行
     *
     * @param diffResult 一个差异的结果集
     * @param dataList   具体的新数据
     */
    protected void refreshData(final DiffUtil.DiffResult diffResult, final List<ViewMode> dataList) {
        getView().getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 这里是主线程运行时
                refreshDataOnUiThread(diffResult, dataList);
            }
        });
    }


    private void refreshDataOnUiThread(final DiffUtil.DiffResult diffResult, final List<ViewMode> dataList) {
        View view = getView();
        if (view == null)
            return;
        // 基本的更新数据并刷新界面
        RecyclerAdapter<ViewMode> adapter = view.getRecyclerAdapter();


        // 改变数据集合并不通知界面刷新
        adapter.getItems().clear();
        adapter.getItems().addAll(dataList);

        adapter.notifyDataSetChanged();
        // 通知界面刷新占位布局
//        view.onAdapterDataChanged();

//         进行增量更新
        diffResult.dispatchUpdatesTo(adapter);

    }


}
