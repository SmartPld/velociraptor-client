package com.pld.velociraptor.service;

import android.content.Context;
import android.os.AsyncTask;

import com.pld.velociraptor.model.Trip;
import com.pld.velociraptor.tools.RestClient;

import java.lang.ref.WeakReference;

/**
 * Created by a607937 on 09/06/2015.
 */
public class AcceptTripAsyncTask extends AsyncTask<Integer, Void, Trip> {

    public static final String TAG = "AcceptTripAsyncTask";

    protected RestClient client;

    private WeakReference<TripAcceptedCallBack> mCallBack;
    private Context context;
    private Exception pendingException;
    private UserService userService;

    public AcceptTripAsyncTask(RestClient client, UserService userService, Context context, TripAcceptedCallBack callBack) {
        mCallBack = new WeakReference<>(callBack);
        this.context = context;
        this.client = client;
        this.userService = userService;
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
    protected Trip doInBackground(Integer... credentials) {

        Trip result = null;
        try{
            result = client.acceptTrip(credentials[0], credentials[1], userService.getCredentials().getId());
            return result;
        }catch(Exception e){
            pendingException = e;
        }



        return result;
    }

    @Override
    protected void onPostExecute(Trip result) {

        TripAcceptedCallBack callBack  = mCallBack.get();
        if (mCallBack == null) {
            return;
        }
        if(pendingException != null){
            callBack.tripAcceptedError(pendingException);
            return;
        }
        callBack.tripAccepted(result);

    }


}