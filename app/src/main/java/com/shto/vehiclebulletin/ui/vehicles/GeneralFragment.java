package com.shto.vehiclebulletin.ui.vehicles;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.shto.vehiclebulletin.R;

public class GeneralFragment extends Fragment {
    private static final String TAG = "general";
    VehiclesViewModel mVehiclesViewModel;

    TextView brand;
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

        brand = root.findViewById(R.id.brand_view);

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
        Log.d("generall", "onCreateView: "
                + clickPosition[0]);
        brand.setText(VehicleGeneral.mVehiclesGeneralData.get(clickPosition[0]).getBrand());
    }
}