package com.shto.vehiclebulletin.ui.vehicles.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
import com.shto.vehiclebulletin.ui.vehicles.VehicleGeneral;
import com.shto.vehiclebulletin.ui.vehicles.VehiclesOverview;

public class AddVehicleDialogFragment extends DialogFragment {

    private static final String TAG = "Add vehicle";

    ChipGroup mChipGroup;
    AutoCompleteTextView mBrand;
    EditText mModel;
    Button mBuildDate;
    AutoCompleteTextView mFuel;
    EditText mColor;
    EditText mLicencePlate;

    // TODO: String selectedChipText = requireContext().getResources().getString(R.string.prompt_car);
    String selectedChipText = "Car";

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private static final String[] COUNTRIES = new String[]{
            "Belgium", "France", "Italy", "Germany", "Spain"
    };

    // Use this instance of the interface to deliver action events
    NoticeDialogListener listener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (NoticeDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException( /* activity.toString()
                    + */ " must implement NoticeDialogListener");
        }
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

                if (!validateForm()) {
                    return;
                }

                addProcess();

                // Send the positive button event back to the host activity
                //listener.onDialogPositiveClick();
                createAlertDialog.dismiss();

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send the positive button event back to the host activity
                listener.onDialogNegativeClick();
                AddVehicleDialogFragment.this.getDialog().cancel();
            }
        });

        return createAlertDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Declare an instance of FirebaseFirestore
        db = FirebaseFirestore.getInstance();
    }

    // TODO: optimize
    private void updateLocal(VehicleGeneral vehicleGeneral) {

        VehicleGeneral.mVehiclesGeneralData.add(vehicleGeneral);

        VehiclesOverview data = new VehiclesOverview();
        String dataRenew = data.getRenew();
        String dataCost = data.getTotalCost();
        int dataLogo = data.getBrandLogoId();

        VehiclesOverview.mVehiclesOverviewData.add(
                new VehiclesOverview(
                        vehicleGeneral.getRefId(),
                        vehicleGeneral.getLicence(),
                        vehicleGeneral.getBrand() + " " + vehicleGeneral.getModel(),
                        dataRenew,
                        dataCost,
                        dataLogo
                )
        );


        listener.onDialogPositiveClick();
    }

    private void addProcess() {
        FirebaseUser user = mAuth.getCurrentUser();

        updateDB(user);
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

        if (mBuildDate.getText().toString().equals(getResources().getString(R.string.prompt_year))) {
            // TODO: validate date
        }

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

    private void updateDB(final FirebaseUser user) {
        String refId;

        if (user != null) {
            refId = db.collection("users").document(user.getUid()).collection("vehicles").document().getId();

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

            db.collection("users").document(user.getUid()).collection("vehicles").document(refId).set(vehicleGeneral)
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

        } else {
            mLicencePlate.setText(null);
        }
    }

    // Event callback
    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        public void onDialogPositiveClick();

        public void onDialogNegativeClick();
    }
}