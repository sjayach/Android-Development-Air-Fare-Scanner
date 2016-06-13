package com.example.android.airfarescanner;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jayakumari on 5/25/2016.
 */
public class segInfo implements Serializable {
    String bookingCode;
    String flightNum;
    String flightCarrier;
    int connectionDuration;
    String flightCarrierCode;
    ArrayList<legInfo> leg_info;

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public String getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    public String getFlightCarrier() {
        return flightCarrier;
    }

    public void setFlightCarrier(String flightCarrier) {
        this.flightCarrier = flightCarrier;
    }

    public ArrayList<legInfo> getLeg_info() {
        return leg_info;
    }

    public void setLeg_info(ArrayList<legInfo> leg_info) {
        this.leg_info = leg_info;
    }

    public int getConnectionDuration() {
        return connectionDuration;
    }

    public void setConnectionDuration(int connectionDuration) {
        this.connectionDuration = connectionDuration;
    }

    public String getFlightCarrierCode() {
        return flightCarrierCode;
    }

    public void setFlightCarrierCode(String flightCarrierCode) {
        this.flightCarrierCode = flightCarrierCode;
    }
}
