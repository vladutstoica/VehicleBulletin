package com.shto.vehiclebulletin.ui.vehicles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.shto.vehiclebulletin.R;

public class GeneralFragment extends Fragment {

    TextView brand;

    public GeneralFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_general, container, false);

        brand = root.findViewById(R.id.brand_textView);
        brand.setText(VehicleGeneral.mVehiclesGeneralData.get(0).getBrand());

        return root;
    }
}