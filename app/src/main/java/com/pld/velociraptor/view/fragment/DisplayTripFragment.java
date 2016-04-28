package com.pld.velociraptor.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pld.velociraptor.R;
import com.pld.velociraptor.VelociraptorApplication;
import com.pld.velociraptor.model.Trip;
import com.pld.velociraptor.service.TripLoadedCallback;
import com.pld.velociraptor.service.TripService;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by thomas on 28/04/2016.
 */
public class DisplayTripFragment extends BaseFragment implements AdapterView.OnItemClickListener,
        TripLoadedCallback, SwipeRefreshLayout.OnRefreshListener{

    public static final String TAG = "DisplayForecastFragment";

    @Inject
    protected TripService tripService;
    @Inject protected Properties properties;

    private List<Trip> trips = new ArrayList<>(); //the forecasts list
    private TripAdapter tripAdapter; //the adapter for the listView

    @InjectView(R.id.listViewTrips) ListView listForecasts; //the view for the forecasts
    @InjectView(R.id.swipe_container_list) SwipeRefreshLayout swipeView; // the swipe to refresh view

    private OnHeadlineSelectedListener mCallback;

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


        List<Trip> displayedTrips = applyFilter(tripsLoaded);
        tripAdapter = new TripAdapter(getActivity(), properties, displayedTrips);
        listForecasts.setAdapter(tripAdapter);

        swipeView.setOnRefreshListener(this);
        swipeView.setRefreshing(false);

    }

    @Override
    public void onRefresh() {
        swipeView.setOnRefreshListener(null);
        try {
            tripService.loadTrips(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
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
        ButterKnife.inject(this, currentView);

        listForecasts.setOnItemClickListener(this);

        try {
            tripService.loadTrips(this);

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

    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        void onTripSelected(Trip selectedTrip, View v);
    }
}
