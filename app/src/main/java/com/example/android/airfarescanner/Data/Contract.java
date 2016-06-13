package com.example.android.airfarescanner.Data;

import android.provider.BaseColumns;

/**
 * Created by anisha on 5/29/2016.
 */
public class Contract {
    public static final class AirportsList implements BaseColumns {

        // Table name
        public static final String TABLE_NAME = "Airports";

        // The location setting string is what will be sent to moviereview

        public static final String _id = "_id";
        public static final String City = "city";
        public static final String Airport_code = "iata";
        public static final String Name = "name";
        public static final String Longitude = "lon";
        public static final String Latitude = "lat";

    }
}
