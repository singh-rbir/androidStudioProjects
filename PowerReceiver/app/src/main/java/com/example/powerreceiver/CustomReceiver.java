package com.example.powerreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CustomReceiver extends BroadcastReceiver {

    /* INFO:
    A system broadcast is a message that the Android system sends when a system event occurs.
    Each system broadcast is wrapped in an Intent object

    TWO KINDS OF RECEIVERS: STATIC (declared in androidManifest) and DYNAMIC (declared in context)

    THREE KINDS OF BROADCASTS: NORMAL, LOCAL, ORDERED
    The broadcast message is wrapped in an Intent object
     */

    private static final String ACTION_CUSTOM_BROADCAST =
            BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        String intentAction = intent.getAction();

        if(intentAction != null){
            String toastMessage = "unknown intent action";
            switch (intentAction){
                case Intent.ACTION_POWER_CONNECTED:
                    toastMessage = "Power connected";
                    break;
                case Intent.ACTION_POWER_DISCONNECTED:
                    toastMessage = "Power disconnected";
                    break;
                case ACTION_CUSTOM_BROADCAST:
                    toastMessage = "Custom Broadcast Received";
                    break;
            }
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
        }

    }
}
