package com.gmail.guushamm.unicare;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Nekkyou on 16-6-2016.
 */
public class NotificationController {
    private MainActivity context;
    private Notification.Builder mBuilder;
    private NotificationManager mNotifyMgr;
    private int mNotificationId;

    public NotificationController(MainActivity context) {
        this.context = context;
        mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationId = 001;
    }

    public void createNotification() {
        mBuilder = new Notification.Builder(context);
        mBuilder.setSmallIcon(R.drawable.ic_menu_slideshow);
        mBuilder.setContentTitle("Remember your appointment");
        mBuilder.setContentText("You have an appointment in 2 hours");
        mBuilder.setContentIntent(getPendingIntent());

        mNotifyMgr.notify(mNotificationId, mBuilder.build());
        mNotificationId++;
    }

    private PendingIntent getPendingIntent() {
        Intent resultIntent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
