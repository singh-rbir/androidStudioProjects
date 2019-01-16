package com.example.rajanbirsingh.twoactivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.twoactivities.extra.REPLY";
    private EditText mReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Step 2: Replying, and sending it back to the first activity (see and do Step 1 first)
        mReply = findViewById(R.id.editText_second);

        /*
        Step 1:
        Getting the intent from the first activity
         */
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.text_message);
        textView.setText(message);

    }

    public void returnReply(View view) {

        String message = mReply.getText().toString();
        Intent replyIntent = new Intent();

        replyIntent.putExtra(EXTRA_REPLY, message);
        setResult(RESULT_OK, replyIntent);
        finish(); // this closes the activity and also returns to the main activity
    }
}
