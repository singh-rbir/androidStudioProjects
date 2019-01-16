package com.example.rajanbirsingh.hellotoast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showToast(View view){
        Toast myToast = Toast.makeText(this, R.string.toast_message, Toast.LENGTH_SHORT);
        myToast.show();
    }

    public void countUp(View view){
        count++;
        TextView showCount = (TextView)findViewById(R.id.show_count);
        if(count != 0)
            showCount.setText(Integer.toString(count));

    }

}
