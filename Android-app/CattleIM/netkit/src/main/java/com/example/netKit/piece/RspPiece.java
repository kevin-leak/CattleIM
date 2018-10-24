package com.example.netKit.piece;

/**
 * @author KevinLeak
 * 用在服务端回送数据时，统一所有回送数据都有的参数
 */
public class RspPiece<T> {


    /**
     * status : 0
     * result :
     * code : 0
     * message :
     */

    private int status;
    private T result;
    private String message;
    private final int SUCCEED = 1;

    public boolean success() {
        return status == SUCCEED;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
