package com.pld.velociraptor.service;

import android.content.Context;
import android.os.AsyncTask;

import com.pld.velociraptor.tools.RestClient;

import java.lang.ref.WeakReference;

/**
 * Created by a607937 on 09/06/2015.
 */
public class LogoutUserAsyncTask extends AsyncTask<String, Void, Void> {

    public static final String TAG = "LoginUserAsyncTask";

    protected RestClient client;


    private WeakReference<UserLoggedOutCallBack> mCallBack;
    private Context context;
    private Exception pendingException;

    public LogoutUserAsyncTask(RestClient client, Context context, UserLoggedOutCallBack callBack) {
        mCallBack = new WeakReference<UserLoggedOutCallBack>(callBack);
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
    protected Void doInBackground(String... sessionToken) {

        try{
            client.logoutUser(sessionToken[0]);
        }catch(Exception e){
            pendingException = e;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        UserLoggedOutCallBack callBack  = mCallBack.get();
        if (mCallBack == null) {
            return;
        }
        if(pendingException != null){
            //TODO exception here
            return;
        }
        callBack.userLoggedOut();

    }


}