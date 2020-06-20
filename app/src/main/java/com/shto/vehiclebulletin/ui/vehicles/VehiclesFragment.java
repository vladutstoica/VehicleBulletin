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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shto.vehiclebulletin.R;


public class VehiclesFragment extends Fragment {
    private static final String TAG = "vehicles";
    // Use this is any bug hit: private RecyclerView.Adapter mAdapter;
    private VehiclesAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        mAdapter = new VehiclesAdapter(Vehicles.mVehiclesData);
        recyclerView.setAdapter(mAdapter);

        ExtendedFloatingActionButton fab = view.findViewById(R.id.extended_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("FAB", "working fab");
                Toast.makeText(getContext(),"working ", Toast.LENGTH_SHORT).show();
                // testxr
                int curSize = mAdapter.getItemCount();

                Vehicles.mVehiclesData.add(new Vehicles("PH-20-SVM", "MAZDA 3 - 2006", "last act", "4 000 EURO", R.drawable.ic_home_black_24dp));
                Toast.makeText(getContext(), "array size " + Vehicles.mVehiclesData.size(), Toast.LENGTH_SHORT).show();

                // testxr
                mAdapter.notifyItemInserted(curSize + 1);
                //mAdapter.notifyDataSetChanged();
            }
        });

        // TODO testing purpose Realtime databases
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("vehicles").child(user.getUid()).child("id1");

        // Read from the database
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Vehicles vehicles = dataSnapshot.getValue(Vehicles.class);
                Vehicles.mVehiclesData.add(vehicles);
                mAdapter.notifyItemInserted(mAdapter.getItemCount() + 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }
}