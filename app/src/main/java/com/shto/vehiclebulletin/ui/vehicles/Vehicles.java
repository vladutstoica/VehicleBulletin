package com.shto.vehiclebulletin.ui.vehicles;
/*
import java.util.ArrayList;

public class Vehicles {
    public static ArrayList<Vehicles> test = new ArrayList<Vehicles>();
    private String mLicencePlate;
    private String mVehicleModel;
    private String mRenew;
    private String mTotalCost;
    private int mVehicleLogo;

    //        test.add(new Vehicles("asd","asd","asd","ad",R.drawable.ic_home_black_24dp));
//    public static final Vehicles[] vehicles = {
//            new Vehicles("PH-20-SVM", "MAZDA 3 - 2005", "test", "2300 LEI", R.drawable.ic_dashboard_black_24dp),
//            new Vehicles("PH-20-SVM", "MAZDA 3 - 2005", "test", "2300 LEI", R.drawable.ic_home_black_24dp),
//            new Vehicles("PH-20-SVM", "MAZDA 3 - 2005", "test", "2300 LEI", R.drawable.ic_dashboard_black_24dp),
//            new Vehicles("PH-20-SVM", "MAZDA 3 - 2005", "test", "2300 LEI", R.drawable.ic_dashboard_black_24dp),
//            new Vehicles("PH-20-SVM", "MAZDA 3 - 2005", "test", "2300 LEI", R.drawable.ic_dashboard_black_24dp),
//            new Vehicles("PH-20-SVM", "MAZDA 3 - 2005", "test", "2300 LEI", R.drawable.ic_dashboard_black_24dp),
//            new Vehicles("PH-20-SVM", "MAZDA 3 - 2005", "test", "2300 LEI", R.drawable.ic_dashboard_black_24dp),
//            new Vehicles("PH-20-SVM", "MAZDA 3 - 2005", "test", "2300 LEI", R.drawable.ic_dashboard_black_24dp),
//            new Vehicles("PH-20-SVM", "MAZDA 3 - 2005", "test", "2300 LEI", R.drawable.ic_dashboard_black_24dp),
//            new Vehicles("PH-20-SVM", "MAZDA 3 - 2005", "test", "2300 LEI", R.drawable.ic_dashboard_black_24dp),
//            new Vehicles("PH-20-SVM", "MAZDA 3 - 2005", "test", "2300 LEI", R.drawable.ic_dashboard_black_24dp),
//            new Vehicles("PH-20-SVM", "MAZDA 3 - 2005", "test", "2300 LEI", R.drawable.ic_dashboard_black_24dp),
//    };

    public Vehicles(String mLicencePlate, String mVehicleModel, String mRenew, String mTotalCost, int mVehicleLogo) {
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
*/

import java.util.ArrayList;

public class Vehicles {
    public static ArrayList<Vehicles> mVehiclesData = new ArrayList<>();
    private String mLicencePlate;
    private String mVehicleModel;
    private String mRenew;
    private String mTotalCost;
    private int mVehicleLogoId;

    public Vehicles(String mLicencePlate, String mVehicleModel, String mRenew, String mTotalCost, int mVehicleLogo) {
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
}