package com.example.factory;

import android.support.v7.app.AppCompatActivity;

import com.example.factory.middleware.user.UserCenter;
import com.example.factory.middleware.user.UserDispatch;


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

    public static UserCenter getInstance() {
        return UserDispatch.instance();
    }
}
