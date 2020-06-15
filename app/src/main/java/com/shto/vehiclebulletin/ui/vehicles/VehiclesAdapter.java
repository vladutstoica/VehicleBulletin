package com.shto.vehiclebulletin.ui.vehicles;
/*
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.shto.vehiclebulletin.R;

public class VehiclesAdapter extends RecyclerView.Adapter {

    // variable for cardView data
    private String[] mLicencePlate;
    private String[] mVehicleModel;
    private String[] mRenew;
    private String[] mTotalCost;
    private int[] mVehicleLogo;

    // Provide a reference to the views used in the recycler view - what data the adapter should work with
    public static class ViewHolder extends RecyclerView.ViewHolder{
        // Our recycler view needs to display CardViews, so we specify that out ViewHolder contains CardViews
        // If you want to display another type of data in the recycler view, you define it here
        private CardView mCardView;

        // Define the ViewHolder
        public ViewHolder(@NonNull CardView itemView) {
            // Call the constructor
            super(itemView);

            mCardView = itemView;
        }
    }

    // pass the data to the adapter using constructor
    public VehiclesAdapter(String[] mLicencePlate, String[] mVehicleModel, String[] mRenew, String[] mTotalCost, int[] mVehicleLogo) {
        this.mLicencePlate = mLicencePlate;
        this.mVehicleModel = mVehicleModel;
        this.mRenew = mRenew;
        this.mTotalCost = mTotalCost;
        this.mVehicleLogo = mVehicleLogo;
    }

    // Used to create the views
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view
        // Specify what layout to use for the contents of the ViewHolder
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_vehicles, parent, false);
        
        return new ViewHolder(cardView);
    }

    // Used to set the values inside the views
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Set the values inside the given view
        CardView zafuk = (CardView) holder.itemView;

        TextView mLicencePlateTextView = zafuk.findViewById(R.id.licence_plate);
        mLicencePlateTextView.setText(mLicencePlate[position]);
        TextView mVehicleModelTextView = zafuk.findViewById(R.id.vehicle_model);
        mVehicleModelTextView.setText(mVehicleModel[position]);
        TextView mRenewTextView = zafuk.findViewById(R.id.renew);
        mRenewTextView.setText(mRenew[position]);
        TextView mTotalCostTextView = zafuk.findViewById(R.id.total_cost);
        mTotalCostTextView.setText(mTotalCost[position]);
        ImageView mVehicleLogoImageView = zafuk.findViewById(R.id.vehicle_logo);
        mVehicleLogoImageView.setImageDrawable(zafuk.getResources().getDrawable(mVehicleLogo[position]));
    }

    // Used to return the number of items in the data set
    @Override
    public int getItemCount() {
        // Return the number of items in the data set
        // The number of the text1 array equals the number of data items in the recycler view
        return mLicencePlate.length;
    }
}
*/

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

public class VehiclesAdapter extends RecyclerView.Adapter<VehiclesAdapter.MyViewHolder> {

    private ArrayList<Vehicles> dataSet;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mLicencePlateTextView;
        private TextView mVehicleModelTextView;
        private TextView mRenewTextView;
        private TextView mTotalCostTextView;
        private ImageView mVehicleLogoId;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            this.mLicencePlateTextView = itemView.findViewById(R.id.licence_plate);
            this.mVehicleModelTextView = itemView.findViewById(R.id.vehicle_model);
            this.mRenewTextView = itemView.findViewById(R.id.renew);
            this.mTotalCostTextView = itemView.findViewById(R.id.total_cost);
            this.mVehicleLogoId = itemView.findViewById(R.id.vehicle_logo);
        }
    }

    public VehiclesAdapter(ArrayList<Vehicles> dataSet) {
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_vehicles, parent, false);

        return new MyViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.mLicencePlateTextView.setText(dataSet.get(position).getLicencePlate());
        holder.mVehicleModelTextView.setText(dataSet.get(position).getVehicleModel());
        holder.mRenewTextView.setText(dataSet.get(position).getRenew());
        holder.mTotalCostTextView.setText(dataSet.get(position).getTotalCost());
        holder.mVehicleLogoId.setImageResource(dataSet.get(position).getVehicleLogoId());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}