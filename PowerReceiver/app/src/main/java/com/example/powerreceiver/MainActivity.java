package com.example.powerreceiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/* INTRODUCTION TO BROADCASTS:
Broadcasts are messages that the Android system and Android apps send when events occur that
might affect the functionality of other apps or app components.

A broadcast is received by any app or app component that has a broadcast receiver registered
for that action.
 */

public class MainActivity extends AppCompatActivity {

    private static final String ACTION_CUSTOM_BROADCAST =
            BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";

    private CustomReceiver mReceiver = new CustomReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* TASK 1 OF BROADCAST RECEIVER'S CODELAB
        Intent filters specify the types of intents a component can receive
         */
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);

        // Register the receiver using the activity context.
        this.registerReceiver(mReceiver, filter);

        /* TASK 2 OF THE SAME CODELAB */
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver,
                new IntentFilter(ACTION_CUSTOM_BROADCAST));

    }

    @Override
    protected void onDestroy(){
        //Unregister the receiver - To save system resources and avoid leaks
        // task 1
        this.unregisterReceiver(mReceiver);

        // task 2 of the codelab
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
        super.onDestroy();
    }


    public void sendCustomBroadcast(View view) {
        Intent customBroadcastIntent = new Intent(ACTION_CUSTOM_BROADCAST);
        LocalBroadcastManager.getInstance(this).sendBroadcast(customBroadcastIntent);

    }
}
