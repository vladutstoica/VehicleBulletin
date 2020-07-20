package com.shto.vehiclebulletin.ui.vehicles.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.shto.vehiclebulletin.R;
import com.shto.vehiclebulletin.ui.vehicles.VehiclesOverview;

import java.util.ArrayList;

/* Every adapter has three primary methods:
 * onCreateViewHolder to inflate the item layout and create the holder,
 * onBindViewHolder to set the view attributes based on the data and
 * getItemCount to determine the number of items
 */
public class VehiclesAdapter extends RecyclerView.Adapter<VehiclesAdapter.MyViewHolder> {

    // Store a member variable for the vehicles data
    private ArrayList<VehiclesOverview> dataSet;

    private OnVehicleListener mOnVehicleListener;

    // Pass the data array into the constructor
    public VehiclesAdapter(ArrayList<VehiclesOverview> dataSet, OnVehicleListener onVehicleListener) {
        this.dataSet = dataSet;
        this.mOnVehicleListener = onVehicleListener;
    }

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

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the custom layout
        MaterialCardView cardView = (MaterialCardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_vehicles, parent, false);
        // Return a new holder instance
        return new MyViewHolder(cardView, mOnVehicleListener);
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        private TextView mLicencePlateTextView;
        private TextView mVehicleModelTextView;
        private TextView mRenewTextView;
        private TextView mTotalCostTextView;
        private ImageView mBrandLogoId;

        OnVehicleListener onVehicleListener;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public MyViewHolder(@NonNull View itemView, OnVehicleListener onVehicleListener) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            this.mLicencePlateTextView = itemView.findViewById(R.id.licence_plate);
            this.mVehicleModelTextView = itemView.findViewById(R.id.vehicle_model);
            this.mRenewTextView = itemView.findViewById(R.id.renew);
            this.mTotalCostTextView = itemView.findViewById(R.id.total_cost);
            this.mBrandLogoId = itemView.findViewById(R.id.brand_logo);

            this.onVehicleListener = onVehicleListener;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onVehicleListener.onVehicleClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            onVehicleListener.onVehicleLongClick(getAdapterPosition());
            return true;
        }
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public interface OnVehicleListener {
        void onVehicleClick(int position);
        void onVehicleLongClick(int position);
    }
}