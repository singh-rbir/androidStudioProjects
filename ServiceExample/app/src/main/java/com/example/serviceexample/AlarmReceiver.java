package com.example.serviceexample;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final int NOTIFICATION_ID = 0;

    private static final String ACTION_CUSTOM_BROADCAST = "ACTION_CUSTOM_BROADCAST_ALARM";
    public NotificationManager mNotifyManager;

    @Override
    public void onReceive(Context context, Intent intent){
        Log.d("asdfasdf", "Reached BroadcastReceiver(alarmReceiver)");
        Toast.makeText(context, "this is broadcast toast", Toast.LENGTH_LONG).show();

        //createNotificationChannel(context);
        //mNotifyManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        //mNotifyManager.notify(NOTIFICATION_ID, getNotificationBuilder(context).build());

        //if(intent.getAction().equals(ACTION_CUSTOM_BROADCAST)){
            Intent serviceIntent = new Intent(context, AlarmService.class);
            if(intent != null)
                serviceIntent.putExtras(intent);
            serviceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            //context.stopService(serviceIntent);
            context.startService(serviceIntent);
        //}


//        serviceIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);


//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            context.startForegroundService(serviceIntent);
//        }else
//            context.startService(serviceIntent);



//        NotificationCompat.Builder notificationBuilder =
//                new NotificationCompat.Builder(context, "asdf")
//                        .setSmallIcon(R.drawable.ic_mic_icon)
//                        .setContentTitle("Incoming call")
//                        .setContentText("(919) 555-1234")
//                        .setPriority(NotificationCompat.PRIORITY_HIGH)
//                        .setCategory(NotificationCompat.CATEGORY_CALL)
//
//                        // Use a full-screen intent only for the highest-priority alerts where you
//                        // have an associated activity that you would like to launch after the user
//                        // interacts with the notification. Also, if your app targets Android 10
//                        // or higher, you need to request the USE_FULL_SCREEN_INTENT permission in
//                        // order for the platform to invoke this notification.
//                        .setFullScreenIntent(fullScreenPendingIntent, true);
//
//        Notification incomingAlarmNotification = notificationBuilder.build();


        Log.d("asdfasdf", "Reached end of alarmReceiver ");
    }

    public NotificationCompat.Builder getNotificationBuilder(Context context){
        Intent notifIntent = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, NOTIFICATION_ID, notifIntent, 0);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Incoming Alarm")
                        .setContentText("Test alarm")
                        .setContentIntent(pi)
                        .setVisibility(NotificationCompat.VISIBILITY_SECRET)
                        //.setCategory(NotificationCompat.CATEGORY_SERVICE)
                        .setAutoCancel(false)
                        .setPriority(NotificationCompat.PRIORITY_LOW)
                        .setChannelId(PRIMARY_CHANNEL_ID);
        //.setDefaults(NotificationCompat.DEFAULT_ALL);


        return builder;
    }

    public void createNotificationChannel(Context context){
        mNotifyManager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);

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
