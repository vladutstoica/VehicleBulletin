package com.shto.vehiclebulletin.ui.vehicles;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.shto.vehiclebulletin.R;

public class VehiclesAdapter extends RecyclerView.Adapter {

    // variable who hold vehicles data
    private String[] text1;
    private String[] text2;

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
    public VehiclesAdapter(String[] text1, String[] text2) {
        this.text1 = text1;
        this.text2 = text2;
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

        TextView textView = zafuk.findViewById(R.id.licence_plate);
        textView.setText(text1[position]);
        TextView textView1 = zafuk.findViewById(R.id.vehicle_model);
        textView1.setText(text2[position]);

    }

    // Used to return the number of items in the data set
    @Override
    public int getItemCount() {
        // Return the number of items in the data set
        // The number of the text1 array equals the number of data items in the recycler view
        return text1.length;
    }
}
