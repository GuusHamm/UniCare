package com.gmail.guushamm.unicare;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;

/**
 * Created by Nekkyou on 17-6-2016.
 */
public class AlarmReceiver extends BroadcastReceiver {
    private Notification.Builder mBuilder;
    private Context context;
    private NotificationManager mNotifyMgr;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        setupBuilder();

        mNotifyMgr.notify(001, mBuilder.build());
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

    private PendingIntent getPendingIntent() {
        Intent resultIntent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
