package com.shto.vehiclebulletin.ui.vehicles;

public class VehicleGeneral {

    private String refId;
    private String type;
    private String brand;
    private String model;
    private String date;
    private String fuel;
    private String color;
    private String licence;

    public VehicleGeneral() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public VehicleGeneral(
            String refId,
            String type,
            String brand,
            String model,
            String date,
            String fuel,
            String color,
            String licence
    ) {
        this.refId = refId;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.date = date;
        this.fuel = fuel;
        this.color = color;
        this.licence = licence;
    }

    public String getRefId() {
        return refId;
    }

    public String getType() {
        return type;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getDate() {
        return date;
    }

    public String getFuel() {
        return fuel;
    }

    public String getColor() {
        return color;
    }

    public String getLicence() {
        return licence;
    }
}
