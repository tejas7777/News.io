package com.example.newsio.ui.main;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {



    private MutableLiveData<String> mName = new MutableLiveData<>();
    private LiveData<String> mText = Transformations.map(mName, new Function<String, String>() {
        @Override
        public String apply(String name) {
            return "Hello world from " + name;
        }
    });

    public void setName(String name) {
        mName.setValue(name);
    }


    public LiveData<String> getText() {
        return mText;
    }
}