package com.example.netKit.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.netKit.NetKit;
import com.example.netKit.model.UserModel;

/**
 * 设置一个用户的账号管理类
 * 1. 处理登录状态持久化问题
 * 2. 处理推送的ID绑定问题 todo 后期处理
 */
public class Account {

    private static String account;
    private static String pushId;
    private static boolean isBind;

    public static String getUserId() {
        return userId;
    }

    private static String userId;
    private static String xCsrfToken;
    private static String Cookie;
    private static String sessionId;
    private final static String X_CSRF_token = "X-CSRFtoken";
    private final static String COOKIE = "COOKIE";
    private final static String SEESION_ID = "SEESION_ID";
    private final static String ACCOUNT_KEY = "ACCOUNT_KEY";
    private final static String PUSH_ID = "PUSH_ID";
    private final static String USER_ID = "USER_ID";
    private final static String IS_BIND = "IS_BIND";

    /**
     * @param accountPiece 服务器返回的piece
     * 用处理登入账户的状态的持久化处理
     */
    public static void login(UserModel accountPiece) {
        Account.account = accountPiece.getAccount();
        Account.userId = accountPiece.getUser().getId();

        save(NetKit.app());
    }

    /**
     * 持久化处理
     */
    public static void save(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Account.class.getName(), Context.MODE_PRIVATE);
        sp.edit()
                .putString(ACCOUNT_KEY, account)
                .putString(PUSH_ID, pushId)
                .putString(USER_ID, userId)
                .putBoolean(IS_BIND, isBind)
                .putString(SEESION_ID, sessionId)
                .apply();
    }

    public static void init(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Account.class.getName(), Context.MODE_PRIVATE);
        Account.userId = sp.getString(USER_ID, "");
        Account.account = sp.getString(ACCOUNT_KEY, "");
        Account.pushId = sp.getString(PUSH_ID, "");
        Account.isBind = sp.getBoolean(IS_BIND, false);
        Account.sessionId = sp.getString(SEESION_ID, "");
    }

    /**
     * 判断是否登入
     */
    public static boolean isLogin(){
        return !TextUtils.isEmpty(Account.userId);
    }

    public static String getAccount() {
        return account;
    }

    public static void setIsBind(boolean isBind) {
        Account.isBind = isBind;
    }

    public static boolean isIsBind() {
        return isBind;
    }

    public static String getPushId() {
        return pushId;
    }

    public static void setPushId(String pushId) {
        Account.pushId = pushId;
//        进行一个持久化
        Account.save(NetKit.app());
    }

    public static String getSession_id() {
        return sessionId;
    }

    public static void setSession_id(String session_id) {
        Account.sessionId = session_id;
        //        进行一个持久化
        Account.save(NetKit.app());

    }

    public static String getCookie() {
        return Cookie;
    }

    public static void setCookie(String cookie) {
        Account.Cookie = cookie;
    }

    public static String getxCsrfToken() {
        return xCsrfToken;
    }

    public static void setxCsrfToken(String xCsrfToken) {
        Account.xCsrfToken = xCsrfToken;
    }
}
