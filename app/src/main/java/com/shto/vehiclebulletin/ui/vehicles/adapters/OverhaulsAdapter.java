package com.shto.vehiclebulletin.ui.vehicles.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.shto.vehiclebulletin.R;
import com.shto.vehiclebulletin.ui.vehicles.pojos.VehicleOverhauls;

import java.util.ArrayList;

/* Every adapter has three primary methods:
 * onCreateViewHolder to inflate the item layout and create the holder,
 * onBindViewHolder to set the view attributes based on the data and
 * getItemCount to determine the number of items
 */
public class OverhaulsAdapter extends RecyclerView.Adapter<OverhaulsAdapter.OverhaulsViewHolder> {

    // Store a member variable for the vehicles data
    private ArrayList<VehicleOverhauls> dataSet;

    // Pass the data array into the constructor
    public OverhaulsAdapter(ArrayList<VehicleOverhauls> dataSet) {
        this.dataSet = dataSet;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public OverhaulsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the custom layout
        MaterialCardView cardView = (MaterialCardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_overhauls, parent, false);
        // Return a new holder instance
        return new OverhaulsViewHolder(cardView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull OverhaulsViewHolder holder, int position) {
        // Set item views based on your views and data model
        holder.name.setText(dataSet.get(position).getName());
        holder.price.setText(dataSet.get(position).getPrice());
        holder.buyDate.setText(dataSet.get(position).getBuyDate());
        holder.serviceName.setText(dataSet.get(position).getServiceName());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class OverhaulsViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        private TextView name;
        private TextView price;
        private TextView buyDate;
        private TextView serviceName;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public OverhaulsViewHolder(@NonNull View itemView) {
            super(itemView);

            this.name = itemView.findViewById(R.id.text_overhauls_name);
            this.price = itemView.findViewById(R.id.text_overhauls_price);
            this.buyDate = itemView.findViewById(R.id.text_overhauls_buydate);
            this.serviceName = itemView.findViewById(R.id.text_overhauls_servicename);
        }
    }
}
