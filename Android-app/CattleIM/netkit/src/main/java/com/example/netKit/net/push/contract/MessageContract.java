package com.example.netKit.net.push.contract;

public interface MessageContract {

//    type
//
//    {
//        "0":"系统消息",
//            "1":"普通消息",
//            "2":"请求消息",
//            "3":"主题消息",
//            "4":"公告消息",
//            "5":"任务消息",
//            "6":"关联消息"
//    }
//
//    category
//
//    {
//        "0":"文本",
//            "1":"图片",
//            "2":"语音",
//            "3":"文件"
//    }


    interface Constant{
        public static final String  CATEGROY_TXT = "0";
        public static final String  CATEGROY_PIC = "1";
        public static final String  CATEGROY_SOUND = "2";
        public static final String  CATEGROY_FILE = "3";


        public static final String  TYPE_SYSTEM = "0";
        public static final String  SIMPLE_FILE = "1";
        public static final String  REQUEST_FILE = "2";
        public static final String  TOPIC_FILE = "3";
        public static final String  TIP_FILE = "4";
        public static final String  TASK_FILE = "5";
        public static final String  RELATE_FILE = "6";
    }



}
