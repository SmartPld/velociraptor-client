package com.pld.velociraptor.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.pld.velociraptor.R;
import com.pld.velociraptor.VelociraptorApplication;
import com.pld.velociraptor.model.Trip;
import com.pld.velociraptor.service.TripLoadedCallback;
import com.pld.velociraptor.service.TripService;
import com.pld.velociraptor.tools.VeloFilter;
import com.pld.velociraptor.view.adapters.RecyclerTripAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thomas on 28/04/2016.
 */
public class DisplayTripFragment extends BaseFragment implements AdapterView.OnItemClickListener,
        TripLoadedCallback, SwipeRefreshLayout.OnRefreshListener, RecyclerTripAdapter.OnItemClickListener {

    public static final String TAG = "DisplayTripFragment";


    private List<Trip> trips = new ArrayList<>(); //the forecasts list
    private RecyclerTripAdapter tripAdapter; //the adapter for the listView
    private VeloFilter filter;

    @Inject
    protected TripService tripService;

    @Inject
    protected Properties properties;

    @Inject
    Gson gson;

    @BindView(R.id.listViewTrips)
    RecyclerView listForecasts; //the view for the forecasts

    @BindView(R.id.swipe_container_list)
    SwipeRefreshLayout swipeView; // the swipe to refresh view

    private OnTripSelectedListener mCallback;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mCallback != null) {
            mCallback.onTripSelected((Trip) parent.getItemAtPosition(position), view);
        }
    }

    @Override
    public void onTripsLoaded(List<Trip> tripsLoaded) {
        trips.clear();
        trips.addAll(tripsLoaded);


        List<Trip> displayedTrips = trips;
        tripAdapter = new RecyclerTripAdapter(getActivity(), displayedTrips, this);
        listForecasts.setLayoutManager(new LinearLayoutManager(getActivity()));
        listForecasts.setAdapter(tripAdapter);
        tripAdapter.notifyDataSetChanged();

        swipeView.setOnRefreshListener(this);
        swipeView.setRefreshing(false);

    }

    @Override
    public void onTripsLoadingError(Exception e) {
        Snackbar.make(this.getView(), "Problème réseau", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onRefresh() {
        swipeView.setOnRefreshListener(null);
        try {

            tripService.loadTrips(filter, this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnTripSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ((VelociraptorApplication) getActivity().getApplication()).getAppComponent().inject(this);




        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View currentView = inflater.inflate(R.layout.fragment_trip_list,container, false);
        ButterKnife.bind(this, currentView);

         SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

         String filterJson = sharedPref.getString(FilterFragment.KEY_FILTER, "null_filter");
         if(filterJson.compareTo("null_filter")!=0) { // then a filter is stored here
             filter = gson.fromJson(filterJson, VeloFilter.class);
         } else{
             filter = new VeloFilter(null, null, null, null, null, null, null);
         }

        try {
            tripService.loadTrips(filter, this);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return currentView;
    }

    public void eraseList() {

        if (tripAdapter != null) {
            tripAdapter.clear();
            trips.clear();
        }
    }

    private List<Trip> applyFilter(List<Trip> tripList) {
        return tripList;
    }

    public static DisplayTripFragment newInstance() {
        return new DisplayTripFragment();
    }

    @Override
    public void onItemClick(View view, int position, Trip trip) {
        if (mCallback != null) {
            mCallback.onTripSelected(trip, view);
        }
    }

    // Container Activity must implement this interface
    public interface OnTripSelectedListener {
        void onTripSelected(Trip selectedTrip, View v);
    }


    public void researchTrips(VeloFilter filter){

        this.filter = filter;
        tripService.loadTrips(filter, this);

    }
}
