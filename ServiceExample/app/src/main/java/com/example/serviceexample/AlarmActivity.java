package com.example.serviceexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AlarmActivity extends AppCompatActivity {

    public PowerManager.WakeLock wakeLock;
    public PowerManager powerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        Log.d("asdf", "---Alarm activity onCreate");

        powerManager = (PowerManager) getSystemService(POWER_SERVICE);

        TextView mTimeView = findViewById(R.id.time_textView);

        if(getIntent() != null){
            mTimeView.setText(getIntent().getStringExtra(MainActivity.EXTRA_TIME));
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        acquireScreenCpuWakeLock(this);
        final Window window = getWindow();
        window.makeActive();
        window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
                /*| WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY*/);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    public void acquireScreenCpuWakeLock(Context context){
        if(wakeLock != null){
            return;
        }
        powerManager = (PowerManager)context.getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK |
                PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "MyApp::MyWakeLockTag");
        wakeLock.acquire(10000);
    }
}