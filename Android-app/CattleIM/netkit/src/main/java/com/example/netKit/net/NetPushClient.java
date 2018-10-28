package com.example.netKit.net;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * 用websocket建立一个长连接来实现实时通信
 */
public class NetPushClient extends WebSocketClient {

    public NetPushClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onMessage(String message) {

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onError(Exception ex) {

    }
}
