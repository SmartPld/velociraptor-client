package com.pld.velociraptor.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.internal.MapLifecycleDelegate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.pld.velociraptor.R;
import com.pld.velociraptor.VelociraptorApplication;
import com.pld.velociraptor.service.MapServices;
import com.pld.velociraptor.service.TripDrawnCallBack;
import com.pld.velociraptor.view.fragment.DetailsTripFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsTripActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, TripDrawnCallBack {

    @BindView(R.id.fab_details)
    protected FloatingActionButton fab;

   // protected MapView mapView2;
    protected MapFragment mapFragment;

    @Inject
    MapServices mapServices;

    @BindView(R.id.app_bar_layout)
    AppBarLayout abl;


    public static Intent newIntent(Context context){

        Intent intent = new Intent(context, DetailsTripActivity.class);

        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_details_trips);

        ButterKnife.bind(this);

        ((VelociraptorApplication)getApplication()).getAppComponent().inject(this);

        MapsInitializer.initialize(DetailsTripActivity.this);
        //mapView2 = (MapView) findViewById(R.id.mapV);
        //mapView2.onCreate(savedInstanceState);
        //mapView2.getMapAsync(this);


        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        /**CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) abl.getLayoutParams();
        AppBarLayout.Behavior behavior = new AppBarLayout.Behavior();
        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
            @Override
            public boolean canDrag(AppBarLayout appBarLayout) {
                return false;
            }
        });
        params.setBehavior(behavior);*/

        collapsingToolbar.setTitle("Detail");

        DetailsTripFragment detailsTripFragment = DetailsTripFragment.newInstance(new Bundle());

       /** FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detailsTripFragment)//TODO use tags
                .commit();
        //fab = (FloatingActionButton) findViewById(R.id.fab_details);*/
        fab.setOnClickListener(detailsTripFragment);
        fab.show();



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
    public void onBackPressed(){

        fab.animate().alpha(0.0f);

        super.onBackPressed();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Toast.makeText(this, "map ready", Toast.LENGTH_LONG).show();
        googleMap.getUiSettings().setAllGesturesEnabled(true);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        mapServices.drawTrip(this, googleMap, "45.750000,4.850000", "45.750050,4.850000");
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Toast.makeText(this,"go",Toast.LENGTH_LONG);

    }
    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapFragment.onSaveInstanceState(outState);
    }

    @Override
    public void onTripDrawn() {

    }
}
