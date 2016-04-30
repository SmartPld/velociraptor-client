package com.pld.velociraptor.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pld.velociraptor.R;
import com.pld.velociraptor.VelociraptorApplication;
import com.pld.velociraptor.tools.VeloFilter;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import org.adw.library.widgets.discreteseekbar.internal.compat.SeekBarCompat;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by maxou on 4/29/16.
 */
public class FilterFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = "FilterFragment";
    public static final String KEY_FILTER = "key_filter";

    private VeloFilter filter;

    private WeakReference<OnResearchRequestedListener> mCallback;

    @Inject
    Gson gson;

    @BindView(R.id.button_search)
    Button searchButton;

    @BindView(R.id.prefMaxDistChosen)
    DiscreteSeekBar maxDistanceSeekBar;

    @BindView(R.id.maxTripDistChosen)
    TextView maxDistanceTextView;




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = new WeakReference<>((OnResearchRequestedListener) context);
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
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



        // Inflate the layout for this fragment
        View currentView = inflater.inflate(R.layout.fragment_filter_settings, container, false);

        ((VelociraptorApplication)getActivity().getApplication()).getAppComponent().inject(this);
        ButterKnife.bind(this, currentView);

        searchButton.setOnClickListener(this);
        maxDistanceSeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                maxDistanceTextView.setText(value+"km");
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }

        });

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        String filterJson = sharedPref.getString(KEY_FILTER, "null_filter");
        if(filterJson.compareTo("null_filter")!=0) { // then a filter is stored here
            filter = gson.fromJson(filterJson, VeloFilter.class);
        } else{
            filter = new VeloFilter(null, null, null, null, null, null, null);
        }

        if(filter.getMaxDistance() != null){
            maxDistanceSeekBar.setProgress(filter.getMaxDistance());
            maxDistanceTextView.setText(filter.getMaxDistance()+"km");
        }


        return currentView;
    }

    public static FilterFragment newInstance() {
        return new FilterFragment();
    }


    @Override
    public void onClick(View v) {

        if(mCallback!=null && mCallback.get()!=null){
            int maxDistance = maxDistanceSeekBar.getProgress();
            filter.setMaxDistance(maxDistance);
            filter.setMinDistance(0);

            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(KEY_FILTER, gson.toJson(filter));
            editor.commit();

            mCallback.get().researchRequested(filter);
        }

    }
}
