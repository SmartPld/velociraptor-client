package com.pld.velociraptor.view.activity;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pld.velociraptor.R;
import com.pld.velociraptor.VelociraptorApplication;
import com.pld.velociraptor.model.Trip;
import com.pld.velociraptor.model.UserProfile;
import com.pld.velociraptor.service.MapServices;
import com.pld.velociraptor.service.TripAcceptedCallBack;
import com.pld.velociraptor.service.TripDrawnCallBack;
import com.pld.velociraptor.service.TripService;
import com.pld.velociraptor.service.TripTerminatedCallBack;
import com.pld.velociraptor.service.UserService;
import com.pld.velociraptor.view.fragment.DetailsTripFragment;
import com.pld.velociraptor.view.fragment.VeloMapFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsTripActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, TripDrawnCallBack, View.OnClickListener, TripAcceptedCallBack, TripTerminatedCallBack {

    private final static String KEY_TRIP = "key_trip";
    private static final int LOCATION = 0;
    private static final String KEY_USER = "key_user";

    private boolean tripDrawnUser = false;
    private LatLng userposition =  null;


    @BindView(R.id.fab_details)
    protected FloatingActionButton fab;

    @BindView(R.id.coordinator)
    protected CoordinatorLayout coordinatorLayout;

    // protected MapView mapView2;
    protected MapFragment mapFragment;

    @Inject
    MapServices mapServices;

    @Inject
    TripService tripService;

    @Inject
    UserService userService;

    @BindView(R.id.app_bar_layout)
    AppBarLayout abl;

    GoogleMap map;

    private Trip trip;


    public static Intent newIntent(Context context, Trip trip) {
        Intent intent = new Intent(context, DetailsTripActivity.class);
        intent.putExtra(KEY_TRIP, trip);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_details_trips);

        ButterKnife.bind(this);

        ((VelociraptorApplication) getApplication()).getAppComponent().inject(this);

        Intent intent = getIntent();
        trip = intent.getParcelableExtra(KEY_TRIP);

        MapsInitializer.initialize(DetailsTripActivity.this);

        mapFragment = (VeloMapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fab.setOnClickListener(this);
        fab.show();
        if(userService.getCurrentUser().getTrajet() != null && userService.getCurrentUser().getTrajet().equals(trip)){
            fab.setImageResource(R.drawable.ic_pin_drop_black_24dp);
            fab.show();
        }else if(userService.getCurrentUser().getTrajet() != null){
            fab.hide();
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        collapsingToolbar.setTitleEnabled(false);
        getSupportActionBar().setTitle("");

        DetailsTripFragment detailsTripFragment = DetailsTripFragment.newInstance(new Bundle(), trip);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detailsTripFragment)//TODO use tags
                .commit();

        ((VeloMapFragment) getFragmentManager().findFragmentById(R.id.map))
                .setListener(new VeloMapFragment.OnTouchListener() {
                    @Override
                    public void onTouch() {
                        collapsingToolbar.requestDisallowInterceptTouchEvent(true);
                    }
                });




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:

                onBackPressed();
                break;
            default:
                break;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    public void onBackPressed() {

        fab.animate().alpha(0.0f);

        Intent returnIntent = getIntent();
        setResult(RESULT_OK,returnIntent);
        finish();

        super.onBackPressed();
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION);
            return;
        }
        map.setMyLocationEnabled(true);
        GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                userposition = loc;
                map.addMarker(new MarkerOptions().position(loc))
                        .setTitle("Votre position");

                if(trip != null && !tripDrawnUser){
                    mapServices.drawTrip(DetailsTripActivity.this, map, trip, loc, true);
                }

            }
        };

        map.setOnMyLocationChangeListener(myLocationChangeListener);
        map.getUiSettings().setAllGesturesEnabled(true);
        map.getUiSettings().setScrollGesturesEnabled(true);
        map.getUiSettings().setRotateGesturesEnabled(true);
        map.getUiSettings().setZoomGesturesEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(false);


        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if(trip != null && userposition == null && !manager.isProviderEnabled( LocationManager.GPS_PROVIDER)){
            mapServices.drawTrip(this, map, trip, null, true);
        }


    }

    @Override
    public void onMapClick(LatLng latLng) {
        Toast.makeText(this,"go",Toast.LENGTH_LONG);

    }
    @Override
    protected void onPause() {
        super.onPause();
        mapFragment.onPause();
    }

    @Override
    protected void onDestroy() {
        try{
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.remove(mapFragment);
            ft.commit();
        }catch(Exception e){
        }
        super.onDestroy();

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if(mapFragment!=null) {
            mapFragment.onLowMemory();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mapFragment!=null) {
            mapFragment.onSaveInstanceState(outState);
        }

    }

    @Override
    public void onTripDrawn(boolean tripDrawnUser) {
        this.tripDrawnUser = tripDrawnUser;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION : {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED  && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


                }
                return;
            }

        }
    }


    @Override
    public void onClick(View v) {
        UserProfile user = userService.getCurrentUser();
        if(user.getTrajet() == null){
            userService.acceptTrip(user, trip, this);
        }else {
            userService.terminateTtrip(user, this);

        }

    }

    @Override
    public void tripAcceptedError(Exception pendingException) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Vous avez déjà un trajet en cours...", Snackbar.LENGTH_LONG);
        snackbar.show();

    }

    @Override
    public void tripAccepted(UserProfile result) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.selected_trip, Snackbar.LENGTH_LONG);
        snackbar.show();
        fab.setImageResource(R.drawable.ic_pin_drop_black_24dp);
        userService.setUser(result);

    }

    @Override
    public void tripTerminated(UserProfile user) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Bien joué ! retrouvez vos stats dans le profil...", Snackbar.LENGTH_LONG);
        snackbar.show();
        fab.setImageResource(R.drawable.ic_send_black_24dp);
        userService.setUser(user);
    }
}
