package com.example.factory.presenter.todo;

import android.support.v7.util.DiffUtil;
import android.util.Log;

import com.example.factory.Factory;
import com.example.factory.contract.todo.EventContract;
import com.example.factory.contract.todo.EventDataSource;
import com.example.factory.middleware.todo.EventDbRepository;
import com.example.factory.presenter.BaseSourcePresenter;
import com.example.netKit.db.Event;
import com.example.netKit.net.push.ConversationFactory;
import com.example.netKit.net.push.PushClient;
import com.example.netKit.net.push.pieces.ConversationAck;
import com.example.netKit.net.push.pieces.ConversationPieces;
import com.example.netKit.utils.DiffUiDataCallback;

import java.util.List;

import okhttp3.WebSocket;

public class EventPresenter extends BaseSourcePresenter<Event, Event, EventDataSource, EventContract.View>
        implements EventContract.Presenter  {

    public final static String TAG = EventPresenter.class.getName();


    public EventPresenter( EventContract.View view) {

        // 建立一个仓库
        super(new EventDbRepository(), view);
    }




    @Override
    public void onDataLoaded(List<Event> events) {

        EventContract.View view = getView();
        if (view == null)
            return;

        // 差异对比
        List<Event> old = view.getRecyclerAdapter().getItems();
        DiffUiDataCallback<Event> callback = new DiffUiDataCallback<>(old, events);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        // 刷新界面
        refreshData(result, events);

    }

    @Override
    public void sendReadEvent(final Event event) {
        final ConversationAck pieces = ConversationFactory.buildModel(event);
        Log.e(TAG, "buildModel: " + pieces.toString() );
        PushClient.getInstance().sendMessage(pieces, new PushClient.MessageListener() {
            @Override
            public void onFailure(WebSocket webSocket) {
                // 不做处理
            }

            @Override
            public void onSuccess() {

                event.setUnReadCount(0);

                Factory.getEventCenter().dispatch(event);
            }
        });
    }
}
