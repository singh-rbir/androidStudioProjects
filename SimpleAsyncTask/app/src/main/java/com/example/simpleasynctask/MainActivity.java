package com.example.simpleasynctask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;

    private static final String TEXT_STATE = "currentText";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            mTextView.setText(savedInstanceState.getString(TEXT_STATE));
        }

        mTextView = findViewById(R.id.textView1);

    }

    public void startTask(View view) {
        mTextView.setText(R.string.napping_text);
        new SimpleAsyncTask(mTextView).execute();
    }

    /*
    now implementing onSaveInstanceState() to preserve the content of the TextView when the activity
    is destroyed in response to a configuration change such as device rotation.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString(TEXT_STATE, mTextView.getText().toString());

    }


}
