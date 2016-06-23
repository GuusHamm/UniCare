package com.gmail.guushamm.unicare;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;

import java.util.Calendar;

/**
 * Created by Nekkyou on 16-6-2016.
 */
public class NotificationController {
    private MainActivity context;
    private Notification.Builder mBuilder;
    private NotificationManager mNotifyMgr;
    private int mNotificationId;
    private AlarmManager alarmManager;

    public NotificationController(MainActivity context) {
        this.context = context;
        mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        mNotificationId = 001;
    }

    public void createNotification() {
        setupBuilder();
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
        mNotificationId++;
    }

    private PendingIntent getPendingIntent() {
        Intent resultIntent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void setupBuilder() {
        mBuilder = new Notification.Builder(context);
        mBuilder.setSmallIcon(R.drawable.ic_logo_unicare);
        mBuilder.setContentTitle("Remember your appointment");
        mBuilder.setContentText("You have an appointment in 2 hours");
        mBuilder.setContentIntent(getPendingIntent());
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setLights(Color.BLUE, 100000, 0);
        mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
    }

    public void startAlarm(){


//        PendingIntent mAlarmSender = PendingIntent.getBroadcast(context, 1927, new Intent(context, AlarmReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent mAlarmSender = PendingIntent.getActivity(context, 1234, new Intent(context, AlarmReceiver.class), 0);
        //Set the alarm to 10 seconds from now
        Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND, 10);
        long firstTime = c.getTimeInMillis();
        // Schedule the alarm!
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        am.set(AlarmManager.RTC_WAKEUP, firstTime, mAlarmSender);
    }
}
