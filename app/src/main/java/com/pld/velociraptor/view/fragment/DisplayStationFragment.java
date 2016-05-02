package com.pld.velociraptor.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pld.velociraptor.VelociraptorApplication;
import com.pld.velociraptor.model.Pos;
import com.pld.velociraptor.model.Station;
import com.pld.velociraptor.service.JCDecauxService;
import com.pld.velociraptor.service.StationsLoadedCallBack;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Thibault on 02/05/2016.
 */
public class DisplayStationFragment extends SupportMapFragment implements OnMapReadyCallback, StationsLoadedCallBack, View.OnClickListener, GoogleMap.CancelableCallback {

    public static final String TAG = "DisplayStaionFragment";

    @Inject
    JCDecauxService jcDecauxService;

    List<Station> stations;

    GoogleMap googleMap;


    public static DisplayStationFragment newInstance(){

        return  new DisplayStationFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ((VelociraptorApplication)getActivity().getApplication()).getAppComponent().inject(this);
        this.getMapAsync(this);

        return super.onCreateView(inflater, container,
                savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        jcDecauxService.getStations(this);
        this.googleMap = googleMap;

    }

    @Override
    public void stationsLoaded(List<Station> stationsList) {
        this.stations = stationsList;
        googleMap.clear();

        double biggest_long=0;
        double lowest_long=200;
        double biggest_lat=0;
        double lowest_lat=200;

        for(Station station : stations){
            int sum = station.getAvailable_bike_stands() +station.getAvailable_bikes();
            double availability = 0;
            if(sum >0){
                availability = station.getAvailable_bikes()/sum;
            }

            //TODO move this algorithm
            if(station.getPos().getLat() > biggest_lat){
                biggest_lat = station.getPos().getLat();
            }
            if(station.getPos().getLat() < lowest_lat){
                lowest_lat = station.getPos().getLat();
            }

            if(station.getPos().getLng() > biggest_long){
                biggest_long = station.getPos().getLng();
            }
            if(station.getPos().getLng() < lowest_long){
                lowest_long = station.getPos().getLng();
            }

            googleMap.addMarker(new MarkerOptions().position(new LatLng(station.getPos().getLat(), station.getPos().getLng()))
                    .icon(BitmapDescriptorFactory.defaultMarker(getColor(availability))).title(station.getName()+" : "+station.getAvailable_bikes()+"/"+sum+" vélo(s) disponibles")
                   );
        }

        LatLngBounds bounds = new LatLngBounds( new LatLng(lowest_lat, lowest_long),new LatLng(biggest_lat, biggest_long));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 200));

    }


public float getColor(double availability) {

    if (availability > 0.7) {
        return BitmapDescriptorFactory.HUE_GREEN;
    } else if (availability < 0.3) {
        return BitmapDescriptorFactory.HUE_RED;
    } else {
        return BitmapDescriptorFactory.HUE_BLUE;
    }
}

    @Override
    public void onClick(View v) {
        jcDecauxService.getStations(this);
    }


    @Override
    public void onFinish() {

    }

    @Override
    public void onCancel() {

    }
}
