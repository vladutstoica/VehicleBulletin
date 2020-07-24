package com.shto.vehiclebulletin.ui.vehicles.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.shto.vehiclebulletin.R;
import com.shto.vehiclebulletin.ui.vehicles.VehiclesViewModel;
import com.shto.vehiclebulletin.ui.vehicles.pojos.VehicleOverhauls;

public class AddOverhaulsDialogFragment extends DialogFragment {
    private static final String TAG = "overhauls";

    EditText mOhName;
    EditText mOhPrice;
    EditText mOhBuyDate;
    EditText mOhServiceName;

    Button addButton;
    Button cancelButton;

//    private FirebaseAuth mAuth;
//    private FirebaseFirestore db;

    private VehiclesViewModel mVehiclesViewModel;
    final int[] clickPosition = new int[1];

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mAuth = FirebaseAuth.getInstance();
//        db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.dialog_overhauls_add, null);

        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(view);
        final AlertDialog createAlertDialog = alertDialogBuilder.create();

        // Create DateRange Picker Dialog
        final MaterialDatePicker.Builder<Long> dateRangePicker = MaterialDatePicker.Builder.datePicker();
        dateRangePicker.setTitleText(R.string.btn_documents_dates);
        final MaterialDatePicker<Long> picker = dateRangePicker.build();

        mOhName = view.findViewById(R.id.input_overhauls_name);
        mOhPrice = view.findViewById(R.id.input_overhauls_price);
        mOhServiceName = view.findViewById(R.id.input_overhauls_servicename);
        mOhBuyDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.show(getParentFragmentManager(), picker.toString());
                picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        mOhBuyDate.setText(picker.getHeaderText());
                    }
                });
            }
        });

        addButton = view.findViewById(R.id.btn_overhauls_add);
        cancelButton = view.findViewById(R.id.btn_overhauls_cancel);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateDialog()) {
                    return;
                }
                addOverhaulsProcess();
                createAlertDialog.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddOverhaulsDialogFragment.this.requireDialog().cancel();
            }
        });

        return createAlertDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.dialog_overhauls_add, container, false);

        mVehiclesViewModel = new ViewModelProvider(requireActivity()).get(VehiclesViewModel.class);

        mVehiclesViewModel.getClickPosition()
                .observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        clickPosition[0] = Integer.parseInt(s);
                    }
                });

        return view;
    }

    private boolean validateDialog() {
        boolean valid = true;

        String name = mOhName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            mOhName.setError(getResources().getString(R.string.required));
            valid = false;
        } else {
            mOhName.setError(null);
        }

        String price = mOhPrice.getText().toString();
        if (TextUtils.isEmpty(price)) {
            mOhPrice.setError(getResources().getString(R.string.required));
            valid = false;
        } else {
            mOhPrice.setError(null);
        }

        String serviceName = mOhServiceName.getText().toString();
        if (TextUtils.isEmpty(serviceName)) {
            mOhServiceName.setError(getResources().getString(R.string.required));
            valid = false;
        } else {
            mOhServiceName.setError(null);
        }

        return valid;
    }

    private void addOverhaulsProcess() {
//        FirebaseUser user = mAuth.getCurrentUser();
//
//        updateDB(user);

        VehicleOverhauls.sVehicleOverhauls.add(new VehicleOverhauls(
                "12ab",
                "Placute frana",
                "300",
                "20 iun. 2020",
                "Costel SRL"
        ));
    }

}
