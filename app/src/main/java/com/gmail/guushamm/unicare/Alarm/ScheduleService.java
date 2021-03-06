package com.gmail.guushamm.unicare.Alarm;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by Nekkyou on 22-6-2016.
 */
public class ScheduleService extends Service {


    public class ServiceBinder extends Binder {
        ScheduleService getService() {
            return ScheduleService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("ScheduleService", "Received start id " + startId + ": " + intent);

        // We want this service to continue running until it is explicitly stopped, so return sticky.
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // This is the object that receives interactions from clients. See
    private final IBinder mBinder = new ServiceBinder();

    /**
     * Show an alarm for a certain date when the alarm is called it will pop up a notification
     */
    public void setAlarm(Calendar c) {
        AlarmTask task = new AlarmTask(this, c, false);
        task.run();
    }

    public void setAlarmNow() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 1);
        AlarmTask task = new AlarmTask(this, calendar, true);
        task.run();
    }
}
