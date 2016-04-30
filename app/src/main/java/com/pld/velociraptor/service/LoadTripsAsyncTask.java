package com.pld.velociraptor.service;

import android.content.Context;
import android.os.AsyncTask;

import com.pld.velociraptor.model.Trip;
import com.pld.velociraptor.model.UserProfile;
import com.pld.velociraptor.tools.RestClient;
import com.pld.velociraptor.tools.VeloFilter;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit.RetrofitError;

/**
 * Created by a607937 on 09/06/2015.
 */
public class LoadTripsAsyncTask extends AsyncTask<VeloFilter, Void, List<Trip>> {

    public static final String TAG = "LoadUserAsyncTask";

    protected RestClient client;


    private WeakReference<TripLoadedCallback> mCallBack;
    private Context context;
    private Exception pendingException;

    public LoadTripsAsyncTask(RestClient client, Context context, TripLoadedCallback callBack) {
        mCallBack = new WeakReference<TripLoadedCallback>(callBack);
        this.context = context;
        this.client = client;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

    }

    @Override
    protected List<Trip> doInBackground(VeloFilter... filters) {

        VeloFilter filter = filters[0];

        List<Trip> result = null;


        try{
            result = client.getTrips(filter);
        }catch(RetrofitError error){
            pendingException = error;

        }


        return result;
    }

    @Override
    protected void onPostExecute(List<Trip> result) {

        TripLoadedCallback callBack  = mCallBack.get();
        if (mCallBack == null) {
            return;
        }

        if(pendingException != null){
            callBack.onTripsLoadingError(pendingException);
            return;
        }
        callBack.onTripsLoaded(result);

    }


}