package com.example.android.airfarescanner;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anisha on 6/9/2016.
 */
public class CustomPlaceAdaper extends ArrayAdapter {
    private final List list;

    public CustomPlaceAdaper(Context context, int resourceId, ArrayList<HotelsRestaurants> list) {
        super(context, resourceId, list);
        this.list = list;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder view;

        if (rowView == null) {
            // Get a new instance of the row layout view
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ;
            rowView = inflater.inflate(R.layout.place_contents, null);

            view = new ViewHolder();
            view.restaurantName= (TextView) rowView.findViewById(R.id.restaurant_Name);

            view.restaurantType= (TextView) rowView.findViewById(R.id.restaurant_type);
            view.ratingBar= (RatingBar) rowView.findViewById(R.id.restaurant_rating);
            rowView.setTag(view);
        }
        else {
            view = (ViewHolder) rowView.getTag();
        }
        HotelsRestaurants item = (HotelsRestaurants) list.get(position);
        view.restaurantName.setText(item.getName());
        view.restaurantType.setText(item.getType());
        view.ratingBar.setRating(item.getRating().floatValue());
        return rowView;
    }
    protected static class ViewHolder {
        protected TextView restaurantName;
        protected TextView restaurantType;
        protected RatingBar ratingBar;
    }



        }
