package com.example.android.airfarescanner;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jayakumari on 5/24/2016.
 */
public class tripPojo implements Serializable {
    String id;
    String price;
    String origin;
    String destination;
    String totalDuration;
    ArrayList<sliceInfo> slice_info;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ArrayList<sliceInfo> getSlice_info() {
        return slice_info;
    }

    public void setSlice_info(ArrayList<sliceInfo> slice_info) {
        this.slice_info = slice_info;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(String totalDuration) {
        this.totalDuration = totalDuration;
    }
}
