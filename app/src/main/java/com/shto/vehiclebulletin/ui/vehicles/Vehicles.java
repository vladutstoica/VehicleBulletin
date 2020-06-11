package com.shto.vehiclebulletin.ui.vehicles;

public class Vehicles {
    private String text1;
    private String text2;

    public static final Vehicles[] vehicles = {
            new Vehicles("text1", "text2"),
            new Vehicles("text3", "text4")
    };

    private Vehicles(String text1, String text2) {
        this.text1 = text1;
        this.text2 = text2;
    }

    public String getText1() {
        return text1;
    }

    public String getText2() {
        return text2;
    }
}
