package com.example.common.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class MessageNotificationUtils extends ContextWrapper {

    private int notifyId = 1;
    private String channelId = "1";
    private int smallIcon;
    private String title;
    private String content;
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private NotificationManagerCompat notificationManagerCompat;

    public MessageNotificationUtils(Context context, int smallIcon, String title, String content) {
        this(context, 1, null, smallIcon, title, content);
    }

    public MessageNotificationUtils(Context context, int notifyId, int smallIcon, String title, String content) {
        this(context, notifyId, null, smallIcon, title, content);
    }

    public MessageNotificationUtils(Context context, int notifyId, String channelId, int smallIcon, String title, String content) {
        super(context);
        this.notifyId = notifyId;
        this.channelId = channelId != null ? channelId : this.notifyId+"";
        this.smallIcon = smallIcon;
        this.title = title;
        this.content = content;
        baseNotification();
    }

    public void notifyProgress(int max, int progress, String title, String content) {
        if (builder != null && progress > 0) {
            builder.setContentTitle(title);
            builder.setContentText(content);
            builder.setProgress(max, progress, false);
            notify();
        }
    }

    public void completeProgress(String title, String content) {
        notifyProgress(0, 0, title, content);
    }

    public void notifyed() {
        notify(builder);
    }

    public void notify(Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        notify(builder.setContentIntent(pendingIntent));
    }

    private void notify(NotificationCompat.Builder builder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getNotificationManager();
            notificationManager.notify(notifyId, builder.build());
        } else {
            getNotificationManagerCompat();
            notificationManagerCompat.notify(notifyId, builder.build());
        }
    }

    public void cancel(int notifyId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.cancel(notifyId);
        } else {
            notificationManagerCompat.cancel(notifyId);
        }
    }


    private void baseNotification() {
        builder = getBuilder(getApplicationContext(), channelId);
        builder.setSmallIcon(smallIcon);
        builder.setContentTitle(title);
        builder.setContentText(content);
    }

    private NotificationCompat.Builder getBuilder(Context context, String channelId) {
        return (builder = new NotificationCompat.Builder(context, channelId));
    }

    private NotificationCompat.Builder getBuilder(Context context) {
        return (builder = new NotificationCompat.Builder(context));
    }

    private void getNotificationManagerCompat() {
        notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getNotificationManager() {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "channel_name", importance);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public NotificationManager getManager() {
        return this.notificationManager;
    }

    public NotificationCompat.Builder getBuilder() {
        return builder;
    }
}
