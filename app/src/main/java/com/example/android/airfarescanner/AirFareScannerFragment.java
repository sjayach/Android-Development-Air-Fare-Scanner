package com.example.android.airfarescanner;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bluelinelabs.logansquare.LoganSquare;
import com.example.android.airfarescanner.Data.AirportClass;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.qpxExpress.QPXExpress;
import com.google.api.services.qpxExpress.QPXExpressRequestInitializer;
import com.google.api.services.qpxExpress.model.CarrierData;
import com.google.api.services.qpxExpress.model.CityData;
import com.google.api.services.qpxExpress.model.FlightInfo;
import com.google.api.services.qpxExpress.model.LegInfo;
import com.google.api.services.qpxExpress.model.PassengerCounts;
import com.google.api.services.qpxExpress.model.PricingInfo;
import com.google.api.services.qpxExpress.model.SegmentInfo;
import com.google.api.services.qpxExpress.model.SliceInfo;
import com.google.api.services.qpxExpress.model.SliceInput;
import com.google.api.services.qpxExpress.model.TripOption;
import com.google.api.services.qpxExpress.model.TripOptionsRequest;
import com.google.api.services.qpxExpress.model.TripsSearchRequest;
import com.google.api.services.qpxExpress.model.TripsSearchResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class AirFareScannerFragment extends Fragment implements View.OnClickListener{
    private EditText departDatetxt;
    private EditText arriveDatetxt;

    private DatePickerDialog departDatePickerDialog;
    private DatePickerDialog arriveDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private AutoCompleteTextView fromAirport;
    private AutoCompleteTextView toAirport;
    private Spinner adultsCount;
    private Spinner childrensCount;
    private Spinner travelClass;
    searchPojo searchObject;
    List<AirportClass> Airports;
    Button Clear;
    Button Clear1;
    EditText text;
    EditText text1;

    private String JsonToParse;
    Button search;
    public static final String LOG_TAG = "AirFareScanner";
    HashMap<String, Double> latMap = new HashMap<String, Double>();
    HashMap<String, Double> lonMap = new HashMap<String, Double>();

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    public AirFareScannerFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

   /* @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
    }*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_air_fare_scanner, container, false);

        JsonToParse = readJson();

        try {
            //Citation : Github. This is libaray which is faster than JSON and GSON
            Airports = LoganSquare.parseList(JsonToParse, AirportClass.class);
            //for(int i=0;i<Airports.size();i++){
            // Log.d(LOG_TAG,"Airports" + Airports);
            //}

        } catch (IOException e) {
            e.printStackTrace();
        }
        // AirportAdapter adapter=new AirportAdapter(getApplicationContext(),R.layout.activity_main,Airports)


        Log.e(LOG_TAG,String.valueOf(Airports.size()));
        List<String> airports_Names = new ArrayList<String>();
        for(AirportClass a : Airports) {
            if (a.getName() !=null && !a.getName().isEmpty()) {
                airports_Names.add(a.getCity() + " - " + a.getAirport_code());
                latMap.put(a.getAirport_code(), Double.parseDouble(a.getLatitude()));
                lonMap.put(a.getAirport_code(), Double.parseDouble(a.getLongitude()));

            }
        }
        Log.e(LOG_TAG,String.valueOf(airports_Names));
        Clear=(Button)view.findViewById(R.id.button1);
        Clear1=(Button)view.findViewById(R.id.button2);

        text=(EditText)view.findViewById(R.id.arriveDateText);
        text1=(EditText)view.findViewById(R.id.departDateText);


        Clear.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                text.setText("");
                Toast t = Toast.makeText(getActivity(),
                        "Date Cleared",
                        Toast.LENGTH_SHORT);
                t.show();

            }});

        Clear1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                text1.setText("");
                Toast t = Toast.makeText(getActivity(),
                        "Date Cleared",
                        Toast.LENGTH_SHORT);
                t.show();

            }});
        fromAirport = (AutoCompleteTextView) view.findViewById(R.id.fromAirport);

        ArrayAdapter<String> adapterNames = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,airports_Names);
        fromAirport.setAdapter(adapterNames);
        fromAirport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e(LOG_TAG, parent.getItemAtPosition(position).toString());
                String fromCityCode [] = parent.getItemAtPosition(position).toString().split(" - ");
                fromAirport.setText(fromCityCode[1]);
                InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(view.getWindowToken(), 0);
                in.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
            }
        });
        //fromAirport.setFilters(new InputFilter[]{new InputFilter.AllCaps()});



        toAirport = (AutoCompleteTextView) view.findViewById(R.id.toAirport);
        ArrayAdapter<String> adapterto = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,airports_Names);
        toAirport.setAdapter(adapterto);
        toAirport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e(LOG_TAG, parent.getItemAtPosition(position).toString());
                String toCityCode [] = parent.getItemAtPosition(position).toString().split(" - ");
                toAirport.setText(toCityCode[1]);
                InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(view.getWindowToken(), 0);
                in.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
            }
        });
        //toAirport.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        adultsCount = (Spinner) view.findViewById(R.id.adultsCount);
        childrensCount = (Spinner) view.findViewById(R.id.childCount);
        travelClass = (Spinner) view.findViewById(R.id.spinner);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        findViewsById(view);

        setDateTimeField();

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        search = (Button) view.findViewById(R.id.button);
        Log.e(LOG_TAG, "Main Activity");


        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.dropdown_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        Spinner adultno = (Spinner) view.findViewById(R.id.adultsCount);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(),
                R.array.adultNumber, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        adultno.setAdapter(adapter1);

        Spinner childno = (Spinner) view.findViewById(R.id.childCount);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),
                R.array.childNumber, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        childno.setAdapter(adapter2);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String from = fromAirport.getText().toString();
                String to= toAirport.getText().toString();
                String arriveDate = arriveDatetxt.getText().toString();
                String departDate = departDatetxt.getText().toString();
                int adult = Integer.parseInt(adultsCount.getSelectedItem().toString());
                int children = Integer.parseInt(childrensCount.getSelectedItem().toString());
                String cabin = travelClass.getSelectedItem().toString();


                Log.e(LOG_TAG, "Search Clicked");
                Log.e(LOG_TAG, "From " + fromAirport.getText());
                Log.e(LOG_TAG, "To " + toAirport.getText());
                Log.e(LOG_TAG, "Adult Count :" + adultsCount.getSelectedItem().toString());
                Log.e(LOG_TAG, "Child Count : " + childrensCount.getSelectedItem().toString());
                Log.e(LOG_TAG, "Depart Date : " + departDatetxt.getText());
                Log.e(LOG_TAG, "Arrive Date :" + arriveDatetxt.getText());

                if (from.isEmpty())
                    Toast.makeText(getActivity(), "Invalid Departure Airport", Toast.LENGTH_LONG).show();
                else if (to.isEmpty())
                    Toast.makeText(getActivity(), "Invalid Detination Airport ", Toast.LENGTH_LONG).show();
                else if (departDate.isEmpty())
                    Toast.makeText(getActivity(), "Please select Departure Date", Toast.LENGTH_LONG).show();
                else if ((adult + children) > 9) {
                    Toast.makeText(getActivity(), "Upto 9 Passsengers at a time", Toast.LENGTH_LONG).show();
                }
                else {
                    searchObject = new searchPojo(from, to, departDate, arriveDate, adult, children, cabin);
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    String currency = prefs.getString(getString(R.string.pref_currency_key),
                            getString(R.string.pref_currency_us));
                    Log.e("Selected Currency", currency );
                    FetchAirFareTask airfareTask = new FetchAirFareTask(currency);
                    airfareTask.execute();

                }


            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(getActivity()).addApi(AppIndex.API).build();







        return view;
    }
    private void findViewsById(View view) {
        departDatetxt = (EditText) view.findViewById(R.id.departDateText);
        departDatetxt.setInputType(InputType.TYPE_NULL);
        departDatetxt.requestFocus();

        arriveDatetxt = (EditText) view.findViewById(R.id.arriveDateText);
        arriveDatetxt.setInputType(InputType.TYPE_NULL);
    }
    private void setDateTimeField() {
        departDatetxt.setOnClickListener(this);
        arriveDatetxt.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();


        departDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                departDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);


                departDatetxt.setText(dateFormatter.format(newDate.getTime()));
                try {
                    arriveDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            Calendar newDate = Calendar.getInstance();
                            newDate.set(year, monthOfYear, dayOfMonth);

                            arriveDatetxt.setText(dateFormatter.format(newDate.getTime()));
                        }
                    }, year, monthOfYear, dayOfMonth);
                    arriveDatePickerDialog.getDatePicker().setMinDate(new SimpleDateFormat("yyyy-MM-dd").parse(departDatetxt.getText().toString()).getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }


        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
    @Override
    public void onClick(View view) {
        if (view == departDatetxt) {
            departDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            departDatePickerDialog.show();
        } else if (view == arriveDatetxt) {

            Calendar newCalendar = Calendar.getInstance();

            try {
                arriveDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);

                        arriveDatetxt.setText(dateFormatter.format(newDate.getTime()));
                    }
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                if(departDatetxt.getText() == null || departDatetxt.getText().length()==0)
                {
                    Toast t = Toast.makeText(getActivity(),
                            "Please Select the Depart Date",
                            Toast.LENGTH_LONG);
                    t.show();
                    departDatePickerDialog.show();

                }
                else{
                    arriveDatePickerDialog.getDatePicker().setMinDate(new SimpleDateFormat("yyyy-MM-dd").parse(departDatetxt.getText().toString()).getTime());
                    arriveDatePickerDialog.show();

                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        /*client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
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
                "Main Page", // TODO: Define a title for the content shown.
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

    class FetchAirFareTask extends AsyncTask<String, Void, ArrayList<tripPojo>> {
        public static final String LOG_TAG = "fetchairfaretask";

        private ProgressDialog progress;
        private String saleCountry;







        public FetchAirFareTask(String country) {

            saleCountry = country;
            progress = new ProgressDialog(getActivity());
        }


        @Override
        protected void onPreExecute() {
            progress.setMessage("Fetching Data... Please Wait");
            progress.show();
            progress.setCancelable(false);

            Log.d(LOG_TAG, "onPreExecute called");
        }

        @Override
        protected ArrayList<tripPojo> doInBackground(String... params) {
            ArrayList<tripPojo> tripList;
            tripList = new ArrayList<tripPojo>();


            // adding each child node to HashMap key => value

            // Citation : Stack Overflow
            String APPLICATION_NAME = "MyFlightApplication";
            final String API_KEY = "";
            HttpTransport httpTransport;
/*            String saleCountry = "US";*/
            JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
            try {
                //httpTransport = GoogleNetHttpTransport.newTrustedTransport();
                Log.e(LOG_TAG, "commented");
                httpTransport = AndroidHttp.newCompatibleTransport();
                PassengerCounts passengers = new PassengerCounts();
                passengers.setAdultCount(searchObject.getAdultCount());
                passengers.setChildCount(searchObject.getChildrenCount());

                List<SliceInput> slices = new ArrayList<SliceInput>();
                SliceInput slice = new SliceInput();
                slice.setOrigin(searchObject.getFromAirport());
                slice.setDestination(searchObject.getDestinationAirport());
                slice.setDate(searchObject.getDepartDate());
                slice.setPreferredCabin(searchObject.getTravelClass());
                slices.add(slice);

                if (!searchObject.getArriveDate().isEmpty()) {
                    slice = new SliceInput();
                    slice.setOrigin(searchObject.getDestinationAirport());
                    slice.setDestination(searchObject.getFromAirport());
                    slice.setDate(searchObject.getArriveDate());
                    slice.setPreferredCabin(searchObject.getTravelClass());
                    slices.add(slice);
                }
                TripOptionsRequest request = new TripOptionsRequest();
                request.setSolutions(50);
                request.setPassengers(passengers);
                request.setSaleCountry(saleCountry);
                request.setSlice(slices);

                TripsSearchRequest parameters = new TripsSearchRequest();
                parameters.setRequest(request);
                QPXExpress qpXExpress = new QPXExpress.Builder(httpTransport, JSON_FACTORY, null).setApplicationName(APPLICATION_NAME)
                        .setGoogleClientRequestInitializer(new QPXExpressRequestInitializer("AIzaSyDrXTMZ28U5N57UrfdE8l8QddQjuHF5KvE")).build();

                TripsSearchResponse list = qpXExpress.trips().search(parameters).execute();

                List<CarrierData> cdList = list.getTrips().getData().getCarrier();
                HashMap<String, String> map = new HashMap<String, String>();
                for(CarrierData cd : cdList) {
                    map.put(cd.getCode(), cd.getName());

                }
                HashMap<String, String> cityHashMap = new HashMap<String, String>();
                List<CityData> cityList = list.getTrips().getData().getCity();
                for(CityData city: cityList) {
                    cityHashMap.put(city.getCode(), city.getName());
                }
                List<TripOption> tripResults=list.getTrips().getTripOption();

                String id;

                for(int i=0; i<tripResults.size(); i++){
                    tripPojo trip = new tripPojo();
                    double price = 0;
                    System.out.println("Trip Size: " + tripResults.size());
                    //Trip Option ID
                    id = tripResults.get(i).getId();
                    System.out.println("id " + id);
                    trip.setId(id);
                    //Pricing
                    List<PricingInfo> priceInfo = tripResults.get(i).getPricing();
                    Locale locale = new Locale("EN", saleCountry);

                    String currencySymbol = Currency.getInstance(locale).getSymbol();

                    for (int p = 0; p < priceInfo.size(); p++) {
                        NumberFormat format = NumberFormat.getCurrencyInstance();

                        format.setCurrency(Currency.getInstance(locale));

                        double personPrice = format.parse(priceInfo.get(p).getSaleTotal()).doubleValue();

                        if (p == 0) {
                            personPrice *= searchObject.getAdultCount();
                        } else {
                            personPrice *= searchObject.getChildrenCount();
                        }

                        price += personPrice;

                    }
                    System.out.println("Price " + price);
                    Double truncatedprice = new BigDecimal(price)
                            .setScale(2, BigDecimal.ROUND_HALF_UP)
                            .doubleValue();

                    trip.setPrice(currencySymbol + " " +String.valueOf(truncatedprice));
                    trip.setOrigin(searchObject.getFromAirport());
                    trip.setDestination(searchObject.getDestinationAirport());
                    //Slice
                    List<SliceInfo> sliceinfo = tripResults.get(i).getSlice();
                    ArrayList<sliceInfo> slice_info = new ArrayList<sliceInfo>();
                    System.out.println("Slices Size: " + sliceinfo.size());
                    int totalDuration = 0;
                    for (int j = 0; j < sliceinfo.size(); j++) {
                        sliceInfo sInfo = new sliceInfo();

                        String duration = sliceinfo.get(j).getDuration().toString();
                        System.out.println("duration " + duration);
                        sInfo.setDuration(duration);
                        totalDuration += Integer.parseInt(duration);
                        sInfo.setTravelTime("" + Integer.parseInt(duration) / 60 + " h " + Integer.parseInt(duration) % 60 + " m");
                        List<SegmentInfo> seginfo = sliceinfo.get(j).getSegment();
                        ArrayList<segInfo> seg_info = new ArrayList<segInfo>();
                        for (int k = 0; k < seginfo.size(); k++) {
                            segInfo seInfo = new segInfo();
                            String bookingCode = seginfo.get(k).getBookingCode();
                            System.out.println("bookingCode " + bookingCode);
                            seInfo.setBookingCode(bookingCode);
                            Integer connectionDuration = seginfo.get(k).getConnectionDuration();
                            if (connectionDuration != null)
                                seInfo.setConnectionDuration(connectionDuration);
                            FlightInfo flightInfo = seginfo.get(k).getFlight();
                            String flightNum = flightInfo.getNumber();
                            System.out.println("flightNum " + flightNum);
                            seInfo.setFlightNum(flightNum);
                            String flightCarrier= flightInfo.getCarrier();
                            System.out.println("flightCarrier "+flightCarrier);
                            seInfo.setFlightCarrier(map.get(flightCarrier));
                            seInfo.setFlightCarrierCode(flightCarrier);


                            List<LegInfo> leg = seginfo.get(k).getLeg();
                            ArrayList<legInfo> leg_info = new ArrayList<legInfo>();
                            for (int l = 0; l < leg.size(); l++) {
                                legInfo lInfo = new legInfo();
                                String aircraft = leg.get(l).getAircraft();
                                System.out.println("aircraft " + aircraft);
                                lInfo.setAircraft(aircraft);
                                String arrivalTime = leg.get(l).getArrivalTime();
                                String dateTime[] = arrivalTime.split("T");
                                String arrivaltime[];
                                if (dateTime[1].contains("+")) {
                                    arrivaltime = dateTime[1].split("\\+");
                                } else {
                                    arrivaltime = dateTime[1].split("\\-");
                                }
                                System.out.println("arrivalTime " + arrivaltime[0]);
                                lInfo.setArrivalTime(arrivaltime[0]);
                                String departDateTime = leg.get(l).getDepartureTime();
                                dateTime = departDateTime.split("T");
                                String departtime[];
                                if (dateTime[1].contains("+")) {
                                    departtime = dateTime[1].split("\\+");
                                } else {
                                    departtime = dateTime[1].split("\\-");
                                }

                                lInfo.setDepartTime(departtime[0]);
                                System.out.println("departTime " + departtime[0]);
                                String dest = leg.get(l).getDestination();
                                lInfo.setDest(dest);
                                System.out.println("Destination " + dest);
                                lInfo.setDestLatitude(latMap.get(dest));
                                lInfo.setDestLongitude(lonMap.get(dest));
                                String destTer = leg.get(l).getDestinationTerminal();
                                lInfo.setDestTer(destTer);
                                System.out.println("DestTer " + destTer);
                                String origin = leg.get(l).getOrigin();
                                lInfo.setOrigin(origin);
                                System.out.println("origun " + origin);

                                lInfo.setOriginToDestination((cityHashMap.get(origin) != null ? cityHashMap.get(origin) : origin)+ " -> "+(cityHashMap.get(dest) != null ?cityHashMap.get(dest) : dest));
                                String originTer = leg.get(l).getOriginTerminal();
                                lInfo.setOriginTer(originTer);
                                System.out.println("OriginTer " + originTer);
                                int durationLeg = leg.get(l).getDuration();
                                lInfo.setDurationLeg(String.valueOf(durationLeg));
                                System.out.println("durationleg " + durationLeg);
                                int mil = leg.get(l).getMileage();
                                lInfo.setMil(String.valueOf(mil));
                                System.out.println("Milleage " + mil);
                                leg_info.add(lInfo);

                            }
                            seInfo.setLeg_info(leg_info);
                            seg_info.add(seInfo);

                        }
                        sInfo.setSeg_info(seg_info);
                        slice_info.add(sInfo);

                    }
                    trip.setSlice_info(slice_info);
                    trip.setTotalDuration(String.valueOf(totalDuration));
                    tripList.add(trip);


                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            } catch (Throwable t) {
                t.printStackTrace();
            }
            return tripList;

        }

        @Override
        protected void onPostExecute(ArrayList<tripPojo> list) {
            super.onPostExecute(list);
            if(progress.isShowing())
                progress.dismiss();

            Intent intent = new Intent(getActivity(), ResultMainActivity.class);
            intent.putExtra("List", list);
            startActivity(intent);

            Log.d(LOG_TAG, "onPostExecute called");

        }
    }
    private String readJson() {
        return readFile("airports.json");
    }

    private List<String> readJsonFromFile() {

        List<String> strings = new ArrayList<>();
        strings.add(readFile("airports.json"));

        return strings;
    }
    private String readFile(String filename) {
        StringBuilder sb = new StringBuilder();


        try {
            InputStream json = getActivity().getAssets().open(filename);
            BufferedReader in = new BufferedReader(new InputStreamReader(json, "UTF-8"));


            String str;
            while ((str = in.readLine()) != null) {
                sb.append(str);
            }


            in.close();
        } catch (Exception e) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error")
                    .setMessage("The JSON file was not able to load properly. These tests won't work until you completely kill this demo app and restart it.")
                    .setPositiveButton("OK", null)
                    .show();
        }


        return sb.toString();
    }



}
