package com.example.android.airfarescanner;

import android.app.Activity;
import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.api.services.qpxExpress.model.LegInfo;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jayakumari on 5/24/2016.
 */
public class CustomAdapter extends ArrayAdapter {
    private final List list;

    public CustomAdapter(Context context, int resourceId, ArrayList<tripPojo> list) {
        super(context, resourceId, list);
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder view;

        if(rowView == null)
        {
            // Get a new instance of the row layout view
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
            rowView = inflater.inflate(R.layout.airlines_list, null);

            // Hold the view objects in an object, that way the don't need to be "re-  finded"
            view = new ViewHolder();
            view.fromAirport= (TextView) rowView.findViewById(R.id.departAirport);
            view.toAirport= (TextView) rowView.findViewById(R.id.arrivalAirport);
/*
            view.Airlines = (TextView) rowView.findViewById(R.id.airlineText);
*/
            view.departTime = (TextView) rowView.findViewById(R.id.departTime);
            view.ArrivalTime = (TextView) rowView.findViewById(R.id.arrivalTime);
            view.travelTime = (TextView) rowView.findViewById(R.id.travelTime);
            view.price = (TextView) rowView.findViewById(R.id.price);
            view.fromAirport1= (TextView) rowView.findViewById(R.id.departAirport1);
            view.toAirport1= (TextView) rowView.findViewById(R.id.arrivalAirport1);
/*
            view.Airlines1 = (TextView) rowView.findViewById(R.id.airlineText1);
*/
            view.departTime1 = (TextView) rowView.findViewById(R.id.departTime1);
            view.ArrivalTime1 = (TextView) rowView.findViewById(R.id.arrivalTime1);
            view.travelTime1 = (TextView) rowView.findViewById(R.id.travelTime1);
            view.deImg = (ImageView) rowView.findViewById(R.id.departView);
            view.arImg = (ImageView) rowView.findViewById(R.id.arriveView);

            rowView.setTag(view);
        } else {
            view = (ViewHolder) rowView.getTag();
        }

        /** Set data to your Views. */
        int i = 0;
        tripPojo item = (tripPojo) list.get(position);
        view.price.setText(item.getPrice());
        view.travelTime.setText(item.getSlice_info().get(i).getTravelTime());
        view.fromAirport.setText(item.getOrigin());
        view.toAirport.setText(item.getDestination());
        //view.Airlines.setText(item.getSlice_info().get(i).getSeg_info().get(0).getFlightCarrier());
        ArrayList<legInfo> lInfo = new ArrayList<legInfo>();
        lInfo = item.getSlice_info().get(i).getSeg_info().get(0).getLeg_info();
        view.departTime.setText(lInfo.get(0).getDepartTime());
        lInfo = item.getSlice_info().get(i).getSeg_info().get(item.getSlice_info().get(i).getSeg_info().size() -1).getLeg_info();
        view.ArrivalTime.setText(lInfo.get(0).getArrivalTime());
        String url = "http://pics.avs.io/100/100/DEFAULT.png";
        if (item.getSlice_info().get(i).getSeg_info().get(0).getFlightCarrierCode() != null)
            url = "http://pics.avs.io/100/100/"+item.getSlice_info().get(i).getSeg_info().get(0).getFlightCarrierCode().toUpperCase()+".png";
        Log.e("URL", item.getSlice_info().get(i).getSeg_info().get(0).getFlightCarrierCode().toUpperCase());
        // Citation: Github. Its a library to display the image.
        UrlImageViewHelper.setUrlDrawable(view.deImg, url);

        if (item.getSlice_info().size() > 1) {
            i = 1;

            view.travelTime1.setText(item.getSlice_info().get(i).getTravelTime());
            view.fromAirport1.setText(item.getDestination());
            view.toAirport1.setText(item.getOrigin());
            //view.Airlines1.setText(item.getSlice_info().get(i).getSeg_info().get(0).getFlightCarrier());
            ArrayList<legInfo> lInfo1 = new ArrayList<legInfo>();
            lInfo1 = item.getSlice_info().get(i).getSeg_info().get(0).getLeg_info();
            view.departTime1.setText(lInfo1.get(0).getDepartTime());
            lInfo1 = item.getSlice_info().get(i).getSeg_info().get(item.getSlice_info().get(i).getSeg_info().size() -1).getLeg_info();
            view.ArrivalTime1.setText(lInfo1.get(lInfo1.size()-1).getArrivalTime());
            String arriveurl = "http://pics.avs.io/100/100/DEFAULT.png";
            if (item.getSlice_info().get(i).getSeg_info().get(0).getFlightCarrierCode() != null)
                arriveurl = "http://pics.avs.io/100/100/"+item.getSlice_info().get(i).getSeg_info().get(0).getFlightCarrierCode().toUpperCase()+".png";
            UrlImageViewHelper.setUrlDrawable(view.arImg, arriveurl);
        } else {
            ImageView img = (ImageView) rowView.findViewById((R.id.arriveView));
            img.setVisibility(View.GONE);
            /*TextView airline_text = (TextView)rowView.findViewById(R.id.airlineText1);
            airline_text.setVisibility(View.GONE);*/
            LinearLayout layout = (LinearLayout) rowView.findViewById(R.id.returnLinearLayout);
            layout.setVisibility(View.GONE);
        }

        return rowView;
    }

    protected static class ViewHolder{
        protected TextView Airlines;
        protected TextView departTime;
        protected TextView ArrivalTime;
        protected TextView fromAirport;
        protected TextView toAirport;
        protected TextView travelTime;
        protected TextView Airlines1;
        protected TextView departTime1;
        protected TextView ArrivalTime1;
        protected TextView fromAirport1;
        protected TextView toAirport1;
        protected TextView travelTime1;
        protected TextView price;
        protected ImageView deImg;
        protected ImageView arImg;
    }
}