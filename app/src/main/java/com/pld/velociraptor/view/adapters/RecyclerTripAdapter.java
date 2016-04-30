package com.pld.velociraptor.view.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pld.velociraptor.R;
import com.pld.velociraptor.model.Trip;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by a607937 on 18/06/2015.
 */
public class RecyclerTripAdapter extends RecyclerView.Adapter<RecyclerTripAdapter.ViewHolder>
        /***/ {

    private List<Trip> trips;
    private WeakReference<OnItemClickListener> mCallBack;
    private Context context;




    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerTripAdapter(Context context, List<Trip> invitations, OnItemClickListener listener) {

        mCallBack = new WeakReference<OnItemClickListener>(listener);
        this.trips = invitations;
        this.context = context;


    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerTripAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(context)
                .inflate(R.layout.item_trip, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        //the current Forecast
        final Trip currentTrip = trips.get(position);
        //we generate the color

        viewHolder.depart.setText(currentTrip.getStation_start().getName());
        viewHolder.arrivee.setText(currentTrip.getStation_end().getName());
        viewHolder.prix.setText(""+currentTrip.getPoints()+" points ");
        viewHolder.delta.setText(currentTrip.getDelta_elevation()+"m");
        viewHolder.distance.setText(currentTrip.getDistance()+"km");
    }






    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return trips.size();
    }

    public void remove(int position) {

    }

    public void remove(Trip trip) {


    }


    public Trip getItemAt(int position)
    {

        return trips.get(position);
    }

    public void clear() {
        trips.clear();
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position, Trip trip);
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case

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

        @BindView(R.id.card)
        CardView card;


        public ViewHolder(View v) {
            super(v);

            ButterKnife.bind(this, v);

            v.setClickable(true);
            card.setOnClickListener(new CardView.OnClickListener(){

                @Override
                public void onClick(View v) {
                    OnItemClickListener listener = null;
                    if ((listener = mCallBack.get()) != null) {
                        mCallBack.get().onItemClick(v, getAdapterPosition(), trips.get(getAdapterPosition()));
                    }
                }
            });
            //v.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            OnItemClickListener listener = null;
            if ((listener = mCallBack.get()) != null)
            {
                mCallBack.get().onItemClick(v, getAdapterPosition(), trips.get(getAdapterPosition()));
            }


        }
    }
}