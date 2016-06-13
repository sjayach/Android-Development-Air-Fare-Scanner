package com.example.android.airfarescanner;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jayakumari on 6/4/2016.
 */
public class placeDetails implements Serializable {
    Double latitude;
    Double longitude;
    String airport;
    ArrayList<HotelsRestaurants> hr;

    public placeDetails(Double latitude, Double longitude, ArrayList<HotelsRestaurants> hr, String airport) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.hr = hr;
        this.airport = airport;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public ArrayList<HotelsRestaurants> getHr() {
        return hr;
    }

    public void setHr(ArrayList<HotelsRestaurants> hr) {
        this.hr = hr;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }
}
