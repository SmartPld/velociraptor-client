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
public class RecyclerTripAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        /***/ {

    private List<Trip> trips;
    private WeakReference<OnItemClickListener> mCallBack;
    private Context context;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private Trip headTrip;


    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerTripAdapter(Context context, List<Trip> invitations, Trip head, OnItemClickListener listener) {

        mCallBack = new WeakReference<OnItemClickListener>(listener);
        this.trips = invitations;
        this.context = context;
        headTrip = head;


    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh = null;
        if (viewType == TYPE_ITEM) {
            // create a new view
            View v = LayoutInflater.from(context)
                    .inflate(R.layout.item_trip, parent, false);
            // set the view's size, margins, paddings and layout parameters

            vh = new ViewHolder(v);

        } else if (viewType == TYPE_HEADER) {
            // create a new view
            View v = LayoutInflater.from(context)
                    .inflate(R.layout.header_list_trip, parent, false);
            // set the view's size, margins, paddings and layout parameters

            vh = new ViewHolderHeader(v);
        }

        return vh;

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && headTrip != null)
            return TYPE_HEADER;

        return TYPE_ITEM;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {


        if (viewHolder instanceof ViewHolder) {

            RecyclerTripAdapter.ViewHolder vh = (RecyclerTripAdapter.ViewHolder) viewHolder;
            //the current Forecast
            final Trip currentTrip = trips.get(position);
            //we generate the color

            vh.depart.setText(currentTrip.getStation_start().getName());
            vh.arrivee.setText(currentTrip.getStation_end().getName());
            vh.prix.setText(""+currentTrip.getPoints()+" points ");
            vh.delta.setText(currentTrip.getDelta_elevation()+"m");
            vh.distance.setText(currentTrip.getDistance()+"km");

            //cast holder to VHItem and set data
        } else if (viewHolder instanceof ViewHolderHeader) {
            //cast holder to VHHeader and set data for header.

            RecyclerTripAdapter.ViewHolderHeader vh = (RecyclerTripAdapter.ViewHolderHeader) viewHolder;
            //the current Forecast
            final Trip currentTrip = headTrip;
            //we generate the color
            vh.depart.setText(currentTrip.getStation_start().getName());
            vh.arrivee.setText(currentTrip.getStation_end().getName());
            vh.prix.setText(""+currentTrip.getPoints()+" points ");
            vh.delta.setText(currentTrip.getDelta_elevation()+"m");
            vh.distance.setText(currentTrip.getDistance()+"km");

            long minRestantes = (currentTrip.getValiditeEnd().getTime() - System.currentTimeMillis()) / 60;
            vh.timeRemainingTextView.setText(minRestantes+" minutes");

        }
    }






    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return trips.size() + 1 ;
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

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolderHeader extends  RecyclerView.ViewHolder implements View.OnClickListener {
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

        @BindView(R.id.time_remaining)
        TextView timeRemainingTextView;


        public ViewHolderHeader(View v) {
            super(v);

            ButterKnife.bind(this, v);

            v.setClickable(true);
            card.setOnClickListener(new CardView.OnClickListener(){

                @Override
                public void onClick(View v) {
                    OnItemClickListener listener = null;
                    if ((listener = mCallBack.get()) != null) {
                        mCallBack.get().onItemClick(v, getAdapterPosition(), headTrip);
                    }
                }
            });


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