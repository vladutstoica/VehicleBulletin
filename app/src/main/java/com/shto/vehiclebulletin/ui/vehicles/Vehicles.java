package com.shto.vehiclebulletin.ui.vehicles;

import android.widget.ImageView;

import com.shto.vehiclebulletin.R;

public class Vehicles {
    private String mLicencePlate;
    private String mVehicleModel;
    private String mRenew;
    private String mTotalCost;
    private int mVehicleLogo;

    public static final Vehicles[] vehicles = {
            new Vehicles("PH-20-SVM", "MAZDA 3 - 2005", "test", "2300 LEI", R.drawable.ic_dashboard_black_24dp),
            new Vehicles("PH-20-SVM", "MAZDA 3 - 2005", "test", "2300 LEI", R.drawable.ic_home_black_24dp),
            new Vehicles("PH-20-SVM", "MAZDA 3 - 2005", "test", "2300 LEI", R.drawable.ic_dashboard_black_24dp),
            new Vehicles("PH-20-SVM", "MAZDA 3 - 2005", "test", "2300 LEI", R.drawable.ic_dashboard_black_24dp),
            new Vehicles("PH-20-SVM", "MAZDA 3 - 2005", "test", "2300 LEI", R.drawable.ic_dashboard_black_24dp),
            new Vehicles("PH-20-SVM", "MAZDA 3 - 2005", "test", "2300 LEI", R.drawable.ic_dashboard_black_24dp),
            new Vehicles("PH-20-SVM", "MAZDA 3 - 2005", "test", "2300 LEI", R.drawable.ic_dashboard_black_24dp),
            new Vehicles("PH-20-SVM", "MAZDA 3 - 2005", "test", "2300 LEI", R.drawable.ic_dashboard_black_24dp),
            new Vehicles("PH-20-SVM", "MAZDA 3 - 2005", "test", "2300 LEI", R.drawable.ic_dashboard_black_24dp),
            new Vehicles("PH-20-SVM", "MAZDA 3 - 2005", "test", "2300 LEI", R.drawable.ic_dashboard_black_24dp),
            new Vehicles("PH-20-SVM", "MAZDA 3 - 2005", "test", "2300 LEI", R.drawable.ic_dashboard_black_24dp),
            new Vehicles("PH-20-SVM", "MAZDA 3 - 2005", "test", "2300 LEI", R.drawable.ic_dashboard_black_24dp),
    };

    private Vehicles(String mLicencePlate, String mVehicleModel, String mRenew, String mTotalCost, int mVehicleLogo) {
        this.mLicencePlate = mLicencePlate;
        this.mVehicleModel = mVehicleModel;
        this.mRenew = mRenew;
        this.mTotalCost = mTotalCost;
        this.mVehicleLogo = mVehicleLogo;
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

    public int getVehicleLogo() {
        return mVehicleLogo;
    }
}
