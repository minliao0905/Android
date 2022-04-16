package com.example.study_6_2.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<String> mImg;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
        mImg = new MutableLiveData<>();
        mImg.setValue("@drawable/qqn");
    }


    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<String> getImg(){
        return mImg;
    }
}