package com.shto.vehiclebulletin.ui.vehicles;

import com.shto.vehiclebulletin.R;

import java.util.ArrayList;

public class VehiclesOverview {
    public static ArrayList<VehiclesOverview> mVehiclesOverviewData = new ArrayList<>();
    private String refId;
    private String mLicencePlate;
    private String mVehicleModel;
    private String mRenew = "No documents";
    private String mTotalCost = "No data";
    private int mBrandLogoId = R.drawable.ic_launcher_foreground;

    public VehiclesOverview() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public VehiclesOverview(
            String refId,
            String mLicencePlate,
            String mVehicleModel,
            String mRenew,
            String mTotalCost,
            int mVehicleLogo
    ) {
        this.refId = refId;
        this.mLicencePlate = mLicencePlate;
        this.mVehicleModel = mVehicleModel;
        this.mRenew = mRenew;
        this.mTotalCost = mTotalCost;
        this.mBrandLogoId = mVehicleLogo;
    }

    public String getRefId() {
        return refId;
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