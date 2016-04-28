package com.pld.velociraptor.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pld.velociraptor.R;


/**
 * Created by Thibault on 10/12/2015.
 */
public class DetailsTripFragment extends BaseFragment implements View.OnClickListener {


    public static DetailsTripFragment newInstance(Bundle bundleArg){
        DetailsTripFragment fragment = new DetailsTripFragment();

        Bundle bundle = new Bundle();
        bundle.putAll(bundleArg);

        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_details_trip, container, false);

        return v;
    }

    @Override
    public void onClick(View v) {
    }
}


