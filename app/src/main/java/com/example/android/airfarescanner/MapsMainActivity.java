package com.example.android.airfarescanner;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Citation : www.numetriclabz.com. Just for reference
public class MapsMainActivity extends AppCompatActivity {
    private SlidingUpPanelLayout mLayout;
    private static final String TAG = "MainAcitvity";
    List<String> array_list;
    //TextView textView ;
    ListView listview;
    private GoogleMap googleMap;
    placeDetails pd;
    Marker marker;
    Marker airport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity_main);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras != null) {
                pd = (placeDetails) extras.getSerializable("HotelsRes");
            }

        }
        try {
            if (googleMap == null) {
                googleMap = ((MapFragment) getFragmentManager().
                        findFragmentById(R.id.map)).getMap();
            }
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            LatLng airportLocation = new LatLng(pd.getLatitude() , pd.getLongitude());
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(airportLocation, 13));

            airport = googleMap.addMarker(new MarkerOptions().
                    position(airportLocation).title(pd.getAirport()).icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).snippet( " "));



        } catch (Exception e) {
            e.printStackTrace();
        }


        init();            // call init method
        setListview();    // call setListview method
        panelListener(); // Call paneListener method

    }

    /**
     * Initialization of the textview and SlidingUpPanelLayout
     */
    public void init(){

        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mLayout.setScrollableView(findViewById(R.id.placeList));

        //textView = (TextView) findViewById(R.id.list_main);
        listview = (ListView) findViewById(R.id.placeList);

    }

    /**
     *  in this method, we set array adapter to display list of item
     *  within this method, call callback setOnItemClickListener method
     *  It call when use click on the list of item
     *  When user click on the list of item, slide up layout and display item of the list
     */
    public void setListview(){

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                //textView.setText(array_list.get(position));
                final HotelsRestaurants map = pd.getHr().get(position);
                if (marker != null) {
                    marker.remove();
                }
                final LatLng place = new LatLng(map.getLatitude() , map.getLongitude());
                marker = googleMap.addMarker(new MarkerOptions().
                        position(place).title(map.getName()).
                        snippet("Distance").anchor(0.0f, 1.0f));
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(marker.getPosition());
                builder.include(airport.getPosition());
                LatLngBounds bounds = builder.build();
                googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                    @Override
                    public View getInfoWindow(Marker arg0) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker  marker) {


                        View v = getLayoutInflater().inflate(R.layout.info_window, null);
                        TextView distance = (TextView) v.findViewById(R.id.placeDistance);
                        RatingBar rating = (RatingBar) v.findViewById(R.id.ratingBar);
                        TextView name = (TextView) v.findViewById(R.id.placeName);
                        TextView address = (TextView) v.findViewById(R.id.placeAddress);
                        TextView type = (TextView) v.findViewById(R.id.placeType);
                        if (marker.getSnippet().equals("Distance")) {

                            distance.setText("Distance : " + CalculationByDistance(airport.getPosition(), place) + " Km");
                            name.setText(map.getName());
                            rating.setRating(map.getRating().floatValue());
                            address.setText(map.getAddress());
                            type.setText(map.getType());
                        } else {
                            name.setText(airport.getTitle());
                            rating.setVisibility(v.GONE);
                            distance.setVisibility(v.GONE);
                            address.setVisibility(v.GONE);
                            type.setVisibility(v.GONE);


                        }
                        return v;
                    }
                });

                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100);
                googleMap.animateCamera(cu);



                Toast.makeText(MapsMainActivity.this, "onItemClick" , Toast.LENGTH_SHORT).show();
            }
        });



        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        CustomPlaceAdaper adapter = new CustomPlaceAdaper(getApplicationContext(),R.layout.place_contents, pd.getHr());


        listview.setAdapter(adapter);
    }

    /**
     * this method call setPanelSlidelistener method to listen open and close of slide panel
     */
    public void panelListener(){




        mLayout.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {

            // During the transition of expand and collapse onPanelSlide function will be called.
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

                Log.e(TAG, "onPanelSlide, offset " + slideOffset);
            }

            // This method will be call after slide up layout
            @Override
            public void onPanelExpanded(View panel) {
                Log.e(TAG, "onPanelExpanded");

            }

            // This method will be call after slide down layout.
            @Override
            public void onPanelCollapsed(View panel) {
                Log.e(TAG, "onPanelCollapsed");

            }

            @Override
            public void onPanelAnchored(View panel) {
                Log.e(TAG, "onPanelAnchored");
            }

            @Override
            public void onPanelHidden(View panel) {
                Log.e(TAG, "onPanelHidden");
            }
        });
    }

    /**
     * This is return type method.
     * With in this method, we create array list
     * @return array list
     */
    public List<String> array_list(){

        ArrayList<String> arrayList = new ArrayList<String>();
        for(HotelsRestaurants hotelsRestaurants : pd.getHr() ) {
            arrayList.add(hotelsRestaurants.getName() + " - " + hotelsRestaurants.getType()
            +"\n Rating : " +hotelsRestaurants.getRating());
        }
        return arrayList;
    }


    @Override
    public void onBackPressed() {
        /*if (mLayout != null &&
                (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {*/
            super.onBackPressed();
        /*}*/
    }
    //Citation : github
    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);
        Double result = Radius * c;
        DecimalFormat df = new DecimalFormat("#.00");


        return Double.parseDouble(df.format(result));
    }
}
