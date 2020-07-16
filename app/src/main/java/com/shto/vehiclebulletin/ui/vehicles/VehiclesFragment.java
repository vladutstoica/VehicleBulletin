package com.shto.vehiclebulletin.ui.vehicles;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.shto.vehiclebulletin.R;
import com.shto.vehiclebulletin.ui.vehicles.adapters.VehiclesAdapter;
import com.shto.vehiclebulletin.ui.vehicles.dialog.AddVehicleDialogFragment;


public class VehiclesFragment extends Fragment implements VehiclesAdapter.OnVehicleListener {
    private static final String TAG = "vehicles";

    private VehiclesAdapter mAdapter;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

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
        // mRecyclerView = inflater.inflate(R.layout.fragment_vehicles, container, false);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // Use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Specify an adapter
        mAdapter = new VehiclesAdapter(VehiclesOverview.mVehiclesOverviewData, this);
        recyclerView.setAdapter(mAdapter);

        final FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            db.collection("users").document(userId).collection("vehicles")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value,
                                            @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                Log.w(TAG, "Listen failed.", e);
                                return;
                            }

                            VehiclesOverview.mVehiclesOverviewData.clear();
                            VehicleGeneral.mVehiclesGeneralData.clear();

                            if (value != null) {
                                for (QueryDocumentSnapshot doc : value) {
                                    retrieveDB(user, doc);
                                }
                            }
                            mAdapter.notifyDataSetChanged();
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
                addVehicleDialog.show(getParentFragmentManager(), "ADD DIALOG");
            }
        });

        return view;
    }

    private void retrieveDB(FirebaseUser user, QueryDocumentSnapshot doc) {
        if (user != null) {
            VehiclesOverview data = new VehiclesOverview();
            String dataRenew = data.getRenew();
            String dataCost = data.getTotalCost();
            int dataLogo = data.getBrandLogoId();

            VehiclesOverview.mVehiclesOverviewData.add(
                    new VehiclesOverview(
                            (String) doc.getData().get("refId"),
                            (String) doc.getData().get("licence"),
                            doc.getData().get("brand") + " " + doc.getData().get("model"),
                            dataRenew,
                            dataCost,
                            dataLogo
                    )
            );

            VehicleGeneral.mVehiclesGeneralData
                    .add(doc.toObject(VehicleGeneral.class));
            Log.d(TAG, "retrieveDB: "
                    + VehicleGeneral.mVehiclesGeneralData.get(0).getBrand());
        }
    }

    @Override
    public void onVehicleClick(int position) {
        Log.d(TAG, "onVehicleClick: click registered on position " + position);
        Navigation.findNavController(requireView())
                .navigate(R.id.action_navigation_vehicles_to_detailsFragment);
    }

    @Override
    public void onVehicleLongClick(int position) {
        Log.d(TAG, "onVehicleClick: long click registered on position " + position);
    }
}