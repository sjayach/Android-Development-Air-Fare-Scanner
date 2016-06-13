package com.example.android.airfarescanner;

import java.io.Serializable;

/**
 * Created by Jayakumari on 5/25/2016.
 */
public class legInfo  implements Serializable {
    String aircraft;
    String arrivalTime;
    String departTime;
    String dest;
    String destTer;
    String origin;
    String originTer;
    String durationLeg;
    String mil;
    String originToDestination;
    Double destLatitude;
    Double destLongitude;

    public String getOriginToDestination() {
        return originToDestination;
    }

    public void setOriginToDestination(String originToDestination) {
        this.originToDestination = originToDestination;
    }

    public String getAircraft() {
        return aircraft;
    }

    public void setAircraft(String aircraft) {
        this.aircraft = aircraft;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartTime() {
        return departTime;
    }

    public void setDepartTime(String departTime) {
        this.departTime = departTime;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getDestTer() {
        return destTer;
    }

    public void setDestTer(String destTer) {
        this.destTer = destTer;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getOriginTer() {
        return originTer;
    }

    public void setOriginTer(String originTer) {
        this.originTer = originTer;
    }

    public String getDurationLeg() {
        return durationLeg;
    }

    public void setDurationLeg(String durationLeg) {
        this.durationLeg = durationLeg;
    }

    public String getMil() {
        return mil;
    }

    public void setMil(String mil) {
        this.mil = mil;
    }

    public Double getDestLatitude() {
        return destLatitude;
    }

    public void setDestLatitude(Double destLatitude) {
        this.destLatitude = destLatitude;
    }

    public Double getDestLongitude() {
        return destLongitude;
    }

    public void setDestLongitude(Double destLongitude) {
        this.destLongitude = destLongitude;
    }
}
