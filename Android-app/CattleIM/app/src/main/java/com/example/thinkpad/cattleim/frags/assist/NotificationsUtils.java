package com.example.thinkpad.cattleim.frags.assist;

import android.annotation.SuppressLint;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class NotificationsUtils {
    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

    public NotificationsUtils() {
    }

    @SuppressLint({"NewApi"})
    public static boolean isNotificationEnabled(Context context) {
        @SuppressLint("WrongConstant") AppOpsManager mAppOps = (AppOpsManager)context.getSystemService("appops");
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;
        Class appOpsClass = null;

        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE, String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField("OP_POST_NOTIFICATION");
            int value = (Integer)opPostNotificationValue.get(Integer.class);
            return (Integer)checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == 0;
        } catch (Exception var9) {
            var9.printStackTrace();
            return false;
        }
    }
}
