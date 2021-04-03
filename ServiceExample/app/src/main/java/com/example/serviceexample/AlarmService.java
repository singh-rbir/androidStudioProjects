package com.example.serviceexample;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.nio.charset.MalformedInputException;
import java.util.Calendar;
import java.util.Locale;

public class AlarmService extends Service {

    public static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final int NOTIFICATION_ID = 6;

    public NotificationManager mNotifyManager;
    public AlarmManager alarmMgr;
    public static int code = 30;
    public long startTimee;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("asdfasdf", "---service onCreate");
        Toast.makeText(this, "service created (onCreate)", Toast.LENGTH_SHORT).show();

        alarmMgr = (AlarmManager)getSystemService(ALARM_SERVICE);
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        createNotificationChannel();
        code++;

        startForeground(NOTIFICATION_ID, getNotificationBuilder().build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("asdfasdf", "service onStartCommand");
        Toast.makeText(this, "service starting (onStartCommand)", Toast.LENGTH_SHORT).show();

        /*
        final WindowManager mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        //final LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);

        WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
                PixelFormat.TRANSLUCENT
        );
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        mLayoutParams.gravity = Gravity.TOP | Gravity.START;
        mLayoutParams.x = 0;
        mLayoutParams.y = 0;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_FULLSCREEN |
                //WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                //WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON |
                //WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                WindowManager.LayoutParams.FLAG_DIM_BEHIND |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON|
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD;

        final View view = LayoutInflater.from(this).inflate(R.layout.activity_f_s_alarm, null);
        //final View view = inflater.inflate(R.layout.activity_f_s_alarm, null);
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWindowManager.removeView(view);
            }
        });
        mWindowManager.addView(view, mLayoutParams);

       */


        //mNotifyManager.notify(0, getNotificationBuilder().build());

//        String time = "";
//        if(intent!=null)
//            time = intent.getStringExtra(MainActivity.EXTRA_TIME);
//        String[] tokens = time.split(":", -3);
//        int hours = Integer.parseInt(tokens[0]); // calculates hours from time strings tokens
//        int mins = Integer.parseInt(tokens[1]); // calculates minutes
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, hours);
//        calendar.set(Calendar.MINUTE, mins);
//        calendar.set(Calendar.SECOND, 0);
//
//        long startTime = calendar.getTimeInMillis();


        Intent alarmIntent = new Intent(this, FSAlarmActivity.class);
        if (intent != null)
            alarmIntent.putExtras(intent);
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        getApplicationContext().startActivity(alarmIntent);


//        PendingIntent pi = PendingIntent.getActivity(this, code + 1, alarmIntent, 0);
//        Log.d("asdf", "----right before setting alarm in service: " + startTime);
//        //alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, startTime, AlarmManager.INTERVAL_DAY, pi);
//        alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, startTime, pi);

        //startForeground(startId, getNotificationBuilder().build());
        //mNotifyManager.notify(0, getNotificationBuilder().build());


        return START_STICKY;
    }



    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done (onDestroy)", Toast.LENGTH_SHORT).show();

        Log.d("asdf", "---service stopped");
        stopSelf();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    public NotificationCompat.Builder getNotificationBuilder(){
        Intent notifIntent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, code, notifIntent, 0);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext(), PRIMARY_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Incoming Alarm")
                        .setContentText("Test alarm")
                        .setContentIntent(pi)
                        .setVisibility(NotificationCompat.VISIBILITY_SECRET)
                        //.setCategory(NotificationCompat.CATEGORY_SERVICE)
                        .setAutoCancel(false)
                        .setPriority(NotificationCompat.PRIORITY_MIN)
                        .setChannelId(PRIMARY_CHANNEL_ID);
                        //.setDefaults(NotificationCompat.DEFAULT_ALL);


        return builder;
    }

    public void createNotificationChannel(){
            mNotifyManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                        "Alarm service notification", NotificationManager.IMPORTANCE_MIN);
                notificationChannel.enableVibration(true);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.setDescription("Notifications from Alarm Service");

                mNotifyManager.createNotificationChannel(notificationChannel);

            }
    }


}
