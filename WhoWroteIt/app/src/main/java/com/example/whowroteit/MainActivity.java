package com.example.whowroteit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText mBookInput;
    private TextView mTitleText;
    private TextView mAuthorText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* initializing variables */
        mBookInput = findViewById(R.id.bookInput);
        mTitleText = findViewById(R.id.titleText);
        mAuthorText = findViewById(R.id.authorText);

    }

    public void searchBooks(View view) {
        String queryString = mBookInput.getText().toString();

        /* TO DISAPPEAR THE KEYBOARD WHILE THE QUERY IS EXECUTING */
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputManager != null){
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        /* Handle the possibility that a network connection is unavailable */
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if(connMgr != null)
            networkInfo = connMgr.getActiveNetworkInfo();

        /*
        If network is available and the queryString is not empty, then go ahead and execute
         */
        if(networkInfo != null && networkInfo.isConnected() && queryString.length() != 0){
            /* Use the async task to execute the book search */
            new FetchBook(mTitleText, mAuthorText).execute(queryString);

            /* SHOW THE LOADING TEXT */
            mAuthorText.setText("");
            mTitleText.setText(R.string.loading);
        } else{
            if(queryString.length() == 0){
                mAuthorText.setText("");
                mTitleText.setText(R.string.no_search_term);
            } else{
                mAuthorText.setText("");
                mTitleText.setText(R.string.no_network);
            }
        }

    }
}
