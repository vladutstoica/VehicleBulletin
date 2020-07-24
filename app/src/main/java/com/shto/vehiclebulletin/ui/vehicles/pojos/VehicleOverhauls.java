package com.shto.vehiclebulletin.ui.vehicles.pojos;

import java.util.ArrayList;

public class VehicleOverhauls {

    public static ArrayList<VehicleOverhauls> sVehicleOverhauls = new ArrayList<>();
    private String refId;
    private String name;
    private String price;
    private String buyDate;
    private String serviceName;

    public VehicleOverhauls() {

    }

    public VehicleOverhauls(
            String refId,
            String name,
            String price,
            String buyDate,
            String serviceName
    ) {
        this.refId = refId;
        this.name = name;
        this.price = price;
        this.buyDate = buyDate;
        this.serviceName = serviceName;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public String getServiceName() {
        return serviceName;
    }
}
