package com.example.netKit.piece;

import java.util.Date;

/**
 * @author KevinLeak
 * 用在服务端回送数据时，统一所有回送数据都有的参数
 */
public class RspPiece<T> {

    /**
     * status : 1
     * message :
     * date :
     *
     */

    private T result;
    private int status = 0;
    private String message;
    private Date date;


    /**
     * 很多状态码表示的错误，在前端已经拦截
     * */
    public final static int SUCCEED = 1;
    public static final int SUCCESS_STATUS = 1;								// # 操作正确
    public static final int FAILURE_STATUS = 0;								// # 操作失败
    public static final int SAME_USERNAME = 7000;							// # 用户名字已经存在
    public static final int SAME_PHONE = 7001;								// # 用户电话号码已经存在
    public static final int FORMAT_ERROR_FILE = 8005;						// # 传入的file文件有问题
    public static final int ERROR_PASSWORD = 10000;						    // # 密码错误
    public static final int ERROR_REPEAT_LOGIN = 11000;					    // # 重复登入
    public static final int ERROR_NET = 11001;								// # 网络错误
    public static final int FORMAT_ERROR_AVATAR = 8006;						// # 头像数据破损
    public static final int NULL_DATA = 6005;
    public static final int NULL_USER = 6004;                               // 用户不存在
    public static final int EXIST_FRIENDS = 12000;                          // 好友关系已经存在

    public boolean isSuccess() {
        return status == SUCCEED;
    }


    public T getResult() {
        return result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getData() {
        return date;
    }

    public void setData(Date data) {
        this.date = data;
    }

    @Override
    public String toString() {
        return "RspPiece{" +
                "result=" + result.toString() +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", date=" + date +
                '}';
    }

    public void setResult(T result) {
        this.result = result;
    }
}
