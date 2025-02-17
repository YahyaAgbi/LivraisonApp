package com.example.BACKAppLiv.model;

public class LocationUpdate {
    private double latitude;
    private double longitude;

    // Constructeurs, getters et setters
    public LocationUpdate() {}

    public LocationUpdate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
