package com.shto.vehiclebulletin.ui.vehicles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shto.vehiclebulletin.R;
import com.shto.vehiclebulletin.ui.vehicles.adapters.OverhaulsAdapter;
import com.shto.vehiclebulletin.ui.vehicles.dialog.AddOverhaulsDialogFragment;
import com.shto.vehiclebulletin.ui.vehicles.pojos.VehicleOverhauls;

public class OverhaulsFragment extends Fragment {

    private static final String TAG = "overhauls";
    private OverhaulsAdapter mOverhaulsAdapter;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    final int[] clickPosition = new int[1];

    public OverhaulsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        VehiclesViewModel vehiclesViewModel = new ViewModelProvider(requireActivity()).get(VehiclesViewModel.class);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overhauls, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_overhauls);
        recyclerView.setHasFixedSize(true);

        // Use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Set the adapter
        mOverhaulsAdapter = new OverhaulsAdapter(VehicleOverhauls.sVehicleOverhauls);
        recyclerView.setAdapter(mOverhaulsAdapter);

        vehiclesViewModel.getClickPosition()
                .observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        clickPosition[0] = Integer.parseInt(s);
                    }
                });

        for (int i = 0; i <= 2; i++) {
            VehicleOverhauls.sVehicleOverhauls.add(new VehicleOverhauls(
                    i + "abc",
                    "Overhaul " + i,
                    i + "99",
                    i + " apr. 202" + i,
                    i + " Name SRL"
            ));
        }

//        final FirebaseUser user = mAuth.getCurrentUser();
//        final String vehicleReference = VehicleGeneral.sVehicleGenerals.get(clickPosition[0]).getRefId();
//        if (user != null) {
//            db.collection("users")
//                    .document(user.getUid())
//                    .collection("vehicles")
//                    .document(vehicleReference)
//                    .collection("documents")
//                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                        @Override
//                        public void onEvent(@Nullable QuerySnapshot value,
//                                            @Nullable FirebaseFirestoreException e) {
//                            if (e != null) {
//                                Log.w(TAG, "Listen failed.", e);
//                                return;
//                            }
//
//                            VehicleDocuments.sVehicleDocuments.clear();
//
//                            if (value != null) {
//                                for (QueryDocumentSnapshot doc : value) {
//                                    VehicleDocuments.sVehicleDocuments
//                                            .add(doc.toObject(VehicleDocuments.class));
//                                    Log.d(TAG, "retrieveDB: "
//                                            + doc.getData());
//                                }
//                            }
//
//                            mDocumentsAdapter.notifyDataSetChanged();
//                        }
//                    });
//        }

        ExtendedFloatingActionButton fab = view.findViewById(R.id.fab_overhauls);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When you want to show your dialog, create an instance of your DialogFragment
                // and call show(), passing the FragmentManager and a tag name for the dialog fragment.
                DialogFragment addOverhaulsDialog = new AddOverhaulsDialogFragment();
                addOverhaulsDialog.show(getParentFragmentManager(), "ADD OVERHAULS DIALOG");

                mOverhaulsAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }
}