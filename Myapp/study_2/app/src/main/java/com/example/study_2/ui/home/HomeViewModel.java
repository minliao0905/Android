package com.example.study_2.ui.home;

import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    public HomeViewModel() {
        mText = new MutableLiveData<>();

        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}