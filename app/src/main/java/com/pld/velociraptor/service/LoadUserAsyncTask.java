package com.pld.velociraptor.service;

import android.content.Context;
import android.os.AsyncTask;

import com.pld.velociraptor.model.UserProfile;
import com.pld.velociraptor.tools.RestClient;

import java.lang.ref.WeakReference;

/**
 * Created by a607937 on 09/06/2015.
 */
public class LoadUserAsyncTask extends AsyncTask<String, Void, UserProfile> {

    public static final String TAG = "LoadUserAsyncTask";

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
    protected UserProfile doInBackground(String... token) {

       UserProfile result = null;

            result = client.getUserProfile(token[0]);


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