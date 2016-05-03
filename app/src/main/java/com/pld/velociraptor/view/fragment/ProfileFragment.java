package com.pld.velociraptor.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pld.velociraptor.R;
import com.pld.velociraptor.VelociraptorApplication;
import com.pld.velociraptor.model.Trip;
import com.pld.velociraptor.service.UserService;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Thibault on 10/12/2015.
 */
public class ProfileFragment extends BaseFragment {


    public static final String TAG = "ProfileFragment";

    @Inject
    UserService userService;


    public static ProfileFragment newInstance(Bundle bundleArg){
        ProfileFragment fragment = new ProfileFragment();

        Bundle bundle = new Bundle();

        bundle.putAll(bundleArg);
        fragment.setArguments(bundle);

        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        ((VelociraptorApplication)getActivity().getApplication()).getAppComponent().inject(this);

        ButterKnife.bind(this, v);


        return v;
    }


}


