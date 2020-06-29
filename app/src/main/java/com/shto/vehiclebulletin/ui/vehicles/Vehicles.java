package com.shto.vehiclebulletin.ui.vehicles;

import java.util.ArrayList;

public class Vehicles {
    public static ArrayList<Vehicles> mVehiclesData = new ArrayList<>();
    private String mLicencePlate;
    private String mVehicleModel;
    private String mRenew;
    private String mTotalCost;
    private int mBrandLogoId;

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
        this.mBrandLogoId = mVehicleLogo;
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

    public int getBrandLogoId() {
        return mBrandLogoId;
    }
}