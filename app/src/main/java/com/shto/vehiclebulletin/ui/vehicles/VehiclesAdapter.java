package com.shto.vehiclebulletin.ui.vehicles;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.shto.vehiclebulletin.R;

import java.util.ArrayList;

//Every adapter has three primary methods:
// onCreateViewHolder to inflate the item layout and create the holder,
// onBindViewHolder to set the view attributes based on the data and
// getItemCount to determine the number of items
public class VehiclesAdapter extends RecyclerView.Adapter<VehiclesAdapter.MyViewHolder> {

    // Store a member variable for the vehicles data
    private ArrayList<Vehicles> dataSet;

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Set item views based on your views and data model
        holder.mLicencePlateTextView.setText(dataSet.get(position).getLicencePlate());
        holder.mVehicleModelTextView.setText(dataSet.get(position).getVehicleModel());
        holder.mRenewTextView.setText(dataSet.get(position).getRenew());
        holder.mTotalCostTextView.setText(dataSet.get(position).getTotalCost());
        holder.mBrandLogoId.setImageResource(dataSet.get(position).getBrandLogoId());
    }

    // Pass the data array into the constructor
    public VehiclesAdapter(ArrayList<Vehicles> dataSet) {
        this.dataSet = dataSet;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the custom layout
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_vehicles, parent, false);
        // Return a new holder instance
        return new MyViewHolder(cardView);
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        private TextView mLicencePlateTextView;
        private TextView mVehicleModelTextView;
        private TextView mRenewTextView;
        private TextView mTotalCostTextView;
        private ImageView mBrandLogoId;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public MyViewHolder(@NonNull View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            this.mLicencePlateTextView = itemView.findViewById(R.id.licence_plate);
            this.mVehicleModelTextView = itemView.findViewById(R.id.vehicle_model);
            this.mRenewTextView = itemView.findViewById(R.id.renew);
            this.mTotalCostTextView = itemView.findViewById(R.id.total_cost);
            this.mBrandLogoId = itemView.findViewById(R.id.brand_logo);
        }
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}