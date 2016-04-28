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

    private LayoutInflater inflater;

    public TripAdapter(Context context, Properties properties, List<Trip> trips) {
        super(context, -1, trips);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_trip, parent, false);

            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        Trip currentTrip = getItem(position);

        //update the textViews with the forecast informations
        viewHolder.idStationStart.setText(currentTrip.getStation_start().getId());

        return convertView;
    }


    protected static class ViewHolder {

        @BindView(R.id.description)
        protected TextView idStationStart;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }



    }

}
