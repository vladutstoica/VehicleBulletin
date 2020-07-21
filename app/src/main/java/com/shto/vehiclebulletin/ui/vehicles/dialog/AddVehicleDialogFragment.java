package com.shto.vehiclebulletin.ui.vehicles.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shto.vehiclebulletin.R;
import com.shto.vehiclebulletin.ui.vehicles.pojos.VehicleGeneral;
import com.shto.vehiclebulletin.ui.vehicles.pojos.VehiclesOverview;

public class AddVehicleDialogFragment extends DialogFragment {
    private static final String TAG = "Add vehicle";

    AutoCompleteTextView mBrand;
    AutoCompleteTextView mFuel;
    Button mBuildDate;
    EditText mModel;
    EditText mColor;
    EditText mLicencePlate;
    ChipGroup mChipGroup;

    String selectedChipText;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private static final String[] BRANDS = new String[]{
            "Opel", "Mazda", "BMW", "Skoda", "Volkswagen"
    };

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.dialog_vehicles_add, null);

        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(view);
        final AlertDialog createAlertDialog = alertDialogBuilder.create();

        // Create DatePicker Dialog
        final MaterialDatePicker.Builder<Long> datePicker = MaterialDatePicker.Builder.datePicker();
        datePicker.setTitleText(R.string.prompt_year);
        final MaterialDatePicker<Long> picker = datePicker.build();

        // Add vehicle dialog member variable
        mChipGroup = view.findViewById(R.id.chip_group);
        mBrand = view.findViewById(R.id.brandAutoCompleteTextView);
        mModel = view.findViewById(R.id.model_input);
        mBuildDate = view.findViewById(R.id.date_input);
        mFuel = view.findViewById(R.id.fuelAutocompleteTextView);
        mColor = view.findViewById(R.id.input_overhauls_name);
        mLicencePlate = view.findViewById(R.id.licence_plate_input);

        selectedChipText = getResources().getString(R.string.prompt_car);
        // Chip group selection listener
        mChipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                Chip chip = group.findViewById(checkedId);
                if (chip != null) {
                    selectedChipText = chip.getText().toString();
                }
            }
        });

        // Date dialog listener
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

        /* DROPDOWN AUTOCOMPLETETEXTVIEW
         * Get a reference to the AutoCompleteTextView in the layout
         * Create the adapter and set it to the AutoCompleteTextView
         */
        AutoCompleteTextView autoTextView = mBrand;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireActivity(), android.R.layout.simple_dropdown_item_1line, BRANDS
        );
        autoTextView.setAdapter(adapter);

        // Custom buttons
        Button addButton = view.findViewById(R.id.add_vehicle_button);
        Button cancelButton = view.findViewById(R.id.cancel_vehicle_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateForm()) {
                    return;
                }
                addProcess();
                createAlertDialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddVehicleDialogFragment.this.requireDialog().cancel();
            }
        });

        return createAlertDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    private boolean validateForm() {
        boolean valid = true;

        // TODO: restrict user input
        String brand = mBrand.getText().toString();
        if (TextUtils.isEmpty(brand)) {
            mBrand.setError("Required.");
            valid = false;
        } else {
            mBrand.setError(null);
        }

        String model = mModel.getText().toString();
        if (TextUtils.isEmpty(model)) {
            mModel.setError("Required.");
            valid = false;
        } else {
            mModel.setError(null);
        }

//        if (mBuildDate.getText().toString().equals(getResources().getString(R.string.prompt_year))) {
//            // TODO: validate date
//        }

        String fuel = mFuel.getText().toString();
        if (TextUtils.isEmpty(fuel)) {
            mFuel.setError("Required.");
            valid = false;
        } else {
            mFuel.setError(null);
        }

        String color = mColor.getText().toString();
        if (TextUtils.isEmpty(color)) {
            mColor.setError("Required.");
            valid = false;
        } else {
            mColor.setError(null);
        }

        String licence = mLicencePlate.getText().toString();
        if (TextUtils.isEmpty(licence)) {
            mLicencePlate.setError("Required.");
            valid = false;
        } else {
            mLicencePlate.setError(null);
        }

        return valid;
    }

    private void addProcess() {
        FirebaseUser user = mAuth.getCurrentUser();

        updateDB(user);
    }

    private void updateDB(final FirebaseUser user) {
        String refId;

        if (user != null) {
            refId = db.collection("users")
                    .document(user.getUid())
                    .collection("vehicles")
                    .document()
                    .getId();

            final VehicleGeneral vehicleGeneral = new VehicleGeneral(
                    refId,
                    selectedChipText,
                    mBrand.getText().toString(),
                    mModel.getText().toString(),
                    mBuildDate.getText().toString(),
                    mFuel.getText().toString(),
                    mColor.getText().toString(),
                    mLicencePlate.getText().toString()
            );

            db.collection("users")
                    .document(user.getUid())
                    .collection("vehicles")
                    .document(refId)
                    .set(vehicleGeneral)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully written!");
                            updateLocal(vehicleGeneral);
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

    private void updateLocal(VehicleGeneral vehicleGeneral) {

        // Add to generals
        VehicleGeneral.sVehicleGenerals.add(vehicleGeneral);


        // Add  to overviews - TODO: TO OPTIMIZE
        VehiclesOverview data = new VehiclesOverview();
        String dataRenew = data.getRenew();
        String dataCost = data.getTotalCost();
        int dataLogo = data.getBrandLogoId();

        VehiclesOverview.sVehiclesOverviews.add(
                new VehiclesOverview(
                        vehicleGeneral.getRefId(),
                        vehicleGeneral.getLicence(),
                        vehicleGeneral.getBrand() + " " + vehicleGeneral.getModel(),
                        dataRenew,
                        dataCost,
                        dataLogo
                )
        );
    }
}