package com.pld.velociraptor.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.pld.velociraptor.model.UserProfile;
import com.pld.velociraptor.tools.RestClient;


import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by a607937 on 09/06/2015.
 */
public class LoadUserAsyncTask extends AsyncTask<Long, Void, UserProfile> {

    public static final String TAG = "LoadForecastAsyncTask";

    protected RestClient client;


    private WeakReference<UserLoadedCallBack> mCallBack;
    private Context context;
    private Exception pendingException;

    public LoadUserAsyncTask(RestClient client, Context context, UserLoadedCallBack callBack) {
        mCallBack = new WeakReference<UserLoadedCallBack>(callBack);
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
    protected UserProfile doInBackground(Long... cityId) {

       UserProfile result = null;

            result = client.getUserProfile("here the token");


        return result;
    }

    @Override
    protected void onPostExecute(UserProfile result) {

        UserLoadedCallBack callBack  = mCallBack.get();
        if (mCallBack == null) {
            return;
        }
        callBack.onUserLoaded(result);

    }


}