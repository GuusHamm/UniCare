package com.gmail.guushamm.unicare;

/**
 * Created by Nekkyou on 10-6-2016.
 */
public class LatLong {
    private double latitude;
    private double longitude;

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

    public LatLong(double latitude, double longitude) {

        this.latitude = latitude;
        this.longitude = longitude;
    }
}
