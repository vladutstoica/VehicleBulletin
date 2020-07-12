package com.shto.vehiclebulletin.ui.vehicles;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.shto.vehiclebulletin.R;
import com.shto.vehiclebulletin.ui.vehicles.dialog.AddVehicleDialogFragment;


public class VehiclesFragment extends Fragment implements AddVehicleDialogFragment.NoticeDialogListener {
    private static final String TAG = "vehicles";
    // Use this is any bug hit: private RecyclerView.Adapter mAdapter;
    private VehiclesAdapter mAdapter;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        /* This callback will only be called when LoginFragment is at least Started.
        https://developer.android.com/guide/navigation/navigation-custom-back
        Prevent the back button to navigate to login page (loginFragment)*/
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Toast.makeText(getContext(), "backbutton pressed.", Toast.LENGTH_SHORT).show();
                requireActivity().finish();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_vehicles, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.vehicles_recycler_view);
        // TODO: make below like work
        //mRecyclerView = inflater.inflate(R.layout.fragment_vehicles, container, false);

        //Vehicles.mVehiclesData.add(new Vehicles("ActivityMain","asd","asd","ad", R.drawable.ic_home_black_24dp));

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // Use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Specify an adapter
        mAdapter = new VehiclesAdapter(VehiclesOverview.mVehiclesOverviewData);
        recyclerView.setAdapter(mAdapter);

        // TODO: add data to specific array
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            String userId = user.getUid();

            db.collection("users").document(userId).collection("vehicles")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && VehiclesOverview.mVehiclesOverviewData.size() == 0) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    Log.d(TAG, document.getId() + " => " + document.getData().get("color"));

                                    VehiclesOverview data = new VehiclesOverview();
                                    String dataRenew = data.getRenew();
                                    String dataCost = data.getTotalCost();
                                    int dataLogo = data.getBrandLogoId();

                                    VehiclesOverview.mVehiclesOverviewData.add(
                                            new VehiclesOverview(
                                                    document.getData().get("refId").toString(),
                                                    document.getData().get("licence").toString(),
                                                    document.getData().get("brand") + " " + document.getData().get("model"),
                                                    dataRenew,
                                                    dataCost,
                                                    dataLogo
                                            )
                                    );
                                }

                                //mAdapter.notifyDataSetChanged();
                                mAdapter.notifyItemRangeInserted(1, mAdapter.getItemCount());
                                Log.d(TAG, "DATABASE read - " +
                                        "mAdapter notify - " +
                                        "adapter size : " + mAdapter.getItemCount());
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }


        ExtendedFloatingActionButton fab = view.findViewById(R.id.extended_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When you want to show your dialog, create an instance of your DialogFragment
                // and call show(), passing the FragmentManager and a tag name for the dialog fragment.
                DialogFragment addVehicleDialog = new AddVehicleDialogFragment();
                addVehicleDialog.show(getParentFragmentManager(), "Add Vehicle Dialog");
                addVehicleDialog.setTargetFragment(VehiclesFragment.this, 0);

            }
        });

        return view;
    }

    @Override
    public void onDialogPositiveClick() {
        // User touched the dialog's positive button
        mAdapter.notifyItemInserted(mAdapter.getItemCount());
        Toast.makeText(getContext(), "listener triggered", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "listener was triggered + adapter size: " + mAdapter.getItemCount());
    }

    @Override
    public void onDialogNegativeClick() {
        mAdapter.notifyItemInserted(mAdapter.getItemCount());
        Log.d(TAG, "NEGATIVE listener was triggered + adapter size: " + mAdapter.getItemCount());
    }
}