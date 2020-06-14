package com.shto.vehiclebulletin.ui.vehicles;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.shto.vehiclebulletin.R;

public class VehiclesFragment extends Fragment {

    // ViewModel
    private VehiclesViewModel mVehiclesViewModel;

    // FAB
    private ExtendedFloatingActionButton mFab;

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
//        String[] mLicencePlate = new String[Vehicles.vehicles.length];
//        for (int i = 0; i < mLicencePlate.length; i++) {
//            mLicencePlate[i] = Vehicles.vehicles[i].getLicencePlate();
//        }
//
//        String[] mVehicleModel = new String[Vehicles.vehicles.length];
//        for (int i = 0; i < mVehicleModel.length; i++) {
//            mVehicleModel[i] = Vehicles.vehicles[i].getVehicleModel();
//        }
//
//        String[] mRenew = new String[Vehicles.vehicles.length];
//        for (int i = 0; i < mRenew.length; i++) {
//            mRenew[i] = Vehicles.vehicles[i].getRenew();
//        }
//
//        String[] mTotalCost = new String[Vehicles.vehicles.length];
//        for (int i = 0; i < mTotalCost.length; i++) {
//            mTotalCost[i] = Vehicles.vehicles[i].getTotalCost();
//        }
//
//        int[] mVehicleLogo = new int[Vehicles.vehicles.length];
//        for (int i = 0; i < mVehicleLogo.length; i++) {
//            mVehicleLogo[i] = Vehicles.vehicles[i].getVehicleLogo();
//        }

        //only testing ONLTTTT
        String[] mLicencePlate = new String[Vehicles.test.size()];
        for (int i = 0; i < mLicencePlate.length; i++) {
            mLicencePlate[i] = Vehicles.test.get(i).getLicencePlate();
        }

        String[] mVehicleModel = new String[Vehicles.test.size()];
        for (int i = 0; i < mVehicleModel.length; i++) {
            mVehicleModel[i] = Vehicles.test.get(i).getVehicleModel();
        }

        String[] mRenew = new String[Vehicles.test.size()];
        for (int i = 0; i < mRenew.length; i++) {
            mRenew[i] = Vehicles.test.get(i).getRenew();
        }

        String[] mTotalCost = new String[Vehicles.test.size()];
        for (int i = 0; i < mTotalCost.length; i++) {
            mTotalCost[i] = Vehicles.test.get(i).getTotalCost();
        }

        int[] mVehicleLogo = new int[Vehicles.test.size()];
        for (int i = 0; i < mVehicleLogo.length; i++) {
            mVehicleLogo[i] = Vehicles.test.get(i).getVehicleLogo();
        }


        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        mAdapter = new VehiclesAdapter(mLicencePlate, mVehicleModel, mRenew, mTotalCost, mVehicleLogo);
        mRecyclerView.setAdapter(mAdapter);


        final int test1 = Vehicles.test.size();
        //working
        mFab = view.findViewById(R.id.extended_fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("FAB", "working fab");
                Toast.makeText(getContext(),"working " + test1, Toast.LENGTH_SHORT).show();

                Vehicles.test.add(new Vehicles("asd","asd","asd","ad", R.drawable.ic_home_black_24dp));
                mAdapter.notifyItemInserted(test1);
                //mAdapter.notifyDataSetChanged();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}