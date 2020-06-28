package com.shto.vehiclebulletin.ui.vehicles.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shto.vehiclebulletin.R;

import java.util.HashMap;
import java.util.Map;

public class AddVehicleDialogFragment extends DialogFragment {

    private static final String TAG = "Add vehicle";

    ChipGroup mChipGroup;
    AutoCompleteTextView mBrand;
    TextInputEditText mModel;
    Button mBuildDate;
    AutoCompleteTextView mFuel;
    TextInputEditText mColor;
    TextInputEditText mLicencePlate;

    String selectedChipText;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private static final String[] COUNTRIES = new String[]{
            "Belgium", "France", "Italy", "Germany", "Spain"
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Declare an instance of FirebaseFirestore
        db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.vehicle_dialog, null);

        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(view);
        final AlertDialog createAlertDialog = alertDialogBuilder.create();

        // Create DatePicker Dialog
        final MaterialDatePicker.Builder<Long> datePicker = MaterialDatePicker.Builder.datePicker();
        datePicker.setTitleText("R.string.your_text");
        final MaterialDatePicker<Long> picker = datePicker.build();

        // Add vehicle dialog member variable
        mChipGroup = view.findViewById(R.id.chipGroup);
        mBrand = view.findViewById(R.id.brandAutoCompleteTextView);
        mModel = view.findViewById(R.id.model_input);
        mBuildDate = view.findViewById(R.id.date_input);
        mFuel = view.findViewById(R.id.fuelAutocompleteTextView);
        mColor = view.findViewById(R.id.color_input);
        mLicencePlate = view.findViewById(R.id.licence_plate_input);

        mChipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                Chip chip = group.findViewById(checkedId);
                if (chip != null) {
                    selectedChipText = chip.getText().toString();
                }
            }
        });

        mBuildDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.show(getParentFragmentManager(), picker.toString());
                picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        mBuildDate.setText(picker.getHeaderText());
                    }
                });
            }
        });

        // Get a reference to the AutoCompleteTextView in the layout
        AutoCompleteTextView autoTextView = mBrand;
        // Get the string array
        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireActivity(), android.R.layout.simple_dropdown_item_1line, COUNTRIES
        );
        autoTextView.setAdapter(adapter);

        // Positive and Cancel button setup
        Button addButton = view.findViewById(R.id.add_vehicle_button);
        Button cancelButton = view.findViewById(R.id.cancel_vehicle_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "createUserWithEmail:success");
                FirebaseUser user = mAuth.getCurrentUser();
                updateDB(user);
                createAlertDialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddVehicleDialogFragment.this.getDialog().cancel();
            }
        });
        return createAlertDialog;
    }

    private void updateDB(FirebaseUser user) {

        Map<String, Object> vehicle = new HashMap<>();
        vehicle.put("type", selectedChipText);
        vehicle.put("brand", mBrand.getText().toString());
        vehicle.put("model", mModel.getText().toString());
        vehicle.put("build-date", mBuildDate.getText().toString());
        vehicle.put("fuel", mFuel.getText().toString());
        vehicle.put("color", mColor.getText().toString());
        vehicle.put("licence-plate", mLicencePlate.getText().toString());


        if (user != null) {
            db.collection("users")
                    .document(user.getUid()).collection("vehicles")
                    .add(vehicle)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                        }
                    });
        }
    }
}