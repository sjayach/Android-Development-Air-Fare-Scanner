package com.example.android.airfarescanner;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jayakumari on 5/25/2016.
 */
public class sliceInfo implements Serializable {
    String duration;
    String travelTime;
    ArrayList<segInfo> seg_info;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    public ArrayList<segInfo> getSeg_info() {
        return seg_info;
    }

    public void setSeg_info(ArrayList<segInfo> seg_info) {
        this.seg_info = seg_info;
    }

}
