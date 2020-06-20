package com.shto.vehiclebulletin.ui.vehicles;

import java.util.ArrayList;

public class Vehicles {
    public static ArrayList<Vehicles> mVehiclesData = new ArrayList<>();
    private String mLicencePlate;
    private String mVehicleModel;
    private String mRenew;
    private String mTotalCost;
    private int mVehicleLogoId;

    public Vehicles() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Vehicles(String mLicencePlate,
                    String mVehicleModel,
                    String mRenew,
                    String mTotalCost,
                    int mVehicleLogo) {
        this.mLicencePlate = mLicencePlate;
        this.mVehicleModel = mVehicleModel;
        this.mRenew = mRenew;
        this.mTotalCost = mTotalCost;
        this.mVehicleLogoId = mVehicleLogo;
    }

    public String getLicencePlate() {
        return mLicencePlate;
    }

    public String getVehicleModel() {
        return mVehicleModel;
    }

    public String getRenew() {
        return mRenew;
    }

    public String getTotalCost() {
        return mTotalCost;
    }

    public int getVehicleLogoId() {
        return mVehicleLogoId;
    }

    //TODO testing database purpose

    public void setLicencePlate(String licencePlate) {
        mLicencePlate = licencePlate;
    }

    public void setVehicleModel(String vehicleModel) {
        mVehicleModel = vehicleModel;
    }

    public void setRenew(String renew) {
        mRenew = renew;
    }

    public void setTotalCost(String totalCost) {
        mTotalCost = totalCost;
    }

    public void setVehicleLogoId(int vehicleLogoId) {
        mVehicleLogoId = vehicleLogoId;
    }
}