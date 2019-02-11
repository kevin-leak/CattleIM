package com.example.netKit.net.push;

import com.example.netKit.net.push.contract.MessageContract;
import com.example.netKit.net.push.pieces.MessagePieces;
import com.example.netKit.net.push.pieces.PushPieces;

import java.util.List;

public class MessageFactory {

    public static String build(PushPieces pieces) {
        return pieces.toJson();
    }

    public static String build(MessagePieces pieces) {
        PushPieces<MessagePieces> pushPieces = new PushPieces<>(pieces);
        return pushPieces.toJson();
    }

    public static String build(List<MessagePieces> piecesList) {
        PushPieces<List<MessagePieces>> pushPieces = new PushPieces<>(piecesList);
        return pushPieces.toJson();
    }

}
