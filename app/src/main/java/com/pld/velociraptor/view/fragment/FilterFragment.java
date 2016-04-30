package com.pld.velociraptor.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pld.velociraptor.R;
import com.pld.velociraptor.tools.VeloFilter;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import org.adw.library.widgets.discreteseekbar.internal.compat.SeekBarCompat;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by maxou on 4/29/16.
 */
public class FilterFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = "FilterFragment";


    @BindView(R.id.button_search)
    Button searchButton;

    @BindView(R.id.prefMaxDistChosen)
    DiscreteSeekBar maxDistanceSeekBar;

    @BindView(R.id.maxTripDistChosen)
    TextView maxDistanceTextView;

    private VeloFilter filter;

    WeakReference<OnResearchRequestedListener> mCallback;


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
        ButterKnife.bind(this, currentView);

        filter = new VeloFilter(10, null, null, null, null, null, null);

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
            mCallback.get().researchRequested(filter);
        }

    }
}
