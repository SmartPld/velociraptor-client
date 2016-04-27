package com.pld.velociraptor.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.pld.velociraptor.R;
import com.pld.velociraptor.view.fragment.DetailsTripFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsTripActivity extends BaseActivity {

    @BindView(R.id.fab)
    FloatingActionButton fab;

    public static Intent newIntent(Context context){

        Intent intent = new Intent(context, DetailsTripActivity.class);

        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_details_trips);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);


        collapsingToolbar.setTitle("Detail");

        DetailsTripFragment detailsTripFragment = DetailsTripFragment.newInstance(new Bundle());

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detailsTripFragment)//TODO use tags
                .commit();

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

}
