package com.pld.velociraptor.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pld.velociraptor.R;
import com.pld.velociraptor.model.Trip;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Thibault on 10/12/2015.
 */
public class DetailsTripFragment extends BaseFragment implements View.OnClickListener {


    private static final String KEY_TRIP = "key_trip";

    private Trip trip;

    @BindView(R.id.depart_name)
    TextView departName;

    @BindView(R.id.depart_address)
    TextView departAddress;

    @BindView(R.id.arrivee_name)
    TextView arriveeName;

    @BindView(R.id.arrivee_address)
    TextView arriveeAddress;


    public static DetailsTripFragment newInstance(Bundle bundleArg, Trip trip){
        DetailsTripFragment fragment = new DetailsTripFragment();

        Bundle bundle = new Bundle();

        bundle.putAll(bundleArg);
        bundle.putParcelable(KEY_TRIP, trip);
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_details_trip, container, false);

        ButterKnife.bind(this, v);

        if(getArguments()!=null && getArguments().getParcelable(KEY_TRIP) != null){

            trip = getArguments().getParcelable(KEY_TRIP);
            departName.setText(trip.getStation_start().getName());
            departAddress.setText(trip.getStation_start().getAddress());

            arriveeName.setText(trip.getStation_end().getName());
            arriveeAddress.setText(trip.getStation_end().getAddress());

        }

        return v;
    }

    @Override
    public void onClick(View v) {
    }
}


