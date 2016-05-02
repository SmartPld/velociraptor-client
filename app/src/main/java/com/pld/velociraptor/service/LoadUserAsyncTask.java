package com.pld.velociraptor.service;

import android.content.Context;
import android.os.AsyncTask;

import com.pld.velociraptor.model.UserProfile;
import com.pld.velociraptor.tools.RestClient;
import com.pld.velociraptor.tools.VeloTokenCredentials;

import java.lang.ref.WeakReference;

import retrofit.RetrofitError;

/**
 * Created by a607937 on 09/06/2015.
 */
public class LoadUserAsyncTask extends AsyncTask<VeloTokenCredentials, Void, UserProfile> {

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
    protected UserProfile doInBackground(VeloTokenCredentials... token) {

       UserProfile result = null;

        try {
            result = client.getUserProfile(token[0].getUserId(),token[0].getId());
        }
        catch(RetrofitError error)
        {
            pendingException = error;
        }

        return result;
    }

    @Override
    protected void onPostExecute(UserProfile result) {

        UserLoadedCallBack callBack  = mCallBack.get();
        if (mCallBack == null) {
            return;
        }
        if(pendingException != null)
        {
            callBack.onUserLoadError(pendingException);
        }

        callBack.onUserLoaded(result);

    }


}