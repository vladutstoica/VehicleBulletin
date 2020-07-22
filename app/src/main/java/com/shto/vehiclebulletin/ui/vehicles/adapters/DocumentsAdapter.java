package com.shto.vehiclebulletin.ui.vehicles.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.shto.vehiclebulletin.R;
import com.shto.vehiclebulletin.ui.vehicles.pojos.VehicleDocuments;

import java.util.ArrayList;

/* Every adapter has three primary methods:
 * onCreateViewHolder to inflate the item layout and create the holder,
 * onBindViewHolder to set the view attributes based on the data and
 * getItemCount to determine the number of items
 */
public class DocumentsAdapter extends RecyclerView.Adapter<DocumentsAdapter.DocumentsViewHolder> {

    // Store a member variable for the vehicles data
    private ArrayList<VehicleDocuments> dataSet;

    public DocumentsAdapter(ArrayList<VehicleDocuments> dataSet) {
        this.dataSet = dataSet;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public DocumentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the custom layout
        MaterialCardView cardView = (MaterialCardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_documents, parent, false);
        // Return a new holder instance
        return new DocumentsViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentsViewHolder holder, int position) {
        // Set item views based on your views and data model
        holder.mName.setText(dataSet.get(position).getName());
        holder.mPrice.setText(dataSet.get(position).getPrice());
        holder.mBuyDate.setText(dataSet.get(position).getBuy_date());
        holder.mExpirationDate.setText(dataSet.get(position).getExp_date());
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class DocumentsViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        private TextView mName;
        private TextView mPrice;
        private TextView mBuyDate;
        private TextView mExpirationDate;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public DocumentsViewHolder(@NonNull View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            this.mName = itemView.findViewById(R.id.text_documents_name);
            this.mPrice = itemView.findViewById(R.id.text_documents_price);
            this.mBuyDate = itemView.findViewById(R.id.text_documents_buydate);
            this.mExpirationDate = itemView.findViewById(R.id.text_documents_expirationdate);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
