package com.example.android.airfarescanner.Data;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * Created by anisha on 5/29/2016.
 */

@JsonObject(serializeNullCollectionElements = true, serializeNullObjects = true)
public class AirportClass {

    @SerializedName("code")
    @JsonField(name = "code")
    String Airport_code;
    @SerializedName("name")
    @JsonField(name = "name")
    private String Name;
    @SerializedName("city")
    @JsonField(name = "city")
    private String city;
    @SerializedName("country")
    @JsonField(name = "country")
    private String country;
    @SerializedName("lat")
    @JsonField(name = "lat")
    private String Latitude;
    @SerializedName("lon")
    @JsonField(name = "lon")
    private String Longitude;
    @SerializedName("tz")
    @JsonField(name = "tz")
    private String tz;
    @SerializedName("state")
    @JsonField(name="state")
    private String State;
    @SerializedName("woeid")
    @JsonField(name="woeid")
    private String woeid;
    @SerializedName("phone")
    @JsonField(name="phone")
    private String phone;
    @SerializedName("type")
    @JsonField(name="type")
    private String type;
    @SerializedName("email")
    @JsonField(name="email")
    private String email;
    @SerializedName("url")
    @JsonField(name="url")
    private String url;
    @SerializedName("runway_length")
    @JsonField(name="runway_length")
    private String runway_length;
    @SerializedName("elev")
    @JsonField(name="elev")
    private String elev;
    @SerializedName("icao")
    @JsonField(name="icao")
    private String icao;
    @SerializedName("direct_flights")
    @JsonField(name="direct_flights")
    private String direct_flights;
    @SerializedName("carriers")
    @JsonField(name="carriers")
    private String carriers;




    public AirportClass() {

    }

    public AirportClass(String airport_code, String name, String city, String country, String latitude, String longitude, String tz, String state, String woeid, String phone, String type, String email, String url, String runway_length, String elev, String icao, String direct_flights, String carriers) {
        Airport_code = airport_code;
        Name = name;
        this.city = city;
        this.country = country;
        Longitude = longitude;
        Latitude = latitude;
        this.tz = tz;
        State = state;
        this.woeid = woeid;
        this.phone = phone;
        this.type = type;
        this.email = email;
        this.url = url;
        this.runway_length = runway_length;
        this.elev = elev;
        this.icao = icao;
        this.direct_flights = direct_flights;
        this.carriers = carriers;
    }

    public String getAirport_code() {
        return Airport_code;
    }

    public void setAirport_code(String airport_code) {
        Airport_code = airport_code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getWoeid() {
        return woeid;
    }

    public void setWoeid(String woeid) {
        this.woeid = woeid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRunway_length() {
        return runway_length;
    }

    public void setRunway_length(String runway_length) {
        this.runway_length = runway_length;
    }

    public String getElev() {
        return elev;
    }

    public void setElev(String elev) {
        this.elev = elev;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public String getDirect_flights() {
        return direct_flights;
    }

    public void setDirect_flights(String direct_flights) {
        this.direct_flights = direct_flights;
    }

    public String getCarriers() {
        return carriers;
    }

    public void setCarriers(String carriers) {
        this.carriers = carriers;
    }
}
