package com.example.factory;

import android.util.Log;

import com.example.common.utils.CollectionUtil;
import com.example.factory.contract.chat.ConversationCenter;
import com.example.factory.contract.todo.EventCenter;
import com.example.factory.middleware.chat.ConversationDispatch;
import com.example.factory.contract.user.UserCenter;
import com.example.factory.middleware.todo.EventDispatch;
import com.example.factory.middleware.user.UserDispatch;
import com.example.netKit.db.Conversation;
import com.example.netKit.model.ConversationModel;
import com.example.netKit.net.push.ConversationFactory;
import com.example.netKit.net.push.PushClient;
import com.example.netKit.net.push.pieces.ConversationAck;
import com.example.netKit.net.push.pieces.PushPieces;

import java.util.List;


/** fixme
 * 结构设计，
 * 模式：app(View) -- Factory(present) -- netkit(model)
 *  1.将从net获取的数据，表现为app模块可以展示的数据
 *  2.需要将处理，前端最方便调用的接口，并通过这个转化前后的端的消息，是的数据的展示和网络传输更方便
 *  3.需要处理错误反馈，消息加密的操作，语音压缩，图片压缩，从common包中获取对象(common包是实现通用的工具类，做好各个机型的适配)
 *
 *  通过netkit生产app需要的东西，接洽
 */

public class Factory{

    final static String TAG = Factory.class.getName();


    public static UserCenter getUserCenter() {
        return UserDispatch.instance();
    }

    /**
     * @param conversation 信息
     * @param isOpen
     */
    public static void dispatchPush(String conversation, boolean isOpen) {
        Log.e(TAG, "dispatchPush: 1 " + conversation );

        List<ConversationModel> conversationModels = ConversationFactory.decodeConText(conversation, isOpen);

        ConversationModel[] models = CollectionUtil.toArray(conversationModels, ConversationModel.class);
        Factory.getConversationCenter().dispatch(models);



        // todo 对消息进行解码， 弄成一个model
        PushClient.getInstance().sendMessage(new PushPieces<String>("ok"));
    }


    /**
     * 获取一个消息中心的实现类
     *
     * @return 消息中心的规范接口
     */
    public static ConversationCenter getConversationCenter() {
        return ConversationDispatch.instance();
    }


    public static EventCenter getEventCenter() {
        return EventDispatch.instance();
    }





}
