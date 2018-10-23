package com.example.common;


import retrofit2.http.Url;


/*
* fixme 处理各种常见的问题， 比如说UI， 在每个app都使用的基础， 如闹钟，富文本编辑，处理语音压缩，图片压缩,手环的连接以及其他
* fixme common包是实现通用的工具类，做好各个机型的适配
* */

/**
 * 设置些不变的常量
 */
public class Common {

    public interface Constance{
        String BASE_URL = "http://192.168.136.105:8000/android/";
    }
}
