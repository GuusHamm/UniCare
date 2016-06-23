package com.gmail.guushamm.unicare.Alarm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Nekkyou on 22-6-2016.
 */
public class ScheduleClient {
    private Context context;
    private  ScheduleService mBoundService;
    private boolean mIsBound;

    public ScheduleClient(Context context) {
        this.context = context;
    }

    public void doBindService() {
        context.bindService(new Intent(context, ScheduleService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with our service has been established,
            // giving us the service object we can use to interact with our service.
            mBoundService = ((ScheduleService.ServiceBinder) service).getService();

            Calendar c = Calendar.getInstance();
            c.add(Calendar.SECOND, 10);
            mBoundService.setAlarm(c);
        }

        public void onServiceDisconnected(ComponentName className) {
            mBoundService = null;
        }
    };

    public void setAlarmForNotification(Calendar calendar) {
        if (mBoundService == null) {
            Toast.makeText(context, "Bound service not set", Toast.LENGTH_SHORT).show();
            doBindService();
            if (mBoundService == null) {
                Toast.makeText(context, "Bound service still not set", Toast.LENGTH_SHORT).show();
            }
        } else {
            mBoundService.setAlarm(calendar);
        }
    }
}
