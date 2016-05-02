package com.pld.velociraptor.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.pld.velociraptor.R;
import com.pld.velociraptor.VelociraptorApplication;
import com.pld.velociraptor.model.Trip;
import com.pld.velociraptor.model.UserProfile;
import com.pld.velociraptor.service.TripService;
import com.pld.velociraptor.service.UserLoggedOutCallBack;
import com.pld.velociraptor.service.UserService;
import com.pld.velociraptor.tools.VeloFilter;
import com.pld.velociraptor.view.fragment.DisplayStationFragment;
import com.pld.velociraptor.view.fragment.DisplayTripFragment;
import com.pld.velociraptor.view.fragment.FilterFragment;
import com.pld.velociraptor.view.fragment.OnResearchRequestedListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class VelociraptorActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DisplayTripFragment.OnTripSelectedListener, UserLoggedOutCallBack,
        OnResearchRequestedListener, View.OnClickListener {

    public final static String KEY_USER = "key_user";

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Inject
    UserService userService;

    @Inject
    TripService tripService;

    NavigationView navigationViewFilter;

    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_velociraptor);

        ((VelociraptorApplication) this.getApplication()).getAppComponent().inject(this); //here injection

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //initialize listener that forwards to filter fragment once button has been clicked
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationViewFilter = (NavigationView) findViewById(R.id.nav_view_right);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            /**
             * Called when a drawer has settled in a completely closed state.
             */
            public void onDrawerClosed(View view) {
                if (!view.equals(navigationViewFilter)) {
                    super.onDrawerClosed(drawer);
                }
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                if (!drawerView.equals(navigationViewFilter)) {
                    super.onDrawerOpened(drawer);
                    invalidateOptionsMenu();
                }
                // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (!drawerView.equals(navigationViewFilter)) {
                    super.onDrawerSlide(drawerView, slideOffset); // this disables the animation
                } else {
                    super.onDrawerSlide(drawerView, 0); // this disables the animation
                }

            }
        };


        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_left);
        navigationView.setNavigationItemSelectedListener(this);


        DisplayTripFragment listFrag = (DisplayTripFragment) getSupportFragmentManager().findFragmentByTag(DisplayTripFragment.TAG);

        if (listFrag == null)

        {
            listFrag = DisplayTripFragment.newInstance();

            // Add the fragment to the 'fragment_container' FrameLayout replace to avoid reinstancing overlaying fragments
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, listFrag, DisplayTripFragment.TAG)
                    .commit();
        }


        FilterFragment filterFrag = (FilterFragment) getSupportFragmentManager().findFragmentByTag(FilterFragment.TAG);
        if (filterFrag == null) {
            filterFrag = FilterFragment.newInstance();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_filter_container, filterFrag, FilterFragment.TAG);
            transaction.commit();
        }

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawer(Gravity.RIGHT);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.velociraptor, menu);

        UserProfile profileToBeDisplayed = userService.getCurrentUser();
        //Customize view according to user profile
        TextView nameView = (TextView) findViewById(R.id.userNameTextView);
        nameView.setText(profileToBeDisplayed.getUsername());

        TextView mailView = (TextView) findViewById(R.id.userMailTextView);
        mailView.setText(profileToBeDisplayed.getEmail());

        //TODO: All this code goes into the method that builds the profile fragment
        //update details in slide in menu:
        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_left);
        //MenuItem bikeMenuView = (MenuItem) navigationView.getMenu().findItem(R.id.nav_bikes);
        //bikeMenuView.setTitle(Integer.toString(profile.getTripsTotal()));
        //MenuItem distanceMenuView = (MenuItem) navigationView.getMenu().findItem(R.id.nav_distance);
        // distanceMenuView.setTitle(Integer.toString(profile.getDistanceTotal()));
        // MenuItem pointsMenuView = (MenuItem) navigationView.getMenu().findItem(R.id.nav_cash);
        // pointsMenuView.setTitle(Integer.toString(profile.getPoints()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_trips) {
            DisplayTripFragment listFrag = (DisplayTripFragment) getSupportFragmentManager().findFragmentByTag(DisplayTripFragment.TAG);

            if (listFrag == null) {
                listFrag = DisplayTripFragment.newInstance();
            }

            // Add the fragment to the 'fragment_container' FrameLayout replace to avoid reinstancing overlaying fragments
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, listFrag, DisplayTripFragment.TAG)
                    .commit();
            fab.setImageResource(R.drawable.ic_search_black_24px);
            fab.setOnClickListener(this);
        } else if (id == R.id.nav_stations) {

            DisplayStationFragment stationFragment = (DisplayStationFragment) getSupportFragmentManager().findFragmentByTag(DisplayStationFragment.TAG);

            if (stationFragment == null)

            {
                stationFragment = DisplayStationFragment.newInstance();


            }
            // Add the fragment to the 'fragment_container' FrameLayout replace to avoid reinstancing overlaying fragments
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, stationFragment, DisplayStationFragment.TAG)
                    .commit();
            fab.setImageResource(R.drawable.ic_refresh_black_24dp);
            fab.setOnClickListener(stationFragment);
        } else if (id == R.id.nav_disconnect) {

            // logout
            userService.logout(userService.getCurrentUser(), this);


        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onTripSelected(Trip selectedTrip, View v) {
        Intent intent = DetailsTripActivity.newIntent(VelociraptorActivity.this, selectedTrip);
        startActivity(intent);
    }

    @Override
    public void userLoggedOut() {
        // redirect to login activity
        Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(loginIntent);
        finish();

    }

    @Override
    public void researchRequested(VeloFilter filter) {

        DisplayTripFragment listFrag = (DisplayTripFragment) getSupportFragmentManager().findFragmentByTag(DisplayTripFragment.TAG);

        if (listFrag != null) {
            drawer.closeDrawer(Gravity.RIGHT);
            listFrag.researchTrips(filter);
        }

    }

    @Override
    public void onClick(View v) {
        drawer.openDrawer(Gravity.RIGHT);
    }
}
