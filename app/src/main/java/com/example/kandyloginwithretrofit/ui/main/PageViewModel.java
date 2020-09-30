package com.example.kandyloginwithretrofit.ui.main;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {

    private MutableLiveData<String> nameInput = new MutableLiveData<>();

    public void setName(String name) {
        nameInput.setValue(name);
    }

    public LiveData<String> getName() {
        return nameInput;
    }

}