package com.example.factory;

/**
 * 结构设计，
 * factory 需要承担的任务，
 * 1. 与后端的连接
 * 2. 与Android前端数据的接洽
 * 3. 处理后端数据的反馈，与前端接洽
 * net、present、model以及前端app 之间的处理：
 * ----->net、present、model 处理与服务器之间的数据传输
 * ----->app、present、model 处理数据从前端的传输
 * -----> 需要做: 两者之间的解耦，数据的持久化，对于后端的数据的分发以及回送，对于前端数据的包装以及回送
 * -----> 处理即时通信的及时化
 */
public class Factory {

}
