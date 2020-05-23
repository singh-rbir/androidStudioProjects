package com.example.rajanbirsingh.bmi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class BMIActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
    }

    private String getInputOfTextField(int id){
        EditText et = (EditText) findViewById(id);
        String input = et.getText().toString();
        return input;
    }

    private void setContentsOfTextView(int id, String contents){
        TextView tv = (TextView)findViewById(id);
        tv.setText(contents);
    }

    public void onComputeButtonClicked(View view){
        if(getInputOfTextField(R.id.weight_label).equals("")){
            // Show a toast "Please specify the weight"
        }

        if(!getInputOfTextField(R.id.weight_label).equals("") && !getInputOfTextField(R.id.height_label).equals("")){
            String nameText = getInputOfTextField(R.id.name_label);
            String weightText = getInputOfTextField(R.id.weight_label);
            String heightText = getInputOfTextField(R.id.height_label);

            double weight = Double.parseDouble(weightText);
            double height = Double.parseDouble(heightText);

            Person user = new Person(nameText, weight, height);

            setContentsOfTextView(R.id.answer_label, user.toString());
        }
    }

    // Following methods are for the spinner (drop down menu)

    private String getItemSelected(int id){
        Spinner spinner = (Spinner) findViewById(id);
        String item = spinner.getSelectedItem().toString();
        return item;
    }

    public void onSpinnerClicked(View view){
        String selectedItem = getItemSelected(R.id.spinner_label);
        if(selectedItem.equals("Option 1")){
            setContentsOfTextView(R.id.answer_label, "Option 1 was selected");
        } else if(selectedItem.equals("Option 2")){
            setContentsOfTextView(R.id.answer_label, "Option 2 was selected");
        }
    }



}
