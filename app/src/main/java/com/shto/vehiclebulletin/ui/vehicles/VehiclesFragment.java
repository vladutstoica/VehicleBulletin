package com.shto.vehiclebulletin.ui.vehicles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.shto.vehiclebulletin.R;

public class VehiclesFragment extends Fragment {

    private VehiclesViewModel mVehiclesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mVehiclesViewModel = new ViewModelProvider(this).get(VehiclesViewModel.class);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vehicles, container, false);
    }
}