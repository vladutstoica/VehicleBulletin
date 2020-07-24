package com.shto.vehiclebulletin.ui.vehicles.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shto.vehiclebulletin.R;
import com.shto.vehiclebulletin.ui.vehicles.VehiclesViewModel;
import com.shto.vehiclebulletin.ui.vehicles.pojos.VehicleDocuments;
import com.shto.vehiclebulletin.ui.vehicles.pojos.VehicleGeneral;

public class AddDocumentsDialogFragment extends DialogFragment {

    private static final String TAG = "document";
    EditText mDocName;
    EditText mDocPrice;
    Button mDocValidDates;

    Button addButton;
    Button cancelButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private VehiclesViewModel mVehiclesViewModel;
    final int[] clickPosition = new int[1];

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        mVehiclesViewModel =
//                new ViewModelProvider(requireActivity()).get(VehiclesViewModel.class);

        View view = getLayoutInflater().inflate(R.layout.dialog_documents_add, null);

        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(view);
        final AlertDialog createAlertDialog = alertDialogBuilder.create();

        // Create DateRange Picker Dialog
        final MaterialDatePicker.Builder<Pair<Long, Long>> dateRangePicker = MaterialDatePicker.Builder.dateRangePicker();
        dateRangePicker.setTitleText(R.string.btn_documents_dates);
        final MaterialDatePicker<Pair<Long, Long>> picker = dateRangePicker.build();

        // Document's dialog member variables
        mDocName = view.findViewById(R.id.input_documents_name);
        mDocPrice = view.findViewById(R.id.input_documents_price);
        mDocValidDates = view.findViewById(R.id.btn_documents_daterange);

        // DateRange Picker Dialog listener
        mDocValidDates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.show(getParentFragmentManager(), picker.toString());
                picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                    @Override
                    public void onPositiveButtonClick(Pair<Long, Long> selection) {
                        mDocValidDates.setText(picker.getHeaderText());
                    }
                });
            }
        });

//        mVehiclesViewModel.getClickPosition()
//                .observe(getViewLifecycleOwner(), new Observer<String>() {
//                    @Override
//                    public void onChanged(String s) {
//                        clickPosition[0] = Integer.parseInt(s);
//                    }
//                });

        addButton = view.findViewById(R.id.btn_documents_add);
        cancelButton = view.findViewById(R.id.btn_documents_cancel);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateDialog()) {
                    return;
                }
                addDocumentProcess();
                createAlertDialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDocumentsDialogFragment.this.requireDialog().cancel();
            }
        });


        return createAlertDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.dialog_documents_add, container, false);

        mVehiclesViewModel =
                new ViewModelProvider(requireActivity()).get(VehiclesViewModel.class);

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

        String name = mDocName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            mDocName.setError("Required.");
            valid = false;
        } else {
            mDocName.setError(null);
        }

        String price = mDocPrice.getText().toString();
        if (TextUtils.isEmpty(price)) {
            mDocPrice.setError("Required.");
            valid = false;
        } else {
            mDocPrice.setError(null);
        }

        // TODO: check date to be picked

        return valid;
    }

    private void addDocumentProcess() {
        FirebaseUser user = mAuth.getCurrentUser();

        updateDB(user);
    }

    private void updateDB(FirebaseUser user) {
        String refId;

        if (user != null) {
            refId = db.collection("users")
                    .document(user.getUid())
                    .collection("vehicles")
                    .document(VehicleGeneral.sVehicleGenerals.get(clickPosition[0]).getRefId())
                    .collection("documents")
                    .document()
                    .getId();

            String validDates = mDocValidDates.getText().toString();
            String buyDate = validDates.substring(0 , validDates.indexOf("–")).trim();
            String expDate = validDates.substring(validDates.indexOf("–") + 1).trim();

            final VehicleDocuments vehicleDocuments = new VehicleDocuments(
                    refId,
                    mDocName.getText().toString(),
                    mDocPrice.getText().toString(),
                    buyDate,
                    expDate
            );

            db.collection("users")
                    .document(user.getUid())
                    .collection("vehicles")
                    .document(VehicleGeneral.sVehicleGenerals.get(clickPosition[0]).getRefId())
                    .collection("documents")
                    .document(refId)
                    .set(vehicleDocuments)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.w(TAG, "DocumentSnapshot successfully written!");
                            updateLocal(vehicleDocuments);
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

    // TODO watch if needed anymore
    private void updateLocal(VehicleDocuments vehicleDocuments) {
        VehicleDocuments.sVehicleDocuments.add(vehicleDocuments);
    }
}
