package com.example.common;


/*
* fixme 处理各种常见的问题， 比如说UI， 在每个app都使用的基础， 如闹钟，富文本编辑，处理语音压缩，图片压缩,手环的连接以及其他
* fixme common包是实现通用的工具类，做好各个机型的适配
* */

/**
 * 设置些不变的常量
 */
public class Common {

    public interface Constance{
        String HTTP_HEAD = "http://";
        String WEB_SOCKET_HEAD = "ws://";
        String BASE_IP_PORT = "192.168.136.104:8000/";
        String BASE_PHONE_UTL = HTTP_HEAD + BASE_IP_PORT + "media/";
        String BASE_URL = HTTP_HEAD + BASE_IP_PORT + "android/";
        String WEB_VIEW_URL = BASE_URL + "web_view/";
        String WEB_SOCKET = WEB_SOCKET_HEAD + BASE_IP_PORT +"android/websocket/";
    }
}
