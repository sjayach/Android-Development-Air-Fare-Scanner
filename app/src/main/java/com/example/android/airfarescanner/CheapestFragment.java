package com.example.android.airfarescanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by pooja on 15-05-2016.
 */
public class CheapestFragment extends Fragment{
    String LOG_TAG="CheapestFragment";
    public CheapestFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.result_fragment_main, container, false);

        ListView listView = (ListView)rootView.findViewById(R.id.listview);
        ArrayList<tripPojo> cheapest = null;
        Log.e("Get arguments ", ""+getArguments().size());
        if(getArguments() != null) {
            Bundle bundle = this.getArguments();
            cheapest = (ArrayList<tripPojo>) bundle.getSerializable("cheapest");
            Log.e("Size of the list ", "Size");
            Log.e("size of the list ", ""+cheapest.size());
        }


        Log.e("adapter value",  " "+ cheapest!=null ? "not null" :"null" +" Size "+cheapest.size()+ getArguments().size());

        CustomAdapter adapter = new CustomAdapter(getContext(),R.layout.airlines_list, cheapest);

       /* ArrayAdapter adapter;
        ArrayList<String> ql = new ArrayList<String>();
        ql.add("one");
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.airlines_list, R.id.item_text, ql);*/
        listView.setAdapter(adapter);
        final ArrayList<tripPojo> finalCheapest = cheapest;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

/*
                    String locationSetting = Utility.getPreferredLocation(getActivity());
*/
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                            /*.setData(WeatherContract.WeatherEntry.buildWeatherLocationWithDate(
                                    locationSetting, cursor.getLong(COL_WEATHER_DATE)
                            ));*/
                intent.putExtra("detailTrip", finalCheapest.get(position));

                startActivity(intent);
                Toast.makeText(getActivity(), finalCheapest.get(position).getPrice(),
                        Toast.LENGTH_SHORT).show();

            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(LOG_TAG, "on start");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(LOG_TAG, "on resume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(LOG_TAG, "on pause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(LOG_TAG, "on destroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(LOG_TAG, "on detach");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(LOG_TAG, "on stop");
    }
}
