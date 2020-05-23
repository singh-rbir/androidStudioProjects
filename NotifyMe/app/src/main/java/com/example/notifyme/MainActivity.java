package com.example.notifyme;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    /*
    UNIQUE NOTIFICATION CHANNEL ID'S, You use this channel ID later, to post your notifications.
    */
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final int NOTIFICATION_ID = 0;

    /* member variables */
    private Button button_notify;
    private Button button_update;
    private Button button_cancel;

    // The NotificationManager Object helps posts the notification to the user
    private NotificationManager mNotifyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_notify = findViewById(R.id.notify);
        button_update = findViewById(R.id.update);
        button_cancel = findViewById(R.id.cancel);

        button_notify.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                sendNotification();
            }
        });
        button_update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                updateNotification();
            }
        });
        button_update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                cancelNotification();
            }
        });

        createNotificationChannel();

        setNotificationButtonState(true, true ,true);
    }

    private void sendNotification(){
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());

        setNotificationButtonState(false, true ,true);
    }

    private void updateNotification(){
        //Bitmap androidImage = BitmapFactory.decodeResource(getResources(), getDrawable());
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        notifyBuilder.setStyle(new NotificationCompat.BigTextStyle()
                        .setBigContentTitle("Notification Updated!"));

        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());

        setNotificationButtonState(false, false ,true);
    }

    private void cancelNotification(){
        mNotifyManager.cancel(NOTIFICATION_ID);

        //setNotificationButtonState(true, false ,false);
    }

    public void createNotificationChannel(){
        /*
        Notification channels is the different categories of notifications that
        show in the settings of your app. It may define properties of your app's notifs
        like enabling lights (boolean), vibration, etc. It is different from
        NotificationCompat.Builder which is used for actually building the specific notification.
         */

        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            // Create a notification channel
            NotificationChannel notificationChannel = new NotificationChannel(
                    PRIMARY_CHANNEL_ID, "Mascot Notification",
                    NotificationManager.IMPORTANCE_HIGH);
            // the following are the initial settings of the notification
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Mascot");
            mNotifyManager.createNotificationChannel(notificationChannel);

        }
    }

    private NotificationCompat.Builder getNotificationBuilder(){ // used for actually building the notification

        /* Intent that the notification uses, to be able to open the activity by tapping the notif */
        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        /*
        The major difference with an Intent that's used for a notification is that
        the Intent must be wrapped in a PendingIntent. The PendingIntent allows
        the Android notification system to perform the assigned action on behalf of your code.
         */

        NotificationCompat.Builder notifyBuilder =
                new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("You've been notified! (title)")
                .setContentText("This is your notification (text).")
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        return notifyBuilder;

    }

    void setNotificationButtonState(Boolean isNotifyEnabled,
                                    Boolean isUpdateEnabled,
                                    Boolean isCancelEnabled) {
        button_notify.setEnabled(isNotifyEnabled);
        button_update.setEnabled(isUpdateEnabled);
        button_cancel.setEnabled(isCancelEnabled);
    }
}
