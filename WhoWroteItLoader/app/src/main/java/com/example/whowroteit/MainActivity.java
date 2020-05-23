package com.example.whowroteit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<String> {

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

        if(getSupportLoaderManager().getLoader(0)!=null){
            getSupportLoaderManager().initLoader(0,null,this);
        }

    }

    public void searchBooks(View view) {
        String queryString = mBookInput.getText().toString();

        /*
        Using the loader
         */
        Bundle queryBundle = new Bundle();
        queryBundle.putString("queryString", queryString);
        getSupportLoaderManager().restartLoader(0, queryBundle, this);
        //LoaderManager.getInstance(this).restartLoader(0, queryBundle, this);

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

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        String queryString = "";

        if(bundle != null)
            queryString = bundle.getString("queryString");

        return new BookLoader(this, queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
        try{
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            int i = 0;
            String title = null;
            String authors = null;

            while(i < itemsArray.length() && authors == null && title == null){
                // get the current item information
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                // Try to get the author and title from the current item,
                // catch if either field is empty and move on.
                try{
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                } catch(Exception e){
                    e.printStackTrace();
                }

                i++;
            }

            if(title != null && authors != null){
                mTitleText.setText(title);
                mAuthorText.setText(authors);
            } else{
                mTitleText.setText(R.string.no_results);
                mAuthorText.setText("");
            }

        } catch(JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
