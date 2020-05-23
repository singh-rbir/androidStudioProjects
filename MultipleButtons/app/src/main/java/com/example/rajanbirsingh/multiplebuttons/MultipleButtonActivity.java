package com.example.rajanbirsingh.multiplebuttons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MultipleButtonActivity extends AppCompatActivity {

    private static Counter c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_button);
        c = new Counter();
        setContentsOfTextView(R.id.counter_label, c.toString());
    }

    public void setContentsOfTextView(int id, String contents){
        TextView textView = (TextView) findViewById(id);
        textView.setText(contents);
    }

    public void buttonIncrementClicked(View view){
        if(!c.hasReachedMaximum()){
            c.increment();
            setContentsOfTextView(R.id.counter_label, c.toString());
        }

    }

    public void buttonDecrementClicked(View view){
        if(!c.hasReachedMinimum()) {
            c.decrement();
            setContentsOfTextView(R.id.counter_label, c.toString());
        }
    }

    public void buttonResetClicked(View view){
        c = new Counter();
        setContentsOfTextView(R.id.counter_label, c.toString());
    }

}
