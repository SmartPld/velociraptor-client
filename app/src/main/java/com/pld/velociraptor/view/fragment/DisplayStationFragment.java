package com.pld.velociraptor.view.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.pld.velociraptor.VelociraptorApplication;
import com.pld.velociraptor.model.Station;
import com.pld.velociraptor.service.JCDecauxService;
import com.pld.velociraptor.service.StationsLoadedCallBack;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Thibault on 02/05/2016.
 */
public class DisplayStationFragment extends SupportMapFragment implements OnMapReadyCallback, StationsLoadedCallBack, View.OnClickListener, GoogleMap.CancelableCallback, ClusterManager.OnClusterItemClickListener {

    public static final String TAG = "DisplayStaionFragment";
    private static final int LOCATION = 0;

    @Inject
    JCDecauxService jcDecauxService;

    List<Station> stations;

    GoogleMap googleMap;

    LatLng userPosition;

    public static DisplayStationFragment newInstance() {

        return new DisplayStationFragment();
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


        ((VelociraptorApplication) getActivity().getApplication()).getAppComponent().inject(this);
        this.getMapAsync(this);

        return super.onCreateView(inflater, container,
                savedInstanceState);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        jcDecauxService.getStations(this);
        this.googleMap = googleMap;


        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
                GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location location) {
                        LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                        userPosition = loc;

                        //googleMap.addMarker(new MarkerOptions().position(loc)
                       //         .icon(BitmapDescriptorFactory.defaultMarker(getColor(BitmapDescriptorFactory.HUE_BLUE)))
                        //        .title("Votre position")
                       // );



            }
        };

    }

    @Override
    public void stationsLoaded(List<Station> stationsList) {
        this.stations = stationsList;
        googleMap.clear();

        double biggest_long=0;
        double lowest_long=200;
        double biggest_lat=0;
        double lowest_lat=200;
        ClusterManager mClusterManager = new ClusterManager<>(getContext(), googleMap);
        mClusterManager.setRenderer(new OwnIconRendered(getContext(), googleMap,mClusterManager));
        googleMap.setOnCameraChangeListener(mClusterManager);
        for(Station station : stations){
            int sum = station.getAvailable_bike_stands() + station.getAvailable_bikes();
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

            mClusterManager.addItem(station);

            //googleMap.addMarker(new MarkerOptions().position(new LatLng(station.getPos().getLat(), station.getPos().getLng()))
            //        .icon(BitmapDescriptorFactory.defaultMarker(getColor(availability))).title(station.getName()+" : "+station.getAvailable_bikes()+"/"+sum+" vélo(s) disponibles")
            //       );
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
        return BitmapDescriptorFactory.HUE_ORANGE;
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

    @Override
    public boolean onClusterItemClick(ClusterItem clusterItem){

        Station station = (Station)clusterItem;




        return false;
    }


    class OwnIconRendered extends DefaultClusterRenderer<Station> {

        public OwnIconRendered(Context context, GoogleMap map,
                               ClusterManager<Station> clusterManager) {
            super(context, map, clusterManager);
        }

        @Override
        protected void onBeforeClusterItemRendered(Station station, MarkerOptions markerOptions) {
            int sum = station.getAvailable_bike_stands() + station.getAvailable_bikes();
            double availability = 0;
            if(sum >0){
                availability = (double)(station.getAvailable_bikes()) / sum;
            }



            markerOptions.title(station.getName()+" : "+station.getAvailable_bikes()+"/"+sum+" vélo(s) disponibles");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(getColor(availability)));
            super.onBeforeClusterItemRendered(station, markerOptions);
        }

        @Override
        protected void onBeforeClusterRendered(Cluster<Station> cluster, MarkerOptions markerOptions) {

            int sumBikes = 0;
            int total = 0;
            Iterator<Station> it = cluster.getItems().iterator();

            while(it.hasNext()){
                Station station = it.next();

                sumBikes += station.getAvailable_bikes();
                total += station.getAvailable_bike_stands() + station.getAvailable_bikes();
            }
            markerOptions.title(sumBikes+"/"+total+" vélo(s) disponibles");
            super.onBeforeClusterRendered(cluster, markerOptions);
        }

    }
}
