package com.example.factory.presenter.chat;

import android.support.v7.util.DiffUtil;
import android.util.Log;

import com.example.factory.Factory;
import com.example.factory.contract.chat.ChatContract;
import com.example.factory.contract.chat.ConversationDataSource;
import com.example.factory.presenter.BaseSourcePresenter;
import com.example.netKit.db.Conversation;
import com.example.netKit.db.Event;
import com.example.netKit.net.push.ConversationFactory;
import com.example.netKit.net.push.PushClient;
import com.example.netKit.net.push.pieces.ConversationAck;
import com.example.netKit.net.push.pieces.ConversationPieces;
import com.example.netKit.persistence.Account;
import com.example.netKit.utils.DiffUiDataCallback;

import java.util.List;

import okhttp3.WebSocket;

public class ChatPresenter<View extends ChatContract.View>
        extends BaseSourcePresenter<Conversation, Conversation, ConversationDataSource, View>
        implements ChatContract.Presenter {

    public final static String TAG = ChatPresenter.class.getName();

    private String receiverId;
    private int receiverType;

    public ChatPresenter(ConversationDataSource source, View view,
                         String receiverId, int receiverType) {
        super(source, view);
        this.receiverId = receiverId;
        this.receiverType = receiverType;
    }

    @Override
    public void onDataLoaded(List<Conversation> conversations) {
        ChatContract.View view = getView();
        if (view == null)
            return;

        // 拿到老数据
        @SuppressWarnings("unchecked")
        List<Conversation> old = view.getRecyclerAdapter().getItems();

        // 差异计算
        DiffUiDataCallback<Conversation> callback = new DiffUiDataCallback<>(old, conversations);
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        // 进行界面刷新
        refreshData(result, conversations);
    }

    @Override
    public void pushText(String content) {
        // todo 构建一个新的消息


        ConversationPieces pieces = new ConversationFactory.Builder()
                .setReceiver(receiverId, receiverType)
                .addContent(content, Conversation.CATEGORY_TEXT).build();

        Log.e(TAG, "pushText: push " + pieces.toString());
        // todo 进行网络发送
        ConversationHelper.push(pieces);
    }

    @Override
    public void pushAudio(String path, long time) {

    }

    @Override
    public void pushImages(String[] paths) {

    }

    @Override
    public boolean rePush(Conversation conversation) {
        Log.e(TAG, "rePush: " + conversation.toString() );
        // 确定消息是可重复发送的
        if (Account.getUserId().equalsIgnoreCase(conversation.getSend())
                && !conversation.isDone()) {
            Log.e(TAG, "rePush: " + " 重新加载" );
            ConversationPieces pieces = ConversationFactory.buildModel(conversation);
            ConversationHelper.push(pieces);
            return true;
        }
        return false;
    }

    @Override
    public void sendReadEvent(ConversationAck ack, final Event event) {
        PushClient.getInstance().sendMessage(ack, new PushClient.MessageListener() {
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
