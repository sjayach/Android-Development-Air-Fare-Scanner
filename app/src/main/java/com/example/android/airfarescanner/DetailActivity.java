package com.example.android.airfarescanner;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;



import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import se.walkercrou.places.GooglePlaces;
import se.walkercrou.places.Param;
import se.walkercrou.places.Place;
import se.walkercrou.places.TypeParam;
import se.walkercrou.places.Types;


public class DetailActivity extends ActionBarActivity {

    private static String layoverAirport = null;
    Double latitude;
    Double longitude;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailActivityFragment())
                    .commit();
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        /*client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Detail Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.android.airfarescanner/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);*/
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        /*Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Detail Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.android.airfarescanner/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();*/
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/

    /**
     * A placeholder fragment containing a simple view.
     */
    @SuppressLint("ValidFragment")
    public  class DetailActivityFragment extends Fragment {
        private  final String LOG_TAG = DetailActivityFragment.class.getSimpleName();


        public DetailActivityFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            Intent intent = getActivity().getIntent();
            Bundle extras = intent.getExtras();
            tripPojo trip = new tripPojo();
            if (extras != null) {
                trip = (tripPojo) extras.getSerializable("detailTrip");
                //forecastStr = t.getOrigin() + " "+ t.getDestination()+ " "+t.getPrice();
            }
            int count = 0;
            LinearLayout reLayout = (LinearLayout) rootView.findViewById(R.id.fragmentDetail);
            ArrayList<sliceInfo> slice = trip.getSlice_info();
            for (int i = 0; i < slice.size(); i++) {
                ArrayList<segInfo> seg_info = slice.get(i).getSeg_info();
                for (int j = 0; j < seg_info.size(); j++) {
                    ArrayList<legInfo> leg = seg_info.get(j).getLeg_info();

                    for (int m = 0; m < leg.size(); m++) {
                        LinearLayout lilayout = new LinearLayout(getActivity());
                        lilayout.setOrientation(LinearLayout.HORIZONTAL);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200);
                        lilayout.setLayoutParams(params);

                        String url = "http://pics.avs.io/100/100/DEFAULT.png";
                        if (seg_info.get(j).getFlightCarrierCode() != null)
                            url = "http://pics.avs.io/100/100/" + seg_info.get(j).getFlightCarrierCode().toUpperCase() + ".png";

                        ImageView flightCarrier = new ImageView(getActivity());
                        ViewGroup.LayoutParams p = new ViewGroup.LayoutParams(200, 200);
                        flightCarrier.setLayoutParams(p);

                        /*flightCarrier.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);*/
                        UrlImageViewHelper.setUrlDrawable(flightCarrier, url);
                        reLayout.addView(flightCarrier);

                        TextView originToDestination = new TextView(getActivity());
                        originToDestination.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                        originToDestination.setText(leg.get(m).getOriginToDestination());
                        reLayout.addView(originToDestination);


                        TextView departTime = new TextView(getActivity());
                        /*departTime.setHeight(49);
                        departTime.setWidth(52);*/
                        //departTime.set
                        departTime.setPadding(2, 2, 100, 2);
                        departTime.setTextAppearance(getActivity(), android.R.style.TextAppearance_Small);
                        ;
                        departTime.setText(leg.get(m).getDepartTime());
                        lilayout.addView(departTime);

                        TextView departAirport = new TextView(getActivity());
                        /*departAirport.setHeight(45);
                        departAirport.setWidth(48);*/
                        departAirport.setPadding(2, 2, 100, 2);

                        departAirport.setTextAppearance(getActivity(), android.R.style.TextAppearance_Medium);
                        ;
                        departAirport.setText(leg.get(m).getOrigin());
                        lilayout.addView(departAirport);


                        TextView travelTime = new TextView(getActivity());
                        travelTime.setPadding(2, 2, 100, 2);
                        travelTime.setTextAppearance(getActivity(), android.R.style.TextAppearance_Small);
                        ;

                        int duration = Integer.parseInt(leg.get(m).getDurationLeg());
                        String travlTimeDuration = String.valueOf(duration / 60) + " h " + String.valueOf(duration % 60) + " m";
                        travelTime.setText(travlTimeDuration);
                        lilayout.addView(travelTime);


                        TextView arrivalAirport = new TextView(getActivity());
                        /*arrivalAirport.setHeight(67);
                        arrivalAirport.setWidth(43);*/
                        arrivalAirport.setTextAppearance(getActivity(), android.R.style.TextAppearance_Medium);
                        ;
                        arrivalAirport.setPadding(2, 2, 100, 2);
                        arrivalAirport.setText(leg.get(m).getDest());
                        layoverAirport = arrivalAirport.getText().toString();
                        lilayout.addView(arrivalAirport);

                        TextView arrivalTime = new TextView(getActivity());
                        arrivalTime.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        arrivalTime.setText(leg.get(m).getArrivalTime());
                        lilayout.addView(arrivalTime);

                        reLayout.addView(lilayout);
                        count++;
                        latitude = leg.get(m).getDestLatitude();
                        longitude = leg.get(m).getDestLongitude();
                    }
                    int connection = seg_info.get(j).getConnectionDuration();
                    if (connection > 0) {
                        TextView connectionDuration = new TextView(getActivity());
                        String connection_Duration = (connection / 60) + " h " + (connection % 60) + " m\n";
                        connectionDuration.setText(connection_Duration);
                        reLayout.addView(connectionDuration);


                    }
                    if ((connection/60) >= 5 || connection == 0) {
                        Button b = new Button(getActivity());
                        b.setText("Near by Restaurants and Hotels");
                        /*b.setTag(latitude + "," +longitude);*/
                        b.setTag(R.id.latitude, latitude);
                        b.setTag(R.id.longitude, longitude);
                        b.setTag(R.id.airport, layoverAirport);
                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                try {

                                    Log.e(LOG_TAG, latitude + " "+ longitude);

                                    FetchAirFareTask airfareTask =
                                            new FetchAirFareTask(Double.parseDouble(v.getTag(R.id.latitude).toString()),
                                                    Double.parseDouble(v.getTag(R.id.longitude).toString()),
                                                    v.getTag(R.id.airport).toString());
                                    airfareTask.execute();



                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }
                        });
                        reLayout.addView(b);

                    }

                }

            }
            Log.e("detail activity", String.valueOf(count));


            /*TextView forecastDetail = new TextView(getActivity());
            forecastDetail.setText(trip.getPrice());
            LinearLayout lilayout = new LinearLayout(getActivity());
            lilayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 67);
            lilayout.setLayoutParams(params);
            lilayout.addView(forecastDetail);
            reLayout.addView(lilayout);*/
            return rootView;
        }

        /*@Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            // Inflate the menu; this adds items to the action bar if it is present.
            inflater.inflate(R.menu.detailfragment, menu);

            // Retrieve the share menu item
            MenuItem menuItem = menu.findItem(R.id.action_share);

            // Get the provider and hold onto it to set/change the share intent.
            ShareActionProvider mShareActionProvider =
                    (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

            // Attach an intent to this ShareActionProvider.  You can update this at any time,
            // like when the user selects a new piece of data they might like to share.
            if (mShareActionProvider != null ) {
                mShareActionProvider.setShareIntent(createShareForecastIntent());
            } else {
                Log.d(LOG_TAG, "Share Action Provider is null?");
            }
        }

        private Intent createShareForecastIntent(){
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, forecastStr + FORECAST_SHARE_HASHTAG);
            return shareIntent;

        }*/
    }

    /*@Override
    public void onBackPressed() {
        moveTaskToBack(true);
        DetailActivity.this.finish();
    }*/
     class FetchAirFareTask extends AsyncTask<String, Void, placeDetails> {
        double latitude;
        double longitude;
        String airport;
        private ProgressDialog progress;

        public FetchAirFareTask(double latitude, double longitude, String airport) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.airport = airport;
            progress = new ProgressDialog(DetailActivity.this);

        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setMessage("Fetching Data... Please Wait");
            progress.show();
            progress.setCancelable(false);

        }

       @Override
       protected placeDetails doInBackground(String... params) {
           ArrayList<HotelsRestaurants> hotelsRestaurants = new ArrayList<HotelsRestaurants>();
           Log.e("on backround", "executing");
           //Citation : Github. GooglePlaces is a library
           GooglePlaces google = new GooglePlaces("AIzaSyAghJ8eCrnOykZ3FOOmx5EyDYYU8V8tCao");
            Log.e("created key", "yes");
           List<Place> places;
           try {
               places = google.getNearbyPlaces(latitude, longitude, 30000, 50, Param.name("types").value(Types.TYPE_RESTAURANT));
               Log.e("Places Size", "" + places.size());
               for (Place place : places) {

                   hotelsRestaurants.add(new HotelsRestaurants(place.getName(), place.getAddress(), place.getRating(), place.getLatitude(), place.getLongitude(), "Restaurant", place.getIconUrl()));
               }
           } catch (Exception e) {
               e.printStackTrace();
           }
           try {
               places = google.getNearbyPlaces(latitude, longitude, 30000, 50, Param.name("types").value(Types.TYPE_LODGING));
               for (Place place : places) {
                   double rating = place.getRating();
                   if (rating < 0){
                       rating = 0;
                   }
                   hotelsRestaurants.add(new HotelsRestaurants(place.getName(), place.getAddress(), rating, place.getLatitude(), place.getLongitude(), "Hotel", place.getIconUrl()));
               }
           } catch (Exception e) {
            e.printStackTrace();
           }
           placeDetails pd = new placeDetails(latitude, longitude, hotelsRestaurants, airport);



            return pd;
        }
        @Override
        protected  void onPostExecute(placeDetails pd) {
            super.onPostExecute(pd);
            if(progress.isShowing())
                progress.dismiss();

            Intent intent = new Intent(DetailActivity.this, MapsMainActivity.class);
            intent.putExtra("HotelsRes", pd);
            startActivity(intent);
        }

    }
        /*@Override
        protected void onPostExecute() {
            super.onpos
            super.onPostExecute();


        }*/
}