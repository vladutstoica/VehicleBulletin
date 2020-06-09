package com.shto.vehiclebulletin.ui.vehicles;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VehiclesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public VehiclesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}