package com.shto.vehiclebulletin.ui.vehicles;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.shto.vehiclebulletin.R;

public class AddVehicleDialogFragment extends DialogFragment {

    private static final String[] COUNTRIES = new String[]{
            "Belgium", "France", "Italy", "Germany", "Spain"
    };

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.vehicle_dialog, null);

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view);

        // Get a reference to the AutoCompleteTextView in the layout
        AutoCompleteTextView autoTextView = view.findViewById(R.id.brandAutoCompleteTextView);
        // Get the string array
        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, COUNTRIES);

        autoTextView.setAdapter(adapter);


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
}