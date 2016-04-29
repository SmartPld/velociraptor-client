package com.pld.velociraptor.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pld.velociraptor.R;

import butterknife.ButterKnife;

/**
 * Created by maxou on 4/29/16.
 */
public class FilterFragment extends BaseFragment {

    public static final String TAG = "FilterFragment";


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


        // Inflate the layout for this fragment
        View currentView = inflater.inflate(R.layout.fragment_filter_settings, container, false);
        ButterKnife.bind(this, currentView);

        return currentView;
    }

    public static FilterFragment newInstance() {
        return new FilterFragment();
    }


}
