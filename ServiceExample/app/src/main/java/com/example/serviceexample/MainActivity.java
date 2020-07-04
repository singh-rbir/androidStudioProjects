package com.example.serviceexample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_TIME = "alarm_time";

    public AlarmManager alarmMgr;
    static int alarmRequestCode = 5;
    private EditText mTimeEditText;
    Button scheduleButton;

    private static final String ACTION_CUSTOM_BROADCAST = "ACTION_CUSTOM_BROADCAST_ALARM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimeEditText = (findViewById(R.id.editTextId));
        scheduleButton = findViewById(R.id.schedule_button);
        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)){
            Intent action = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            startActivity(action);
        }

        stopService(new Intent(this, AlarmService.class));

    }

    public void scheduleAlarm(View view) {

        String time = mTimeEditText.getText().toString();

        String[] tokens = time.split(":", -3);
        int hours = Integer.parseInt(tokens[0]); // calculates hours from time strings tokens
        int mins = Integer.parseInt(tokens[1]); // calculates minutes

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, mins);
        calendar.set(Calendar.SECOND, 0);
        long startTime = calendar.getTimeInMillis();

        //Log.d("asdf", "start time in mainactivity: " + startTime);

        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra(EXTRA_TIME, time);
        //intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        //intent.setAction(ACTION_CUSTOM_BROADCAST);
        PendingIntent pi = PendingIntent.getBroadcast(this, alarmRequestCode++, intent, 0);
        alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, startTime, pi);

//        stopService(serviceIntent);
//        startService(serviceIntent);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            startForegroundService(serviceIntent);
//        }else
//            startService(serviceIntent);
//        PendingIntent pi = PendingIntent.getService(this, alarmRequestCode++, serviceIntent, 0);
//        alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, startTime, pi);


//

        //long startTime = calendar.getTimeInMillis(); // refresh startTime after resetting Calendar.Day_OF_WEEK

        //Intent alarmIntent = new Intent(this, AlarmReceiver.class);
//        Intent alarmIntent = new Intent("com.example.serviceexample.START_ALARM");
//        alarmIntent.setClass(getApplicationContext(), AlarmReceiver.class);
//        Intent alarmIntent = new Intent(this, AlarmService.class);
//        alarmIntent.putExtra(EXTRA_TIME, convertTo12HrTime(time)); // passes time in 12hr format string


        System.out.println("[Set] Pending intents's requestCode: " + alarmRequestCode);
        //System.out.println("Start time right before setting alarm is: " + startTime);
        //PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(this, alarmRequestCode + 2, alarmIntent, 0);

        //stopService(alarmIntent);
        //PendingIntent alarmPendingIntent = PendingIntent.getService(this, alarmRequestCode, alarmIntent, 0);

        Log.d("asdf", "pendingintent created");

        //alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, startTime, AlarmManager.INTERVAL_DAY * 7, alarmPendingIntent);
        //alarmMgr.setExact(AlarmManager.RTC_WAKEUP, startTime, alarmPendingIntent);

    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    public static String convertTo12HrTime(String time){
        String result = "";
        String mode = "a.m.";
        String[] tokens = time.split(":");
        int hours = Integer.parseInt(tokens[0]);
        String minutes = tokens[1];
        if(hours > 12 && hours < 24){
            hours = hours - 12;
            mode = "p.m.";
        } else if(hours == 0)
            hours = 12;
        else if(hours == 12)
            mode = "p.m.";
        result += (hours) + ":" + minutes + " " + mode;

        return result;
    }

    @Override
    protected void onDestroy() {
        Log.d("asdf", "mainActivity onDestroy");
        super.onDestroy();
    }


}