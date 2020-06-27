package com.shto.vehiclebulletin.ui.vehicles;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.shto.vehiclebulletin.R;

public class AddVehicleDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(getLayoutInflater().inflate(R.layout.vehicle_dialog, null));

        // Add action buttons
//           .setPositiveButton("add", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int id) {
//                // sign in the user ...
//            }
//        })
//                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        AddVehicleDialogFragment.this.getDialog().cancel();
//                    }
//                });

        return builder.create();
    }

    /**
     * The system calls this to get the DialogFragment's layout, regardless
     * of whether it's being displayed as a dialog or an embedded fragment.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment

        final View root = inflater.inflate(R.layout.vehicle_dialog, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] COUNTRIES = new String[]{"Item 1", "Item 2", "Item 3", "Item 4"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        getContext(),
                        R.layout.dropdown_item,
                        COUNTRIES);

        AutoCompleteTextView editTextFilledExposedDropdown =
                view.findViewById(R.id.brandAutoCompleteTextView);
        editTextFilledExposedDropdown.setAdapter(adapter);
    }
}