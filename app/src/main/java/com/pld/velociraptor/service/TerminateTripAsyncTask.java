package com.pld.velociraptor.service;

import android.content.Context;
import android.os.AsyncTask;

import com.pld.velociraptor.model.Trip;
import com.pld.velociraptor.model.UserProfile;
import com.pld.velociraptor.tools.RestClient;

import java.lang.ref.WeakReference;

/**
 * Created by a607937 on 09/06/2015.
 */
public class TerminateTripAsyncTask extends AsyncTask<UserProfile, Void, UserProfile> {

    public static final String TAG = "AcceptTripAsyncTask";

    protected RestClient client;

    private WeakReference<TripTerminatedCallBack> mCallBack;
    private Context context;
    private Exception pendingException;
    private UserService userService;

    public TerminateTripAsyncTask(RestClient client, UserService userService, Context context, TripTerminatedCallBack callBack) {
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
    protected UserProfile doInBackground(UserProfile... user) {

        UserProfile result = null;
        try{
            result = client.terminateTrip(user[0], userService.getCredentials().getId());
            return result;
        }catch(Exception e){
            pendingException = e;
        }
        return result;
    }

    @Override
    protected void onPostExecute(UserProfile result) {

        TripTerminatedCallBack callBack  = mCallBack.get();
        if (mCallBack == null) {
            return;
        }
        if(pendingException != null){
            return;
        }
        callBack.tripTerminated(result);

    }


}