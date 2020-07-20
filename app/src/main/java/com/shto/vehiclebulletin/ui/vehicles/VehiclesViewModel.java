package com.shto.vehiclebulletin.ui.vehicles;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VehiclesViewModel extends ViewModel {

    private MutableLiveData<String> mClickPosition = new MutableLiveData<>();

    public void setClickPosition(String clickPosition) {
        mClickPosition.setValue(clickPosition);
    }

    public MutableLiveData<String> getClickPosition() {
        return mClickPosition;
    }
}