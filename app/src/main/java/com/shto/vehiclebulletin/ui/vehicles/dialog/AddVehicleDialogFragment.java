package com.shto.vehiclebulletin.ui.vehicles.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.shto.vehiclebulletin.R;
import com.shto.vehiclebulletin.ui.vehicles.Vehicles;

public class AddVehicleDialogFragment extends DialogFragment {

    private static final String[] COUNTRIES = new String[]{
            "Belgium", "France", "Italy", "Germany", "Spain"
    };

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.vehicle_dialog, null);

        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        final Button data = view.findViewById(R.id.date_input);

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DialogFragment newFragment = new DatePickerFragment();
//                newFragment.show(getParentFragmentManager(), "datePicker");
                MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
                builder.setTitleText("R.string.your_text");
                final MaterialDatePicker<Long> picker = builder.build();
                picker.show(getParentFragmentManager(), picker.toString());
                picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        data.setText(picker.getHeaderText());
                    }
                });
            }
        });

        Button addButton = view.findViewById(R.id.add_vehicle_button);
        Button cancelButton = view.findViewById(R.id.cancel_vehicle_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vehicles.mVehiclesData.add(new Vehicles(
                        "PH-20-SVM",
                        "MAZDA 3 - 2006",
                        "last act",
                        "4 000 EURO",
                        R.drawable.ic_round_home_24
                ));
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddVehicleDialogFragment.this.getDialog().cancel();
            }
        });

        // Get a reference to the AutoCompleteTextView in the layout
        AutoCompleteTextView autoTextView = view.findViewById(R.id.brandAutoCompleteTextView);
        // Get the string array
        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                requireActivity(), android.R.layout.simple_dropdown_item_1line, COUNTRIES
        );
        autoTextView.setAdapter(adapter);

        return builder.create();
    }
}