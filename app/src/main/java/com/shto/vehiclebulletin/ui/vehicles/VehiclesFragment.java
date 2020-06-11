package com.shto.vehiclebulletin.ui.vehicles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shto.vehiclebulletin.R;

public class VehiclesFragment extends Fragment {

    // ViewModel
    private VehiclesViewModel mVehiclesViewModel;

    // RecyclerView
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mVehiclesViewModel = new ViewModelProvider(this).get(VehiclesViewModel.class);


        //testing
        View view = inflater.inflate(R.layout.fragment_vehicles, container, false);
        mRecyclerView = view.findViewById(R.id.vehicles_recycler_view);
        // TODO: make below like work
        //mRecyclerView = inflater.inflate(R.layout.fragment_vehicles, container, false);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        //only testing
        String[] text1 = new String[Vehicles.vehicles.length];
        for (int i = 0; i < text1.length; i++) {
            text1[i] = Vehicles.vehicles[i].getText1();
        }

        String[] text2 = new String[Vehicles.vehicles.length];
        for (int i = 0; i < text2.length; i++) {
            text2[i] = Vehicles.vehicles[i].getText2();
        }

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        mAdapter = new VehiclesAdapter(text1, text2);
        mRecyclerView.setAdapter(mAdapter);

        // Inflate the layout for this fragment
        return view;
    }

}