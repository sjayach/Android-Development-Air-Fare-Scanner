package com.example.android.airfarescanner;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Jayakumari on 5/22/2016.
 */
public class searchPojo implements Serializable{
    String fromAirport;
    String destinationAirport;
    String departDate;
    String arriveDate;
    int adultCount;
    int childrenCount;
    String TravelClass;

    public searchPojo() {

    }

    public searchPojo(String fromAirport, String destinationAirport, String departDate, String arriveDate, int adultCount, int childrenCount, String travelClass) {
        this.fromAirport = fromAirport;
        this.destinationAirport = destinationAirport;
        this.departDate = departDate;
        this.arriveDate = arriveDate;
        this.adultCount = adultCount;
        this.childrenCount = childrenCount;
        this.TravelClass = QPXClass(travelClass);
    }

    public String getFromAirport() {
        return fromAirport;
    }

    public void setFromAirport(String fromAirport) {
        this.fromAirport = fromAirport;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(String destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public String getDepartDate() {
        return departDate;
    }

    public void setDepartDate(String departDate) {
        this.departDate = departDate;
    }

    public String getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(String arriveDate) {
        this.arriveDate = arriveDate;
    }

    public int getAdultCount() {
        return adultCount;
    }

    public void setAdultCount(int adultCount) {
        this.adultCount = adultCount;
    }

    public int getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }

    public String getTravelClass() {
        return TravelClass;
    }

    public void setTravelClass(String travelClass) {
        TravelClass = QPXClass(travelClass);
    }

    public String QPXClass(String travelClass) {
        String qpxClass = "";

        switch(travelClass) {
            case "Economy Class":
                qpxClass ="COACH";
                break;
            case "Premium Economy":
                qpxClass = "PREMIUM_COACH";
                break;
            case "Business Class":
                qpxClass = "BUSINESS";
                break;
            case "First Class":
                qpxClass ="FIRST";
                break;
            default:
                qpxClass="COACH";
                break;
        }
        return qpxClass;
    }
}
