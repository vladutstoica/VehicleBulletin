package com.shto.vehiclebulletin.ui.vehicles.pojos;

import java.util.ArrayList;

public class VehicleDocuments {

    public static ArrayList<VehicleDocuments> sVehicleDocuments = new ArrayList<>();
    private String refId;
    private String name;
    private String price;
    private String buy_date;
    private String exp_date;

//    public VehicleDocuments() {
//        // Default constructor required for calls to DataSnapshot.getValue(User.class)
//    }

    public VehicleDocuments(
            String refId,
            String name,
            String price,
            String buy_date,
            String exp_date
    ) {
        this.refId = refId;
        this.name = name;
        this.price = price;
        this.buy_date = buy_date;
        this.exp_date = exp_date;
    }

    public String getRefId() {
        return refId;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getBuy_date() {
        return buy_date;
    }

    public String getExp_date() {
        return exp_date;
    }
}
