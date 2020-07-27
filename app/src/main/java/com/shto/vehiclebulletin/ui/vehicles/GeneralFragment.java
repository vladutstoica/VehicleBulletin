package com.shto.vehiclebulletin.ui.vehicles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.shto.vehiclebulletin.R;
import com.shto.vehiclebulletin.ui.vehicles.pojos.VehicleGeneral;

public class GeneralFragment extends Fragment {
    VehiclesViewModel mVehiclesViewModel;

    TextView brand;
    TextView model;
    TextView buildDate;
    TextView fuel;
    TextView color;
    TextView licencePlate;

    final int[] clickPosition = new int[1];

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
        mVehiclesViewModel =
                new ViewModelProvider(requireActivity()).get(VehiclesViewModel.class);

        View root = inflater.inflate(R.layout.fragment_general, container, false);

        brand = root.findViewById(R.id.text_general_brand);
        model = root.findViewById(R.id.text_general_model);
        buildDate = root.findViewById(R.id.text_general_builddate);
        fuel = root.findViewById(R.id.text_general_fuel);
        color = root.findViewById(R.id.text_general_color);
        licencePlate = root.findViewById(R.id.text_general_licence);

        mVehiclesViewModel.getClickPosition()
                .observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                clickPosition[0] = Integer.parseInt(s);

                updateView();
            }
        });

        return root;
    }

    private void updateView() {
        brand.setText(VehicleGeneral.sVehicleGenerals.get(clickPosition[0]).getBrand());
        model.setText(VehicleGeneral.sVehicleGenerals.get(clickPosition[0]).getModel());
        buildDate.setText(VehicleGeneral.sVehicleGenerals.get(clickPosition[0]).getDate());
        fuel.setText(VehicleGeneral.sVehicleGenerals.get(clickPosition[0]).getFuel());
        color.setText(VehicleGeneral.sVehicleGenerals.get(clickPosition[0]).getColor());
        licencePlate.setText(VehicleGeneral.sVehicleGenerals.get(clickPosition[0]).getLicence());
    }
}