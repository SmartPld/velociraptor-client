package com.pld.velociraptor.view.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pld.velociraptor.R;
import com.pld.velociraptor.model.Trip;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by thomas on 28/04/2016.
 */
public class TripAdapter extends ArrayAdapter<Trip> {

    public TripAdapter(Context context, Properties properties, List<Trip> trips) {
        super(context, -1, trips);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_trip, parent, false);

            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        Trip currentTrip = getItem(position);

        //update the textViews with the forecast informations
        viewHolder.depart.setText(currentTrip.getStation_start().getName());
        viewHolder.arrivee.setText(currentTrip.getStation_end().getName());
        viewHolder.prix.setText(""+currentTrip.getPoints()+" points ");
        viewHolder.delta.setText(currentTrip.getDelta_elevation()+"m");
        viewHolder.distance.setText(currentTrip.getDistance()+"m");

        return convertView;
    }


    protected static class ViewHolder {

        @BindView(R.id.depart)
        protected TextView depart;

        @BindView(R.id.arrivee)
        protected TextView arrivee;

        @BindView(R.id.price)
        protected TextView prix;

        @BindView(R.id.distance)
        protected TextView distance;

        @BindView(R.id.delta)
        protected TextView delta;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }

    }

}
