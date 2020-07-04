package com.example.roomwordssample;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/*
NOTE: Never pass context into ViewModel instances.
Do not store Activity, Fragment, or View instances or their Context in the ViewModel.
 */
public class WordViewModel extends AndroidViewModel {

    private WordRepository mRepository;

    /* LiveData member variable to cache the list of words */
    private LiveData<List<Word>> mAllWords;


    public WordViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    LiveData<List<Word>> getAllWords(){
        return mAllWords;
    }

    public void insert(Word word){
        mRepository.insert(word);
    }

}
