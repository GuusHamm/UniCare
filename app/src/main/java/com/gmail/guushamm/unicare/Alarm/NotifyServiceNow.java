package com.gmail.guushamm.unicare.Alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.gmail.guushamm.unicare.MainActivity;
import com.gmail.guushamm.unicare.R;

/**
 * Created by Nekkyou on 23-6-2016.
 */
public class NotifyServiceNow extends Service {
    public class ServiceBinder extends Binder {
        NotifyServiceNow getService() {
            return NotifyServiceNow.this;
        }
    }

    // Unique id to identify the notification.
    private static final int NOTIFICATION = 1234;
    // Name of an intent extra we can use to identify if this service was started to create a notification
    public static final String INTENT_NOTIFY = "com.blundell.tut.service.INTENT_NOTIFYNOW";
    // The system notification manager
    private NotificationManager mNM;

    @Override
    public void onCreate() {
        Log.i("NotifyServiceNow", "onCreate()");
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        // If this service was started by out AlarmTask intent then we want to show our notification
        if(intent.getBooleanExtra(INTENT_NOTIFY, false))
            showNotification();

        // We don't care if this service is stopped as we have already delivered our notification
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // This is the object that receives interactions from clients
    private final IBinder mBinder = new ServiceBinder();

    /**
     * Creates a notification and shows it in the OS drag-down status bar
     */
    private void showNotification() {
        int icon = R.drawable.ic_logo_unicare;
        long time = System.currentTimeMillis();

        Notification.Builder mBuilder = new Notification.Builder(getApplicationContext());
        mBuilder.setSmallIcon(icon);
        mBuilder.setContentTitle("Remember your appointment");
        mBuilder.setContentText("You will be remembered 2 hours before your appointment");
        mBuilder.setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0));
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setLights(Color.BLUE, 100000, 0);
        mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Notification notification = mBuilder.build();

        // Clear the notification when it is pressed
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        // Send the notification to the system.
        mNM.notify(NOTIFICATION, notification);

        // Stop the service when we are finished
        stopSelf();
    }
}