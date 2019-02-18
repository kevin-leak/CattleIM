package com.example.factory.contract.todo;


import com.example.netKit.db.TimeLine;

public interface TimeLineCenter {
    // 分发处理一堆用户卡片的信息，并更新到数据库
    void dispatch(TimeLine... model);
}
