package com.pld.velociraptor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pld.velociraptor.communication.ProfileInteraction;
import com.pld.velociraptor.communication.ProfileMockInteraction;
import com.pld.velociraptor.model.UserProfile;

public class VelociraptorActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String sessionToken;
    private UserProfile profile;

    //TODO: Inject
    private ProfileInteraction profileInteraction = ProfileMockInteraction.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_velociraptor);

        //customize view
        Bundle b = getIntent().getExtras();
        this.sessionToken = b.getString("sessionToken");

        //TODO: Use Spinner+AsyncTask to load profile...
        profile = ProfileMockInteraction.getInstance().getUserProfile(sessionToken);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.velociraptor, menu);

        //Customize view according to user profile
        TextView nameView = (TextView) findViewById(R.id.userNameTextView);
        nameView.setText(profile.getUsername());

        TextView mailView = (TextView) findViewById(R.id.userMailTextView);
        mailView.setText(profile.getEmail());

        //update details in slide in menu:
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        MenuItem bikeMenuView = (MenuItem) navigationView.getMenu().findItem(R.id.nav_bikes);
        bikeMenuView.setTitle(Integer.toString(profile.getTripsTotal()));
        MenuItem distanceMenuView = (MenuItem) navigationView.getMenu().findItem(R.id.nav_distance);
        distanceMenuView.setTitle(Integer.toString(profile.getDistanceTotal()));
        MenuItem pointsMenuView = (MenuItem) navigationView.getMenu().findItem(R.id.nav_cash);
        pointsMenuView.setTitle(Integer.toString(profile.getPoints()));

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

        if (id == R.id.nav_bikes) {
            Toast.makeText(this, "La quantité des trajets réalisés",
                    Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_distance) {
            Toast.makeText(this, "La distance totale effectuée",
                    Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_cash) {
            Toast.makeText(this, "La totalité des remboursements obtenus",
                    Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_disconnect) {

            // logout
            profileInteraction.logout(sessionToken);

            // redirect to login activity
            Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
